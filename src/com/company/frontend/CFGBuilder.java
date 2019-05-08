package com.company.frontend;

import com.company.frontend.AST.*;
import com.company.frontend.IR.*;
import com.company.frontend.SymbolTable.SymbolInfo;
import com.company.frontend.SymbolTable.SymbolTable;
import com.company.frontend.SymbolTable.SymbolType;

import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

import static com.company.frontend.IR.CFGInst.isCompare;

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

    private HashMap<String, ASTFuncDeclNode> funcDeclMap = new HashMap<>();
    private boolean isInInline = false;
    private Stack<HashMap<String, CFGInstAddr>> inlineParamStack = new Stack<>();
    private Stack<CFGNode> inlineFuncAfterStack = new Stack<>();
    private Stack<CFGInstAddr> inlineFuncRet = new Stack<>();
    private final int inlineMaxDepth = 10;
    private final int inlineMaxOperations = 20;
    private boolean useGlobal = false;
    private HashMap<String, Integer> operationCount = new HashMap<>();

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
                visitExpr(node);
                CFGNode n = cfg.addNode();
                if (node.endNode.insts.isEmpty() || !node.endNode.insts.lastElement().isCompare()){
                    CFGInst c_inst = n.addInst(CFGInst.InstType.op_eq);
                    c_inst.addOperand(node.instAddr);
                    c_inst.addOperand(CFGInstAddr.newImmAddr(1)); // true
                }
                CFGInst j_inst = n.addInst(CFGInst.InstType.op_jcc);//jump if false
                j_inst.addOperand(CFGInstAddr.newLabelAddr(fN));
                node.endNode.linkTo(n);
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
    @Override
    public void visitCompilationUnitNode(ASTCompilationUnitNode node){
        CFGNode staticInit;
        staticInit = curStaticInit = cfg.addNode();
        staticInit.name = "_static_init";
        for (ASTStmtNode i : node.declList){
            if (i instanceof ASTFuncDeclNode) funcDeclMap.put("_func_"+ ((ASTFuncDeclNode) i).funcName, (ASTFuncDeclNode)i);
        }
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
        visitStmt(node.paramList);
        int paramIdx = 0;
        if (node.isMember) {
            CFGInst paramInst = node.startNode.addInst(CFGInst.InstType.op_rpara);
            curThisAddr = CFGInstAddr.newRegAddr();
            paramInst.addOperand(curThisAddr);
            paramInst.addOperand(CFGInstAddr.newImmAddr(paramIdx++));
        }
        if (node.paramList != null && !isInInline)
            for (ASTStmtNode i : node.paramList.stmtList) {
                CFGInst paramInst = node.startNode.addInst(CFGInst.InstType.op_rpara);
                paramInst.addOperand(((ASTDeclNode) i).reg);
                paramInst.addOperand(CFGInstAddr.newImmAddr(paramIdx++));
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
        if (isInInline){
            CFGInstAddr tmp = CFGInstAddr.newRegAddr();
            visitExpr(node.initExpr);
            node.startNode = node.initExpr.startNode;
            node.endNode = cfg.addNode();
            node.initExpr.endNode.linkTo(node.endNode);
            if (node.initExpr.instAddr != null) {
                CFGInst mvInst = node.endNode.addInst(CFGInst.InstType.op_mov);
                CFGInstAddr mvReg = tmp;
                mvInst.addOperand(mvReg);
                mvInst.addOperand(node.initExpr.instAddr);
            }
            inlineParamStack.peek().put(node.name, tmp);
        }
        else{
            visitExpr(node.initExpr);
            node.startNode = node.initExpr.startNode;
            node.endNode = cfg.addNode();
            node.initExpr.endNode.linkTo(node.endNode);
            if (node.initExpr.instAddr != null) {
                CFGInst mvInst = node.endNode.addInst(CFGInst.InstType.op_mov);
                CFGInstAddr mvReg = node.reg;
                mvInst.addOperand(mvReg);
                mvInst.addOperand(node.initExpr.instAddr);
            }
            if (curFuncName == null && curClassName == null) {
                int size = node.reg.addr1.getSize();//size of static
                if (node.initExpr.nodeType == ASTNodeType.p_int || node.initExpr.nodeType == ASTNodeType.p_bool) {
                    cfg.dataList.add(new CFGData("_global_"+node.name, ((ASTPrimNode)node.initExpr).intValue, true));
                } else {
                    if (!node.initExpr.isEmpty()) {
                        curStaticInit.linkTo(node.startNode);
                        curStaticInit = node.endNode;
                    }
                    cfg.dataList.add(new CFGData("_global_"+node.name, size, false));
                }
            }
        }

    }

    @Override
    public void visitExprNode(ASTExprNode node){
        if (node.exprList == null) {
            node.startNode = node.endNode = cfg.addNode();
            return;
        }
        if (node.nodeType == ASTNodeType.e_land || node.nodeType == ASTNodeType.e_lor || node.nodeType == ASTNodeType.e_not){
            CFGNode endNode = cfg.addNode();
            node.instAddr = CFGInstAddr.newRegAddr();
            genBoolExprNode(node.instAddr, node, endNode);
            node.endNode = endNode;
            return;
        }
        //if inst is in the origin order
        if  (node.nodeType != ASTNodeType.e_member && node.nodeType != ASTNodeType.e_call && node.nodeType != ASTNodeType.e_asgn){
            CFGNode last = node.startNode = cfg.addNode();
            for (ASTExprNode i : node.exprList) {
                visitExpr(i);
                last.linkTo(i.startNode);
                last = i.endNode;
            }
            node.endNode = last;
        }
        CFGInst.InstType type = CFGInst.InstType.op_nop;
        switch (node.nodeType){
            case e_add:
                if (node.resultType.equals(SymbolType.strSymbolType)){
                    CFGInst s1_inst = node.endNode.addInst(CFGInst.InstType.op_wpara);
                    s1_inst.addOperand(node.exprList.get(0).instAddr);
                    s1_inst.addOperand(CFGInstAddr.newImmAddr(0));
                    CFGInst s2_inst = node.endNode.addInst(CFGInst.InstType.op_wpara);
                    s2_inst.addOperand(node.exprList.get(1).instAddr);
                    s2_inst.addOperand(CFGInstAddr.newImmAddr(1));
                    CFGInst call_inst = node.endNode.addInst(CFGInst.InstType.op_call);
                    call_inst.addOperand(CFGInstAddr.newLabelAddr("_lib_strcat"));//use the lib
                    CFGInst mov_inst = node.endNode.addInst(CFGInst.InstType.op_mov);
                    CFGInstAddr ret_addr = CFGInstAddr.newRegAddr();
                    mov_inst.addOperand(ret_addr);
                    mov_inst.addOperand(CFGInstAddr.newRegAddr(-2));
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
            case e_inc_p:{
                CFGInst inc_inst = node.endNode.addInst(CFGInst.InstType.op_inc);
                inc_inst.addOperand(node.exprList.get(0).instAddr);
                node.instAddr = node.exprList.get(0).instAddr;
                return;
            }
            case e_inc_s:{
                CFGInst mv_inst = node.endNode.addInst(CFGInst.InstType.op_mov);
                node.instAddr = CFGInstAddr.newRegAddr();
                mv_inst.addOperand(node.instAddr);
                mv_inst.addOperand(node.exprList.get(0).instAddr);

                CFGInst inc_inst = node.endNode.addInst(CFGInst.InstType.op_inc);
                inc_inst.addOperand(node.exprList.get(0).instAddr);
                return;
            }
            case e_dec_p:{
                CFGInst dec_inst = node.endNode.addInst(CFGInst.InstType.op_dec);
                dec_inst.addOperand(node.exprList.get(0).instAddr);
                node.instAddr = node.exprList.get(0).instAddr;
                return;
            }
            case e_dec_s:{
                CFGInst mv_inst = node.endNode.addInst(CFGInst.InstType.op_mov);
                node.instAddr = CFGInstAddr.newRegAddr();
                mv_inst.addOperand(node.instAddr);
                mv_inst.addOperand(node.exprList.get(0).instAddr);

                CFGInst inc_inst = node.endNode.addInst(CFGInst.InstType.op_dec);
                inc_inst.addOperand(node.exprList.get(0).instAddr);
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
                    CFGInst s1_inst = node.endNode.addInst(CFGInst.InstType.op_wpara);
                    s1_inst.addOperand(node.exprList.get(0).instAddr);
                    s1_inst.addOperand(CFGInstAddr.newImmAddr(0));
                    CFGInst s2_inst = node.endNode.addInst(CFGInst.InstType.op_wpara);
                    s2_inst.addOperand(node.exprList.get(1).instAddr);
                    s2_inst.addOperand(CFGInstAddr.newImmAddr(1));
                    CFGInst call_inst = node.endNode.addInst(CFGInst.InstType.op_call);
                    call_inst.addOperand(CFGInstAddr.newLabelAddr("_lib_strcmp"));//use the lib
                    CFGInst cmp_inst = node.endNode.addInst(type);
                    cmp_inst.addOperand(CFGInstAddr.newRegAddr(-2));
                    cmp_inst.addOperand(CFGInstAddr.newImmAddr(0));
                    //result in reg(-2)
                    node.instAddr = CFGInstAddr.newRegAddr(-2);
                }
                else {
                    CFGInst inst = node.endNode.addInst(type);
                    for (ASTExprNode i : node.exprList) inst.addOperand(i.instAddr);
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
                return;
            //exprList has not been visited
            case e_member:
                //use member outside the class
                visitExpr(node.exprList.firstElement());
                node.startNode = node.exprList.firstElement().startNode;
                node.endNode = node.exprList.get(0).endNode;
                //if last element of member expression (such as a.b)
                if (node.exprList.get(1).instAddr != null && node.exprList.get(1).instAddr.a_type == CFGInstAddr.addrType.a_imm){
                    node.instAddr = CFGInstAddr.newMemAddr(node.exprList.get(0).instAddr, CFGInstAddr.newImmAddr(0), 8, node.exprList.get(1).instAddr.getNum());
                    //lit4 is the position in the class
                }
                else // (such as a.b.b.b)
                {
                    visitExpr(node.exprList.get(1));
                    node.exprList.firstElement().endNode.linkTo(node.exprList.get(1).startNode);
                    node.endNode = node.exprList.get(1).endNode;
                }
                return;
            case e_call:{
                //exprList = {functionName, paramList}
                type = CFGInst.InstType.op_call;
                visitExpr(node.exprList.get(0));
                node.startNode = node.exprList.get(0).startNode;
                CFGNode last = node.exprList.get(0).endNode;
                if (curProc != null) curProc.isCaller = true;
                //array.size();
                if (node.toNode.name.startsWith("_lib_array")){
                    node.endNode = cfg.addNode();
                    CFGInst mv_inst = node.endNode.addInst(CFGInst.InstType.op_mov);
                    node.instAddr = CFGInstAddr.newRegAddr();
                    mv_inst.addOperand(node.instAddr);
                    mv_inst.addOperand(CFGInstAddr.newMemAddr(node.exprList.get(0).exprList.get(0).instAddr, CFGInstAddr.newImmAddr(0),0,0));
                    node.startNode.linkTo(node.endNode);
                    return;
                }
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
                            last.linkTo(intExpr.startNode);
                            last = intExpr.endNode;
                            CFGNode tmp = cfg.addNode();
                            CFGInst param_inst = tmp.addInst( CFGInst.InstType.op_wpara);
                            param_inst.addOperand(intExpr.instAddr);
                            param_inst.addOperand(CFGInstAddr.newImmAddr(0));

                            CFGInst call_inst = tmp.addInst(CFGInst.InstType.op_call);
                            if (isLine && exprStack.empty()){
                                call_inst.addOperand(CFGInstAddr.newLabelAddr("_lib_printlnInt"));
                                isLine = false;
                            }
                            else call_inst.addOperand(CFGInstAddr.newLabelAddr("_lib_printInt"));
                            last.linkTo(tmp);
                            last = tmp;
                        }
                        else if (expr.nodeType == ASTNodeType.e_add){
                            for (int i = expr.exprList.size()-1; i>=0 ;i--)
                                exprStack.push(expr.exprList.get(i));
                        }
                        else {
                            last.linkTo(expr.startNode);
                            last = expr.endNode;
                            CFGNode tmp = cfg.addNode();
                            CFGInst param_inst = tmp.addInst( CFGInst.InstType.op_wpara);
                            param_inst.addOperand(expr.instAddr);
                            param_inst.addOperand(CFGInstAddr.newImmAddr(0));

                            CFGInst call_inst = tmp.addInst(CFGInst.InstType.op_call);
                            if (isLine && exprStack.empty()){
                                call_inst.addOperand(CFGInstAddr.newLabelAddr("_lib_println"));
                                isLine = false;
                            }
                            else call_inst.addOperand(CFGInstAddr.newLabelAddr("_lib_print"));
                            last.linkTo(tmp);
                            last = tmp;
                        }
                    }
                    node.endNode = last;
                    return;
                }

                CFGProcess callee = cfg.getProc(node.toNode.name);
                if (callee != null) callee.isCallee = true;

                if (!deserveInline(node.toNode.name)) {
                    int flag = 0; //if flag then ptAddr
                    CFGInstAddr ptAddr = null;
                    if (node.toNode.name.startsWith("_class")) {
                        flag = 1;
                        if (curThisAddr == null || node.exprList.get(0).nodeType == ASTNodeType.e_member)
                            ptAddr = node.exprList.get(0).exprList.get(0).instAddr;
                        else ptAddr = curThisAddr;
                    }
                    //str
                    if (node.toNode.name.startsWith("_lib_str")) {
                        flag = 1;
                        ptAddr = node.exprList.get(0).exprList.get(0).instAddr;
                    }
                    node.endNode = cfg.addNode();
                    if (node.exprList.size() > 1) {
                        Vector<ASTExprNode> exprList = node.exprList.get(1).exprList;
                        for (ASTExprNode i : exprList) {
                            visitExpr(i);
                            last.linkTo(i.startNode);
                            last = i.endNode;
                        }
                        for (int i = exprList.size() - 1; i >= 0; i--) {
                            ASTExprNode j = exprList.get(i);
                            CFGInst inst = node.endNode.addInst(CFGInst.InstType.op_wpara);
                            inst.addOperand(j.instAddr);
                            inst.addOperand(CFGInstAddr.newImmAddr(i + flag));
                        }
                    }
                    if (flag == 1) {
                        CFGInst inst = node.endNode.addInst(CFGInst.InstType.op_wpara);
                        assert (ptAddr != null);
                        inst.addOperand(ptAddr);
                        inst.addOperand(CFGInstAddr.newImmAddr(0));
                    }
                    CFGInst call_inst = node.endNode.addInst(CFGInst.InstType.op_call);
                    call_inst.addOperand(CFGInstAddr.newLabelAddr(node.toNode));

                    CFGInst mv_inst = node.endNode.addInst(CFGInst.InstType.op_mov);
                    CFGInstAddr ret_addr = CFGInstAddr.newRegAddr();
                    mv_inst.addOperand(ret_addr);
                    mv_inst.addOperand(CFGInstAddr.newRegAddr(-2));
                    node.instAddr = ret_addr;
                    last.linkTo(node.endNode);
                }
                else {
                    ASTFuncDeclNode funcDecl = funcDeclMap.get(node.toNode.name);
                    CFGNode inlineFuncBody = cfg.addNode();
                    CFGNode inlineFuncAfter = cfg.addNode();
                    inlineFuncAfterStack.push(inlineFuncAfter);

                    if (node.exprList.size() > 1) {
                        Vector<ASTExprNode> exprList = node.exprList.get(1).exprList;
                        inlineParamStack.push(new HashMap<>());
                        for (ASTExprNode i : exprList) {
                            visitExpr(i);
                            last.linkTo(i.startNode);
                            last = i.endNode;
                        }
                        for (int i = 0; i < exprList.size(); i++) {
                            CFGInst mov_inst = inlineFuncBody.addInst(CFGInst.InstType.op_mov);
                            CFGInstAddr tmp = CFGInstAddr.newRegAddr();
                            mov_inst.operands.add(tmp);
                            mov_inst.operands.add(exprList.get(i).instAddr);
                            inlineParamStack.peek().put(((ASTDeclNode)funcDecl.paramList.stmtList.get(i)).name, tmp);
                        }
                    }
                        last.linkTo(inlineFuncBody);
                        last = inlineFuncBody;
                        boolean oldInline = isInInline;
                        isInInline = true;

                        CFGInstAddr ret_addr = CFGInstAddr.newRegAddr();
                        node.instAddr = ret_addr;
                        inlineFuncRet.push(ret_addr);

                        for (ASTStmtNode i : funcDecl.funcBody.stmtList){
                            visitStmt(i);
                            last.linkTo(i.startNode);
                            last = i.endNode;
                        }
                        //TODO
                       // lastNode.linkTo(inlineFuncAfter);
                        inlineFuncAfterStack.pop();
                        inlineFuncRet.pop();
                        isInInline = oldInline;
                        node.endNode = inlineFuncAfter;
                }
                return;
            }
            case e_asgn:{
                if (SymbolType.boolSymbolType.equals(node.exprList.get(1).resultType) && !(node.exprList.get(1) instanceof ASTPrimNode)){
                    //logical assign
                    visitExpr(node.exprList.firstElement());
                    node.startNode = node.exprList.get(0).startNode;
                    node.endNode = cfg.addNode();
                    genBoolExprNode(node.exprList.get(0).instAddr, node.exprList.get(1), node.endNode);
                    node.startNode.linkTo(node.exprList.get(1).startNode);
                }
                else {
                    CFGNode last = node.startNode = cfg.addNode();
                    for (ASTExprNode i: node.exprList) {
                        visitExpr(i);
                        last.linkTo(i.startNode);
                        last = i.endNode;
                    };
                    node.endNode = cfg.addNode();
                    last.linkTo(node.endNode);
                    CFGInst inst = node.endNode.addInst(CFGInst.InstType.op_mov);
                    inst.addOperand(node.exprList.get(0).instAddr);
                    inst.addOperand(node.exprList.get(1).instAddr);
                }
                node.instAddr = node.exprList.get(0).instAddr;
            }
            return;
            default:
                return;
        }
        assert type != CFGInst.InstType.op_nop;
        if (node.exprList.size() == 2 && node.exprList.get(0).instAddr.isConst() && node.exprList.get(1).instAddr.isConst()){
            node.instAddr = CFGInstAddr.newImmAddr(getConst(type, node.exprList.get(0).instAddr.getConst(), node.exprList.get(1).instAddr.getConst()));
            return;
        }
        if (node.exprList.size() == 1 && node.exprList.get(0).instAddr.isConst()){
            node.instAddr = CFGInstAddr.newImmAddr(getConst(type, node.exprList.get(0).instAddr.getConst(), 0));
            return;
        }
        CFGInst inst = node.endNode.addInst(type);
        node.instAddr = CFGInstAddr.newRegAddr();
        inst.addOperand(node.instAddr);
        for (ASTExprNode i : node.exprList) inst.addOperand(i.instAddr);
    }

    private int getConst(CFGInst.InstType type, int const1, int const2) {
        switch (type){
            case op_add:
                return const1 +const2;
            case op_sub:
                return const1-const2;
            case op_mult:
                return const1 * const2;
            case op_div:
                return const1/const2;
            case op_mod:
                return const1 % const2;
            case op_and:
                return const1 & const2;
            case op_or:
                return const1 | const2;
            case op_xor:
                return const1 ^ const2;
            case op_eq:
                return const1 == const2?1:0;
            case op_ne:
                return const1 == const2?0:1;
            case op_ge:
                return const1 >= const2?1:0;
            case op_gt:
                return const1 > const2?1:0;
            case op_lt:
                return const1 <const2?1:0;
            case op_le:
                return const1 <= const2 ? 1:0;
            case op_neg:
                return -const1;
            case op_pos:
                return const1;
            case op_not:
                return ~const1;
            case op_shl:
                return const1 << const2;
            case op_shr:
                return const1 >> const2;
                default:
                    return 0;
        }
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
                CFGNode stepNode = cfg.addNode();
                node.endNode = cfg.addNode();
                curLoopBreakNode.push(node.endNode);
                curLoopContNode.push(stepNode);

                for (int i = 0; i < node.stmtList.size(); i++){
                    if (i == 1) continue;
                    visitStmt(node.stmtList.get(i));
                }

                CFGNode n =  node.stmtList.get(0).startNode;
                visitLogicalExprNode((ASTExprNode)node.stmtList.get(1), node.stmtList.get(3).startNode, node.endNode);
                node.stmtList.get(0).endNode.linkTo(node.stmtList.get(1).startNode);

                if (node.stmtList.get(3).endNode != null)
                    node.stmtList.get(3).endNode.linkTo(stepNode);
                stepNode.linkTo(node.stmtList.get(2).startNode);
                node.stmtList.get(2).endNode.linkTo(node.stmtList.get(1).startNode);

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
                if (isInInline){
                    CFGNode inlineFuncAfter = inlineFuncAfterStack.peek();
                    if (((ASTExprNode)node.stmtList.firstElement()).instAddr != null) {
                        node.startNode = node.stmtList.firstElement().startNode;
                        node.stmtList.firstElement().endNode.linkTo(n);
                        CFGInst mv_inst = n.addInst(CFGInst.InstType.op_mov);
                        mv_inst.operands.add(inlineFuncRet.peek());
                        mv_inst.operands.add(((ASTExprNode)node.stmtList.firstElement()).instAddr);
                    }
                    else {
                        node.startNode = n;
                    }
                    n.linkTo(inlineFuncAfter);
                    node.endNode = null;
                    break;
                }
                if (((ASTExprNode)node.stmtList.firstElement()).instAddr != null){
                    node.startNode = node.stmtList.firstElement().startNode;
                    node.stmtList.firstElement().endNode.linkTo(n);
                    CFGInst mv_inst = n.addInst(CFGInst.InstType.op_mov);
                    mv_inst.addOperand(CFGInstAddr.newRegAddr(-2));
                    mv_inst.addOperand(((ASTExprNode)node.stmtList.firstElement()).instAddr);
                }
                else {
                    node.startNode = n;
                }
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
                //TODO
                if (isInInline) {
                    node.instAddr = inlineParamStack.peek().get(node.stringValue);
                }
                else
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
        node.startNode = node.endNode = cfg.addNode();
    }

    @Override
    public void visitCreatorNode(ASTCreatorNode node) {
        node.startNode = cfg.addNode();
        if (node.exprList != null)
            for (ASTExprNode i : node.exprList) visitExpr(i);
        if (node.type.dimension > 0){
            CFGInstAddr startAddr = CFGInstAddr.newStackAddr(8);
            CFGInstAddr tmpAddr = startAddr;
            int iDim = 0;
            CFGNode last = node.startNode;
            for (ASTExprNode i : node.exprList){
                if (i.isEmpty()) break;
                ++iDim;
                last.linkTo(i.startNode);
                last = i.endNode;
                CFGNode tmp = cfg.addNode();
                CFGInst mv_inst = tmp.addInst(CFGInst.InstType.op_mov);
                assert tmpAddr!=null;
                mv_inst.addOperand(tmpAddr);
                mv_inst.addOperand(i.instAddr);
                last.linkTo(tmp);
                last = tmp;
                tmpAddr = CFGInstAddr.newStackAddr(8);
            }
            {
                node.endNode = cfg.addNode();
                last.linkTo(node.endNode);
                CFGInstAddr tmpReg = CFGInstAddr.newRegAddr(-4);
                CFGInst lea_inst = node.endNode.addInst(CFGInst.InstType.op_lea);
                lea_inst.addOperand(tmpReg);
                lea_inst.addOperand(startAddr);
                CFGInst param_inst = node.endNode.addInst(CFGInst.InstType.op_wpara);
                param_inst.addOperand(tmpReg);
                param_inst.addOperand(CFGInstAddr.newImmAddr(1));
            }
            {
                CFGInst param_inst = node.endNode.addInst(CFGInst.InstType.op_wpara);
                param_inst.addOperand(CFGInstAddr.newImmAddr(iDim));
                param_inst.addOperand(CFGInstAddr.newImmAddr(0));
            }
            CFGInst call_inst = node.endNode.addInst(CFGInst.InstType.op_call);
            call_inst.addOperand(CFGInstAddr.newLabelAddr("_lib_alloc"));
            node.instAddr = CFGInstAddr.newRegAddr(-2);
        }
        else {
            CFGInst param_inst = node.startNode.addInst(CFGInst.InstType.op_wpara);
            param_inst.addOperand(CFGInstAddr.newImmAddr(node.resultType.getClassMemSize()));
            param_inst.addOperand(CFGInstAddr.newImmAddr(0));
            CFGInst call_inst = node.startNode.addInst(CFGInst.InstType.op_call);
            call_inst.addOperand(CFGInstAddr.newLabelAddr("malloc"));
            if (node.hasConstructor){
                node.instAddr = CFGInstAddr.newRegAddr();
                CFGInst mv_inst = node.startNode.addInst(CFGInst.InstType.op_mov);
                mv_inst.addOperand(node.instAddr);
                mv_inst.addOperand(CFGInstAddr.newRegAddr(-2));
                CFGInst this_inst = node.startNode.addInst(CFGInst.InstType.op_wpara);
                this_inst.addOperand(CFGInstAddr.newRegAddr(-2));
                this_inst.addOperand(CFGInstAddr.newImmAddr(0));
                CFGInst cons_inst = node.startNode.addInst(CFGInst.InstType.op_call);
                cons_inst.addOperand(CFGInstAddr.newLabelAddr("_class_"+node.type.className + "._init"));
            }
            else {
                node.instAddr = CFGInstAddr.newRegAddr(-2);
            }
            node.endNode = node.startNode;
        }
        //array of class = array of point
    }


    private boolean deserveInline(String funcName){
        boolean openInline = false;
        if (!openInline) return false;
        if (!funcDeclMap.containsKey(funcName)) return false;
        ASTFuncDeclNode node = funcDeclMap.get(funcName);
        if (node.isMember) return false;
        int cnt;
        useGlobal = false;
        if (operationCount.containsKey(funcName)) cnt = operationCount.get(funcName);
        else {
            cnt = countOperations(node);
            operationCount.put(funcName,cnt);
        }
        if (cnt > inlineMaxOperations || useGlobal) return false;
        if (inlineParamStack.size() >= inlineMaxDepth) return false;
        return true;
    }
    private int countOperations(ASTExprNode node){
        if (node == null || node.nodeType == ASTNodeType.e_empty) return 0;
        if (node instanceof ASTCreatorNode) return node.resultType.arrayDim;
        if (node instanceof ASTPrimNode) {
            if (node.nodeType == ASTNodeType.p_id && node.instAddr.a_type != CFGInstAddr.addrType.a_reg)
                useGlobal = true;
            return 0;
        }
        int cnt = 0;
        for (ASTExprNode i: node.exprList) cnt += countOperations(i);
        return cnt + 1;
    }
    private int countOperations(ASTStmtNode node){
        if (node == null || node.nodeType == ASTNodeType.s_empty) return 0;
        if (node instanceof ASTExprNode) return countOperations((ASTExprNode)node);
        if (node instanceof ASTDeclNode) return countOperations(((ASTDeclNode) node).initExpr) + 1;
        int cnt = 0;
        for (ASTStmtNode i: node.stmtList) cnt += countOperations(i);
        return cnt;
    }
    private int countOperations(ASTFuncDeclNode node) {
        int cnt = 0;
        for (ASTStmtNode i: node.funcBody.stmtList) cnt += countOperations(i);
        return cnt;
    }
}
