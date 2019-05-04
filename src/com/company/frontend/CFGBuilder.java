package com.company.frontend;

import com.company.frontend.AST.*;
import com.company.frontend.IR.*;
import com.company.frontend.SymbolTable.SymbolType;

import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

public class CFGBuilder extends ASTBaseVisitor{
    private CFG cfg;

    private CFGNode curFuncRetNode = null;
   // private CFGInstAddr curFuncRetAddr = null;
    private CFGInstAddr curThisAddr = null;

    private String curFuncName = null;
    private String curClassName = null;
    private CFGNode curStaticInit;
    private CFGProcess curProc;
    private HashMap<String, Integer> strLitMap = new HashMap<>();
    private int strLitCnt = 0;

    private Stack<CFGNode> curLoopBreakNode = new Stack<>();
    private Stack<CFGNode> curLoopContNode = new Stack<>();


    public CFGBuilder(CFG _cfg) {
        cfg = _cfg;
    }
    private void visitLogicalExprNode(ASTExprNode node, CFGNode tN, CFGNode fN){
        if (node.isEmpty()){
            CFGNode n = node.startNode = node.endNode = cfg.addNode();
            n.linkTo(tN);
            return;
        }
        switch (node.nodeType) {
            case p_bool:{
                CFGNode n = node.startNode = node.endNode = cfg.addNode();
                if (((ASTPrimNode)node).intValue == 1) n.linkTo(tN);
                else n.linkTo(fN);
                break;
            }
            case e_land:
                visitLogicalExprNode(node.exprList.get(1), tN, fN);
                visitLogicalExprNode(node.exprList.get(0),node.exprList.get(1).startNode, fN);
                node.startNode = node.exprList.get(0).startNode;
                break;
            case e_lor:
                visitLogicalExprNode(node.exprList.get(1), tN, fN);
                visitLogicalExprNode(node.exprList.get(0), tN, node.exprList.get(1).startNode);
                //order is important
                node.startNode = node.exprList.get(0).startNode;
                break;
            case e_not:
                visitLogicalExprNode(node.exprList.get(0), fN, tN);
                node.startNode = node.exprList.get(0).startNode;
                break;
            default: {//ge,le,gt,lt,eq,neq...
                visitExprNode(node);
                CFGNode n = node.startNode = node.endNode = cfg.addNode();
                n.addInst(node.instList);
                if (n.insts.isEmpty() || !n.insts.lastElement().isCompare()){
                    CFGInst c_inst = n.addInst(CFGInst.InstType.op_eq);
                    c_inst.addOperand(node.instAddr);
                    c_inst.addOperand(CFGInstAddr.newImmAddr(1)); // true
                }
                CFGInst j_inst = n.addInst(CFGInst.InstType.op_jcc);
                j_inst.addOperand(CFGInstAddr.newLabelAddr(fN));//why falseNode ???
                n.linkTo(fN);
                n.linkTo(tN);
            }
        }
    }
    private void genBoolExprNode(CFGInstAddr addr, ASTExprNode expr,CFGNode endNode){
        CFGNode tNode = cfg.addNode();
        CFGNode fNode = cfg.addNode();
        CFGInst tInst = tNode.addInst(CFGInst.InstType.op_mov);
        tInst.addOperand(addr);
        tInst.addOperand(CFGInstAddr.newImmAddr(1));
        CFGInst fInst = fNode.addInst(CFGInst.InstType.op_mov);
        fInst.addOperand(addr);
        fInst.addOperand(CFGInstAddr.newImmAddr(0));
        visitLogicalExprNode(expr, tNode, fNode);
        tNode.linkTo(endNode);
        fNode.linkTo(endNode);
        }
        // expr -> stmt
        private void wrapExprNode(ASTStmtNode expr){
        if (!(expr instanceof ASTExprNode) || expr.startNode != null) return;
        CFGNode n = cfg.addNode();
        expr.startNode = expr.endNode = n;
        n.addInst(((ASTExprNode) expr).instList);
    }
    @Override
    public void visitCompilationUnitNode(ASTCompilationUnitNode node){
        CFGNode staticInit;
        staticInit = curStaticInit = cfg.addNode();
        staticInit.name = "_static_init";
        for (ASTStmtNode i : node.declList)  visitStmt(i);
        if (curStaticInit.name.equals("_static_init")) return;
        //when curStaticInit != "_static_init" (there are static data)
        curStaticInit.insts.add(new CFGInst(CFGInst.InstType.op_return));
        CFGProcess staticInitProc = new CFGProcess(staticInit, curStaticInit);
        staticInitProc.isCallee = false;
        staticInitProc.isCaller = true;
        //??? why caller
        cfg.processList.add(staticInitProc);

        CFGInst call_inst = new CFGInst(CFGInst.InstType.op_call);
        call_inst.addOperand(CFGInstAddr.newLabelAddr(staticInit));
        cfg.entryNode.insts.insertElementAt(call_inst,0);
    }

    @Override
    public void visitClassDeclNode(ASTClassDeclNode node) {
        curClassName = node.className;
        for (ASTStmtNode i : node.stmtList) visitStmt(i);
        curClassName = null;
    }

    @Override
    public void visitFuncDeclNode(ASTFuncDeclNode node) {
        curFuncRetNode = cfg.addNode();
        curFuncRetNode.addInst(CFGInst.InstType.op_return);
        node.endNode = curFuncRetNode;
        curProc = new CFGProcess(node.startNode, node.endNode);
        cfg.processList.add(curProc);
        curFuncName = node.funcName;
        visitTypeNode(node.returnType);
        visitStmt(node.paramList);
        int paramIdx = 0;
        if (node.isMember) {
            CFGInst paramInst = node.startNode.addInst(CFGInst.InstType.op_rpara);
            curThisAddr = CFGInstAddr.newRegAddr();
            paramInst.addOperand(curThisAddr);
            paramInst.addOperand(CFGInstAddr.newImmAddr(paramIdx++));
        }
        if (node.paramList != null)
            for (ASTStmtNode i : node.paramList.stmtList) {
                CFGInst paramInst = node.startNode.addInst(CFGInst.InstType.op_rpara);
                paramInst.addOperand(((ASTDeclNode) i).reg);
                paramInst.addOperand(CFGInstAddr.newImmAddr(paramIdx++));
                //what is the use of paramIdx???
            }
        curProc.paramCnt = paramIdx;
        visitStmt(node.funcBody);
        node.startNode.linkTo(node.funcBody.startNode);
        if (node.funcBody.endNode != null) node.funcBody.endNode.linkTo(curFuncRetNode);
        if (node.funcName.equals("main")) {
            node.startNode.name = "main";
            cfg.entryNode = node.startNode;
        }
        curFuncRetNode = null;
        curFuncName = null;
        curThisAddr = null;
        curProc = null;
        //funcDecl.startNode->funcBody.startNode
        //funcBody.endNode->funcDecl.endNode = funcRet
    }

    @Override
    public void visitVarDeclNode(ASTDeclNode node) {
        visitTypeNode(node.type);
        if (SymbolType.boolSymbolType.equals(node.initExpr.resultType) && !(node.initExpr instanceof  ASTPrimNode)){
            node.endNode = cfg.addNode();
            genBoolExprNode(node.reg, node.initExpr, node.endNode);
            node.startNode = node.initExpr.startNode;
        }
        else {
            visitExpr(node.initExpr);
            if (node.initExpr.instAddr != null) {
                CFGInst mvInst = new CFGInst(CFGInst.InstType.op_mov);
                CFGInstAddr mvReg = node.reg;
                mvInst.addOperand(mvReg);
                mvInst.addOperand(node.initExpr.instAddr);
                node.initExpr.instList.add(mvInst);
            }
            CFGNode curNode = cfg.addNode();
            curNode.addInst(node.initExpr.instList);
            node.startNode = node.endNode = curNode;
        }
        if (curFuncName == null && curClassName == null) {
            int size = node.reg.addr1.lit3;//???
            if (node.initExpr.nodeType == ASTNodeType.p_int || node.initExpr.nodeType == ASTNodeType.p_bool) {
                cfg.dataList.add(new CFGData("_global_"+node.name, ((ASTPrimNode)node.initExpr).intValue, true));
            } else {
                if (node.initExpr.isEmpty()) {
                    curStaticInit.linkTo(node.startNode);
                    curStaticInit = node.endNode;
                }
                cfg.dataList.add(new CFGData("_global_"+node.name, size, false));
            }
        }
    }

    @Override
    public void visitExprNode(ASTExprNode node){
        if (node.exprList == null) return;
        //if inst is in the origin order
        if  (node.nodeType != ASTNodeType.e_member && node.nodeType != ASTNodeType.e_call && node.nodeType != ASTNodeType.e_asgn){
            for (ASTExprNode i : node.exprList) visitExpr(i);
            for (ASTExprNode i: node.exprList) node.instList.addAll(i.instList);
        }
        CFGInst.InstType type = CFGInst.InstType.op_nop;
        switch (node.nodeType){
            case e_add:
                if (node.resultType.equals(SymbolType.strSymbolType)){
                    CFGInst s1_inst = new CFGInst(CFGInst.InstType.op_wpara);
                    s1_inst.addOperand(node.exprList.get(0).instAddr);
                    s1_inst.addOperand(CFGInstAddr.newImmAddr(0));
                    CFGInst s2_inst = new CFGInst(CFGInst.InstType.op_wpara);
                    s2_inst.addOperand(node.exprList.get(1).instAddr);
                    s2_inst.addOperand(CFGInstAddr.newImmAddr(1));
                    node.instList.add(s1_inst);
                    node.instList.add(s2_inst);
                    CFGInst call_inst = new CFGInst(CFGInst.InstType.op_call);
                    call_inst.addOperand(CFGInstAddr.newLabelAddr("_lib_strcat"));//use the lib
                    node.instList.add(call_inst);
                    CFGInst mov_inst = new CFGInst(CFGInst.InstType.op_mov);
                    CFGInstAddr ret_addr = CFGInstAddr.newRegAddr();
                    mov_inst.addOperand(ret_addr);
                    mov_inst.addOperand(CFGInstAddr.newRegAddr(-2));
                    node.instList.add(mov_inst);
                    node.instAddr = ret_addr;
                    return;
                }
                else type = CFGInst.InstType.op_add;
                break;
            case e_sub:
                type = CFGInst.InstType.op_sub;
                break;
            case e_mult:
                type = CFGInst.InstType.op_mult;
                break;
            case e_mod:
                type = CFGInst.InstType.op_mod;
                break;
            case e_div:
                type = CFGInst.InstType.op_div;
                break;
            case e_pos:
                type = CFGInst.InstType.op_pos;
                break;
            case e_neg:
                type = CFGInst.InstType.op_neg;
                break;
            case e_shl:
                type = CFGInst.InstType.op_shl;
                break;
            case e_shr:
                type = CFGInst.InstType.op_shr;
                break;
            case e_band:
                type = CFGInst.InstType.op_and;
                break;
            case e_bor:
                type = CFGInst.InstType.op_or;
                break;
            case e_bxor:
                type = CFGInst.InstType.op_xor;
                break;
            case e_bneg:
                type = CFGInst.InstType.op_not;
                break;
            case e_land:
            case e_lor:
            case e_not:
                //logical expression  nothing to do
                break;
            case e_inc_p:{
                CFGInst inc_inst = new CFGInst(CFGInst.InstType.op_inc);
                inc_inst.addOperand(node.exprList.get(0).instAddr);
                node.instList.add(inc_inst);
                node.instAddr = node.exprList.get(0).instAddr;
                return;
            }
            case e_inc_s:{
                CFGInst mv_inst = new CFGInst(CFGInst.InstType.op_mov);
                node.instAddr = CFGInstAddr.newRegAddr();
                mv_inst.addOperand(node.instAddr);
                mv_inst.addOperand(node.exprList.get(0).instAddr);
                node.instList.add(mv_inst);

                CFGInst inc_inst = new CFGInst(CFGInst.InstType.op_inc);
                inc_inst.addOperand(node.exprList.get(0).instAddr);
                node.instList.add(inc_inst);
                return;
            }
            case e_dec_p:{
                CFGInst dec_inst = new CFGInst(CFGInst.InstType.op_dec);
                dec_inst.addOperand(node.exprList.get(0).instAddr);
                node.instList.add(dec_inst);
                node.instAddr = node.exprList.get(0).instAddr;
                return;
            }
            case e_dec_s:{
                CFGInst mv_inst = new CFGInst(CFGInst.InstType.op_mov);
                node.instAddr = CFGInstAddr.newRegAddr();
                mv_inst.addOperand(node.instAddr);
                mv_inst.addOperand(node.exprList.get(0).instAddr);
                node.instList.add(mv_inst);

                CFGInst inc_inst = new CFGInst(CFGInst.InstType.op_dec);
                inc_inst.addOperand(node.exprList.get(0).instAddr);
                node.instList.add(inc_inst);
                return;
            }
            case e_ne:
            case e_eq:
            case e_lt:
            case e_le:
            case e_gt:
            case e_ge:
            {
                switch (node.nodeType){

                    case e_eq:
                        type = CFGInst.InstType.op_eq;
                        break;
                    case e_ne:
                        type = CFGInst.InstType.op_ne;
                        break;
                    case e_ge:
                        type = CFGInst.InstType.op_ge;
                        break;
                    case e_gt:
                        type = CFGInst.InstType.op_gt;
                        break;
                    case e_le:
                        type = CFGInst.InstType.op_le;
                        break;
                    case e_lt:
                        type = CFGInst.InstType.op_lt;
                        break;
                }
                if (SymbolType.strSymbolType.equals(node.exprList.get(0).resultType)){
                    CFGInst s1_inst = new CFGInst(CFGInst.InstType.op_wpara);
                    s1_inst.addOperand(node.exprList.get(0).instAddr);
                    s1_inst.addOperand(CFGInstAddr.newImmAddr(0));
                    CFGInst s2_inst = new CFGInst(CFGInst.InstType.op_wpara);
                    s2_inst.addOperand(node.exprList.get(1).instAddr);
                    s2_inst.addOperand(CFGInstAddr.newImmAddr(1));
                    node.instList.add(s1_inst);
                    node.instList.add(s2_inst);
                    CFGInst call_inst = new CFGInst(CFGInst.InstType.op_call);
                    call_inst.addOperand(CFGInstAddr.newLabelAddr("_lib_strcmp"));//use the lib
                    node.instList.add(call_inst);
                    CFGInst cmp_inst = new CFGInst(type);
                    cmp_inst.addOperand(CFGInstAddr.newRegAddr(-2));
                    cmp_inst.addOperand(CFGInstAddr.newImmAddr(0));
                    //???
                    node.instList.add(cmp_inst);
                    node.instAddr = CFGInstAddr.newRegAddr(-2);
                }
                else {
                    CFGInst inst = new CFGInst(type);
                    for (ASTExprNode i : node.exprList) inst.addOperand(i.instAddr);
                    node.instList.add(inst);
                    node.instAddr = CFGInstAddr.newRegAddr();
                }
                return;
            }
            /*case e_creator:
                node.instAddr = node.exprList.firstElement().instAddr;
                return;*/
            case e_list:
                return;
            case e_empty:
                return;
            case e_idx:
                node.instAddr = CFGInstAddr.newMemAddr(node.exprList.get(0).instAddr, node.exprList.get(1).instAddr, 8, 8);
                //why num = 8 ???
                return;
            //exprList has not been visited
            case e_member:
                //use member outside the class
                visitExpr(node.exprList.firstElement());
                node.instList.addAll(node.exprList.firstElement().instList);
                //if last element of member expression (such as a.b)
                if (node.exprList.get(1).instAddr != null && node.exprList.get(1).instAddr.a_type == CFGInstAddr.addrType.a_imm){
                    node.instAddr = CFGInstAddr.newMemAddr(node.exprList.get(0).instAddr, CFGInstAddr.newImmAddr(0), 8, node.exprList.get(1).instAddr.getNum());
                    //lit4 is the position in the class
                }
                else // (such as a.b.b.b)
                {
                    visitExpr(node.exprList.get(1));
                    node.instList.addAll(node.exprList.get(1).instList);
                }
                return;
            case e_call:
                //exprList = {functionName, paramList}
                type = CFGInst.InstType.op_call;
                visitExpr(node.exprList.get(0));
                node.instList.addAll(node.exprList.get(0).instList);
                //array.size();
                if (node.toNode.name.startsWith("_lib_array")){
                    CFGInst mv_inst = new CFGInst(CFGInst.InstType.op_mov);
                    node.instAddr = CFGInstAddr.newRegAddr();
                    mv_inst.addOperand(node.instAddr);
                    mv_inst.addOperand(CFGInstAddr.newMemAddr(node.exprList.get(0).exprList.get(0).instAddr, CFGInstAddr.newImmAddr(0),0,0));
                    node.instList.add(mv_inst);
                    return;
                }
                if (curProc != null) curProc.isCaller = true;
                //print println
                if (node.toNode.name.startsWith("_lib_print")){
                    boolean isLine = node.toNode.name.equals("_lib_println");
                    Stack<ASTExprNode> exprStack = new Stack<>();
                    ASTExprNode param = node.exprList.get(1).exprList.get(0);
                    visitExpr(param);
                    exprStack.add(param);
                    //optimization of add of String
                    while (!exprStack.isEmpty()){
                        ASTExprNode expr =  exprStack.pop();
                        if (expr.nodeType == ASTNodeType.e_call && expr.toNode.name.equals("_lib_toString")){
                            //toString(int)
                            ASTExprNode intExpr = expr.exprList.get(1).exprList.get(0);
                            node.instList.addAll(intExpr.instList);
                            CFGInst param_inst = new CFGInst( CFGInst.InstType.op_wpara);
                            param_inst.addOperand(intExpr.instAddr);
                            param_inst.addOperand(CFGInstAddr.newImmAddr(0));
                            node.instList.add(param_inst);

                            CFGInst call_inst = new CFGInst(CFGInst.InstType.op_call);
                            if (isLine && exprStack.empty()){
                                call_inst.addOperand(CFGInstAddr.newLabelAddr("_lib_printlnInt"));
                                isLine = false;
                            }
                            else call_inst.addOperand(CFGInstAddr.newLabelAddr("_lib_printInt"));
                            node.instList.add(call_inst);
                        }
                        else if (expr.nodeType == ASTNodeType.e_add){
                            for (int i = expr.exprList.size()-1; i>=0 ;i--)
                                exprStack.push(expr.exprList.get(i));
                        }
                        else {
                            node.instList.addAll(expr.instList);
                            CFGInst param_inst = new CFGInst( CFGInst.InstType.op_wpara);
                            param_inst.addOperand(expr.instAddr);
                            param_inst.addOperand(CFGInstAddr.newImmAddr(0));
                            node.instList.add(param_inst);

                            CFGInst call_inst = new CFGInst(CFGInst.InstType.op_call);
                            if (isLine && exprStack.empty()){
                                call_inst.addOperand(CFGInstAddr.newLabelAddr("_lib_println"));
                                isLine = false;
                            }
                            else call_inst.addOperand(CFGInstAddr.newLabelAddr("_lib_print"));
                            node.instList.add(call_inst);
                        }
                    }
                    return;
                }
                int flag = 0; //if flag then ptAddr
                CFGInstAddr ptAddr = null;
                if (node.toNode.name.startsWith("_class")){
                    flag = 1;
                    if (curThisAddr == null || node.exprList.get(0).nodeType == ASTNodeType.e_member)
                                ptAddr = node.exprList.get(0).exprList.get(0).instAddr ;
                    else ptAddr = curThisAddr;
                }
                //str
                if (node.toNode.name.startsWith("_lib_str")){
                    flag = 1;
                    ptAddr = node.exprList.get(0).exprList.get(0).instAddr;
                }
                if (node.exprList.size()> 1){
                    Vector<ASTExprNode> exprList = node.exprList.get(1).exprList;
                    for (ASTExprNode i: exprList) {
                        visitExpr(i);
                        node.instList.addAll(i.instList);
                    }
                    for (int i = exprList.size()-1; i>=0; i--){
                        ASTExprNode j = exprList.get(i);
                        CFGInst inst = new CFGInst(CFGInst.InstType.op_wpara);
                        inst.addOperand(j.instAddr);
                        inst.addOperand(CFGInstAddr.newImmAddr(i + flag));
                        node.instList.add(inst);
                    }
                }
                if (flag == 1) {
                    CFGInst inst = new CFGInst(CFGInst.InstType.op_wpara);
                    assert (ptAddr != null);
                    inst.addOperand(ptAddr);
                    inst.addOperand(CFGInstAddr.newImmAddr(0));
                    node.instList.add(inst);
                }
                CFGProcess callee = cfg.getProc(node.toNode.name);
                if (callee != null) callee.isCallee = true;

                CFGInst call_inst = new CFGInst(CFGInst.InstType.op_call);
                call_inst.addOperand(CFGInstAddr.newLabelAddr(node.toNode));
                node.instList.add(call_inst);

                CFGInst mv_inst = new CFGInst(CFGInst.InstType.op_mov);
                CFGInstAddr ret_addr = CFGInstAddr.newRegAddr();
                mv_inst.addOperand(ret_addr);
                mv_inst.addOperand(CFGInstAddr.newRegAddr(-2));
                node.instList.add(mv_inst);
                node.instAddr = ret_addr;
                return;
            case e_asgn:{
                if (SymbolType.boolSymbolType.equals(node.exprList.get(1).resultType) && !(node.exprList.get(1) instanceof ASTPrimNode)){
                    //logical assign
                    visitExpr(node.exprList.firstElement());
                    node.startNode = cfg.addNode();
                    node.endNode = cfg.addNode();
                    node.startNode.addInst(node.exprList.get(0).instList);
                    genBoolExprNode(node.exprList.get(0).instAddr, node.exprList.get(1), node.endNode);
                    node.startNode.linkTo(node.exprList.get(1).startNode);
                }
                else {
                    for (ASTExprNode i: node.exprList) visitExpr(i);
                    for (ASTExprNode i : node.exprList) node.instList.addAll(i.instList);
                    CFGInst inst = new CFGInst(CFGInst.InstType.op_mov);
                    inst.addOperand(node.exprList.get(0).instAddr);
                    inst.addOperand(node.exprList.get(1).instAddr);
                    node.instList.add(inst);
                }
                node.instAddr = node.exprList.get(0).instAddr;
            }
            return;
            default:
                return;
        }
        assert type != CFGInst.InstType.op_nop;
        CFGInst inst = new CFGInst(type);
        node.instAddr = CFGInstAddr.newRegAddr();
        inst.addOperand(node.instAddr);
        for (ASTExprNode i : node.exprList) inst.addOperand(i.instAddr);
        node.instList.add(inst);
    }
    @Override
    public void visitStmtNode(ASTStmtNode node) {
        switch (node.nodeType){
            case s_block: {
                if (node.stmtList == null || node.stmtList.isEmpty()) {
                    node.startNode = node.endNode = cfg.addNode();
                    break;
                }
                for (ASTStmtNode i : node.stmtList) visitStmt(i);
                for (ASTStmtNode i : node.stmtList) wrapExprNode(i);
                CFGNode lastNode = node.stmtList.firstElement().endNode;
                for (int i = 1; i < node.stmtList.size(); ++i) {
                    if (lastNode != null) lastNode.linkTo(node.stmtList.get(i).startNode);
                    lastNode = node.stmtList.get(i).endNode;
                }
                node.startNode = node.stmtList.firstElement().startNode;
                node.endNode = node.stmtList.lastElement().endNode;
                break;
            }
            case s_break:
            case s_continue:{
                CFGNode n = cfg.addNode();
                CFGInst j_inst = n.addInst(CFGInst.InstType.op_jmp);
                CFGNode toNode = (node.nodeType == ASTNodeType.s_continue)? curLoopContNode.peek():curLoopBreakNode.peek();
                j_inst.addOperand(CFGInstAddr.newLabelAddr(toNode));
                n.linkTo(toNode);
                node.startNode = n;
                node.endNode = null;
                break;
            }
            case s_for:{
                //for (0;1;2) 3
                // start = 0 -> 1-> (3, end)
                // 3 -> 2(step)-> 1
                if (node.stmtList == null) break;
                CFGNode n = cfg.addNode();
                CFGNode stepNode = cfg.addNode();
                node.endNode = cfg.addNode();
                curLoopBreakNode.push(node.endNode);
                curLoopContNode.push(stepNode);

                for (int i = 0; i < node.stmtList.size(); i++){
                    if (i == 1) continue;
                    visitStmt(node.stmtList.get(i));
                }
                wrapExprNode(node.stmtList.get(3));

                n.addInst(((ASTExprNode)node.stmtList.get(0)).instList);
                visitLogicalExprNode((ASTExprNode)node.stmtList.get(1), node.stmtList.get(3).startNode, node.endNode);
                n.linkTo(node.stmtList.get(1).startNode);

                if (node.stmtList.get(3).endNode != null)
                    node.stmtList.get(3).endNode.linkTo(stepNode);
                stepNode.addInst(((ASTExprNode)node.stmtList.get(2)).instList);
                stepNode.linkTo(node.stmtList.get(1).startNode);
                node.startNode = n;
                curLoopContNode.pop();
                curLoopBreakNode.pop();
                break;
            }
            case s_while:{
                //while (0) 1
                // start = 0 -> (1,end)
                // 1 -> 0
                // cont -> 0
                if (node.stmtList == null) break;
                node.endNode = cfg.addNode();
                CFGNode contNode = cfg.addNode();
                curLoopBreakNode.push(node.endNode);
                curLoopContNode.push(contNode);
                visitStmt(node.stmtList.get(1));
                wrapExprNode(node.stmtList.get(1));
                visitLogicalExprNode((ASTExprNode)node.stmtList.get(0), node.stmtList.get(1).startNode, node.endNode);
                node.startNode = node.stmtList.firstElement().startNode;
                if (node.stmtList.get(1).endNode != null)
                    node.stmtList.get(1).endNode.linkTo(node.startNode);
                contNode.linkTo(node.startNode);
                curLoopBreakNode.pop();
                curLoopContNode.pop();
                break;
            }
            case s_if:{
                CFGNode n = cfg.addNode();
                CFGNode fN;
                for (int i = node.stmtList.size()-1; i >=1; i--){
                    ASTStmtNode j = node.stmtList.get(i);
                    visitStmt(j);
                    wrapExprNode(j);
                    if (j.endNode != null) j.endNode.linkTo(n);
                }
                if (node.stmtList.size() < 3) fN = n;
                else fN = node.stmtList.get(2).startNode;
                visitLogicalExprNode((ASTExprNode)node.stmtList.get(0), node.stmtList.get(1).startNode, fN);
                node.startNode = node.stmtList.firstElement().startNode;
                node.endNode = n;
                break;
            }
            case s_return:{
                if (node.stmtList != null) {
                    ASTExprNode expr = (ASTExprNode) node.stmtList.firstElement();
                    if (SymbolType.boolSymbolType.equals(expr.resultType) && !(expr instanceof ASTPrimNode)) {
                        CFGNode n = cfg.addNode();
                        genBoolExprNode(CFGInstAddr.newRegAddr(-2),expr, n);
                        node.startNode = expr.startNode;
                        n.linkTo(curFuncRetNode);
                        node.endNode = null;
                        return;
                    }
                    for (ASTStmtNode i : node.stmtList) visitStmt(i);
                }
                CFGNode n = cfg.addNode();
                n.addInst(((ASTExprNode)node.stmtList.firstElement()).instList);
                if (((ASTExprNode)node.stmtList.firstElement()).instAddr != null){
                    CFGInst mv_inst = n.addInst(CFGInst.InstType.op_mov);
                    mv_inst.addOperand(CFGInstAddr.newRegAddr(-2));
                    mv_inst.addOperand(((ASTExprNode)node.stmtList.firstElement()).instAddr);
                }
                node.startNode = n;
                n.linkTo(curFuncRetNode);
                node.endNode = null;
                break;
            }
            case s_paramlist:
                if (node.stmtList != null)
                    for (ASTStmtNode i: node.stmtList) visitStmt(i);
                break;
            case s_empty:
                node.startNode = node.endNode = cfg.addNode();
                break;
        }
    }

    @Override
    public void visitPrimNode(ASTPrimNode node) {
        switch (node.nodeType){
            case p_bool:
            case p_int:
                node.instAddr = CFGInstAddr.newImmAddr(node.intValue);
                break;
            case p_str:
                int idx;
                String name;
                if (strLitMap.containsKey(node.stringValue)){
                    idx = strLitMap.get(node.stringValue);
                    name = "_str_"  + idx;
                }
                else {
                    idx = ++strLitCnt;
                    strLitMap.put(node.stringValue, idx);
                    name = "_str_" + idx;
                    cfg.dataList.add(new CFGData(name, node.stringValue));
                }
                node.instAddr = CFGInstAddr.newStaticAddr(name, node.stringValue.length());
                break;
            case p_id:
                //if use member inside class
                if (curThisAddr != null && node.instAddr != null && node.instAddr.a_type == CFGInstAddr.addrType.a_imm){
                    node.instAddr = CFGInstAddr.newMemAddr(curThisAddr, CFGInstAddr.newImmAddr(0),8,node.instAddr.getNum());
                }
                break;
            case p_null:
                node.instAddr = CFGInstAddr.newImmAddr(0);
                break;
            case p_this:
                node.instAddr = curThisAddr;
                break;
        }
    }

    @Override
    public void visitCreatorNode(ASTCreatorNode node) {
        if (node.exprList != null)
            for (ASTExprNode i : node.exprList) visitExpr(i);
        visitTypeNode(node.type);
        if (node.type.dimension > 0){
            CFGInstAddr startAddr = CFGInstAddr.newStackAddr(8);
            CFGInstAddr tmpAddr = startAddr;
            int iDim = 0;
            for (ASTExprNode i : node.exprList){
                if (i.isEmpty()) break;
                ++iDim;
                node.instList.addAll(i.instList);
                CFGInst mv_inst = new CFGInst(CFGInst.InstType.op_mov);
                assert tmpAddr!=null;
                mv_inst.addOperand(tmpAddr);
                mv_inst.addOperand(i.instAddr);
                node.instList.add(mv_inst);
                tmpAddr = CFGInstAddr.newStackAddr(8);
            }
            {
                CFGInstAddr tmpReg = CFGInstAddr.newRegAddr(-4);
                CFGInst lea_inst = new CFGInst(CFGInst.InstType.op_lea);
                lea_inst.addOperand(tmpReg);
                lea_inst.addOperand(startAddr);
                node.instList.add(lea_inst);
                CFGInst param_inst = new CFGInst(CFGInst.InstType.op_wpara);
                param_inst.addOperand(tmpReg);
                param_inst.addOperand(CFGInstAddr.newImmAddr(1));
                node.instList.add(param_inst);
            }
            {
                CFGInst param_inst = new CFGInst(CFGInst.InstType.op_wpara);
                param_inst.addOperand(CFGInstAddr.newImmAddr(iDim));
                param_inst.addOperand(CFGInstAddr.newImmAddr(0));
                node.instList.add(param_inst);
            }
            CFGInst call_inst = new CFGInst(CFGInst.InstType.op_call);
            call_inst.addOperand(CFGInstAddr.newLabelAddr("_lib_alloc"));
            node.instList.add(call_inst);
            node.instAddr = CFGInstAddr.newRegAddr(-2);
        }
        else {
            CFGInst param_inst = new CFGInst(CFGInst.InstType.op_wpara);
            param_inst.addOperand(CFGInstAddr.newImmAddr(node.resultType.getMemSize()));
            param_inst.addOperand(CFGInstAddr.newImmAddr(0));
            node.instList.add(param_inst);
            CFGInst call_inst = new CFGInst(CFGInst.InstType.op_call);
            call_inst.addOperand(CFGInstAddr.newLabelAddr("malloc"));
            node.instList.add(call_inst);
            if (node.hasConstructor){
                node.instAddr = CFGInstAddr.newRegAddr();
                CFGInst mv_inst = new CFGInst(CFGInst.InstType.op_mov);
                mv_inst.addOperand(node.instAddr);
                mv_inst.addOperand(CFGInstAddr.newRegAddr(-2));
                node.instList.add(mv_inst);
                CFGInst this_inst = new CFGInst(CFGInst.InstType.op_wpara);
                this_inst.addOperand(CFGInstAddr.newRegAddr(-2));
                this_inst.addOperand(CFGInstAddr.newImmAddr(0));
                node.instList.add(this_inst);
                CFGInst cons_inst = new CFGInst(CFGInst.InstType.op_call);
                cons_inst.addOperand(CFGInstAddr.newLabelAddr("_class_"+node.type.className + "._init"));
                node.instList.add(cons_inst);
            }
            else {
                node.instAddr = CFGInstAddr.newRegAddr(-2);
            }
        }
        //array of class = array of point
    }

    @Override
    public void visitTypeNode(ASTTypeNode node) {
            //output
    }
}
