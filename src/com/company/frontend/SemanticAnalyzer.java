package com.company.frontend;

import com.company.common.CompileError;
import com.company.frontend.AST.*;
import com.company.frontend.SymbolTable.SymbolInfo;
import com.company.frontend.SymbolTable.SymbolTable;
import com.company.frontend.SymbolTable.SymbolType;

import java.util.Vector;

public class SemanticAnalyzer extends ASTBaseVisitor {
    private SymbolTable ST;
    private CompileError ce;
    private String currentClass;
    private String currentFunc;
    private StringBuilder currentMember = new StringBuilder();
   // private boolean hasReturn;
    private SymbolType currentReturnType;
    private int loopCnt;

    public SemanticAnalyzer(SymbolTable _s, CompileError _c){
        ST = _s;
        ce = _c;
    }
    private String getScopeName(String name){
        return (currentClass == null ? "" : currentClass + ".") + name;
    }
    private boolean checkExprType(ASTExprNode node, SymbolType type){
        if (node == null || type == null) return false;
        if (node.resultType == null) return false;
        return node.resultType.equals(type);
    }
    private boolean checkSubExprType(ASTExprNode node, SymbolType type, int num){
        if (num == 1) return checkExprType(node.exprList.get(0), type);
        else if (num == 2) return checkExprType(node.exprList.get(1),type) && checkExprType(node.exprList.get(0),type);
        return false;
    }
    private boolean checkFunc(Vector<SymbolType> memList, Vector<SymbolType> params){
        for (int i = 0; i < params.size();i++){
            if (memList.get(i+1) == null) return false;
            if (!memList.get(i+1).equals(params.get(i))){
                return false;
            }
        }

        return true;
    }
    private boolean checkLvalue(ASTExprNode node){
        if (node instanceof ASTPrimNode) {
            if (node.nodeType != ASTNodeType.p_id) return false;
            //resultType = FUNC ??
            return true;
        }
        switch (node.nodeType){
            case e_idx:
                return true;
            case e_member:
                return checkLvalue(node.exprList.get(1));
            case e_inc_p:
            case e_dec_p:
                return true;
            default:
                return false;
        }
    }
    @Override
    public void visitCompilationUnitNode(ASTCompilationUnitNode node) {
        for (ASTStmtNode i:node.declList) visitStmt(i);
        //ST.pop()???
    }
    @Override
    public void visitClassDeclNode(ASTClassDeclNode node) {
        currentClass = node.className;
        ST.push(node.className);
        for (ASTStmtNode i : node.stmtList)
            if (i instanceof ASTFuncDeclNode) visitFuncDecl_m((ASTFuncDeclNode)i);
            else if (i instanceof ASTDeclNode) visitVarDecl_m((ASTDeclNode)i);

        for (ASTStmtNode i : node.stmtList) visitStmt(i);
        currentClass = null;
        ST.pop();
    }
    public void visitFuncDecl_m(ASTFuncDeclNode node){
        if (!node.isConstructor)
        ST.pushSymbol(node.funcName, ST.findSymbol(getScopeName(node.isConstructor?"_init":node.funcName)));
    }
    public void visitVarDecl_m(ASTDeclNode node){
        ST.pushSymbol(node.name,new SymbolType(node.type), node, currentClass,currentFunc);
    }
    @Override
    public void visitFuncDeclNode(ASTFuncDeclNode node) {
        visitTypeNode(node.returnType);
        currentFunc = node.funcName;

        ST.push(node.funcName);
        //hasReturn = false;
        currentReturnType = new SymbolType(node.returnType);
        visitStmt(node.paramList);
        for (ASTStmtNode i: node.funcBody.stmtList) visitStmt(i);
       // if (!hasReturn && currentReturnType.type != SymbolType.symbolType.VOID)
          //  ce.add(CompileError.ceType.ce_noreturn, node.funcName, node.pos);
        //warning
        currentFunc = null;
        currentReturnType = null;
        ST.pop();
    }
    @Override
    public void visitVarDeclNode(ASTDeclNode node) {
        visitTypeNode(node.type);
        if (node.initExpr != null) {
            visitExpr(node.initExpr);
            if (!node.initExpr.isEmpty() && !checkExprType(node.initExpr, new SymbolType(node.type)))
                ce.add(CompileError.ceType.ce_type, "invalid init:"+node.name, node.pos);
        }
        //if not in class or in function
        if (currentClass == null || currentFunc != null)
            ST.pushSymbol(node.name,new SymbolType(node.type), node,currentClass,currentFunc);
    }
    //check type
    @Override
    public void visitTypeNode(ASTTypeNode node){
        if (node.nodeType == ASTNodeType.t_void && node.dimension != 0) ce.add(CompileError.ceType.ce_invalid_type,"void",node.pos);
        if (node.nodeType == ASTNodeType.t_class){
            SymbolInfo info = ST.findSymbol(node.className);
            if (info == null || info.type.type != SymbolType.symbolType.CLASS)
                ce.add(CompileError.ceType.ce_invalid_type, node.className, node.pos);
        }
    }

    @Override
    public void visitStmtNode(ASTStmtNode node){
        switch (node.nodeType){
            case s_block:
                ST.push(Integer.toString(node.hashCode()));
                for (ASTStmtNode i: node.stmtList) visitStmt(i);
                ST.pop();
                return;
            case s_continue:
            case s_break:
                if (loopCnt == 0) ce.add(CompileError.ceType.ce_outofloop, "can't break out of loop", node.pos);
                return;
            case s_return:
                if (currentFunc == null) ce.add(CompileError.ceType.ce_type, "no need to return", node.pos);
                visitStmt(node.stmtList.get(0));
                if (!checkExprType((ASTExprNode) node.stmtList.get(0), currentReturnType))
                    ce.add(CompileError.ceType.ce_type, "return:" + currentFunc, node.pos);
                //hasReturn = true;
                return;
            case s_paramlist:
                for (ASTStmtNode i: node.stmtList) visitStmt(i);
                return;
            case s_if:
                for (ASTStmtNode i: node.stmtList) visitStmt(i);
                if (!checkExprType((ASTExprNode) node.stmtList.get(0), SymbolType.boolSymbolType))
                    ce.add(CompileError.ceType.ce_type, "if", node.pos);
                return;
            case s_for:
                ++loopCnt;
                for (ASTStmtNode i: node.stmtList) visitStmt(i);
                --loopCnt;
                if (node.stmtList.get(1) != null && !checkExprType((ASTExprNode) node.stmtList.get(1), SymbolType.boolSymbolType))
                    ce.add(CompileError.ceType.ce_type, "for", node.pos);
                return;
            case s_while:
                ++loopCnt;
                for (ASTStmtNode i: node.stmtList) visitStmt(i);
                --loopCnt;
                if (!checkExprType((ASTExprNode) node.stmtList.get(0), SymbolType.boolSymbolType))
                    ce.add(CompileError.ceType.ce_type, "while", node.pos);
                return;
            default:
                return;
        }
    }


    @Override
    public void visitExprNode(ASTExprNode node){
        if (node.nodeType != ASTNodeType.e_member) {
            if (node.exprList != null)
            for (ASTExprNode i : node.exprList) visitExpr(i);
        }
        switch (node.nodeType) {
            case e_add:
                if (checkSubExprType(node,SymbolType.strSymbolType,2))
                    node.resultType = SymbolType.strSymbolType;
                else if (checkSubExprType(node, SymbolType.intSymbolType,2))
                    node.resultType = SymbolType.intSymbolType;
                else ce.add(CompileError.ceType.ce_type, "invalid type of:"+node.nodeType.toString(),node.pos);
                break;
            case e_ge:
            case e_gt:
            case e_le:
            case e_lt:
                if (checkSubExprType(node,SymbolType.intSymbolType, 2))
                node.resultType = SymbolType.boolSymbolType;
                else if (checkSubExprType(node,SymbolType.strSymbolType, 2))
                    node.resultType = SymbolType.boolSymbolType;
                else ce.add(CompileError.ceType.ce_type,"invalid type of:"+node.nodeType.toString(),node.pos);
                break;
            case e_band:
            case e_bor:
            case e_bxor:
            case e_sub:
            case e_div:
            case e_shl:
            case e_shr:
            case e_mod:
            case e_mult:
                if (checkSubExprType(node,SymbolType.intSymbolType, 2))
                    node.resultType = SymbolType.intSymbolType;
                else ce.add(CompileError.ceType.ce_type,"invalid type of:"+node.nodeType.toString(),node.pos);
                break;
            case e_asgn:
                if (!checkLvalue(node.exprList.get(0)))
                    ce.add(CompileError.ceType.ce_lvalue, "not lvalue",node.pos);
                if (node.exprList.get(0).resultType.equals(node.exprList.get(1).resultType))
                    node.resultType = node.exprList.get(0).resultType;
                else ce.add(CompileError.ceType.ce_type, "invalid type of:" + node.nodeType.toString(), node.pos);
                break;
            case e_pos:
            case e_neg:
            case e_bneg:
                if (checkSubExprType(node,SymbolType.intSymbolType, 1))
                    node.resultType = SymbolType.intSymbolType;
                else ce.add(CompileError.ceType.ce_type,"invalid type of:"+node.nodeType.toString(),node.pos);
                break;
            case e_land:
            case e_lor:
                if (checkSubExprType(node,SymbolType.boolSymbolType,2))
                    node.resultType = SymbolType.boolSymbolType;
                else ce.add(CompileError.ceType.ce_type,"invalid type of:"+node.nodeType.toString(),node.pos);
                break;
            case e_dec_p:
            case e_dec_s:
            case e_inc_p:
            case e_inc_s:
                if (!checkLvalue(node.exprList.get(0)))
                    ce.add(CompileError.ceType.ce_lvalue, "not lvalue",node.pos);
                if (checkSubExprType(node, SymbolType.intSymbolType, 1))
                    node.resultType = SymbolType.intSymbolType;
                else ce.add(CompileError.ceType.ce_type, "invalid type of:" + node.nodeType.toString(), node.pos);
                break;
            case e_eq:
            case e_ne:
                if (node.exprList.get(0).resultType.equals(node.exprList.get(1).resultType))
                    node.resultType = SymbolType.boolSymbolType;
                else ce.add(CompileError.ceType.ce_type,"invalid type of:"+node.nodeType.toString(),node.pos);
                break;
            case e_not:
                if (checkSubExprType(node,SymbolType.boolSymbolType,1))
                    node.resultType = SymbolType.boolSymbolType;
                else ce.add(CompileError.ceType.ce_type,"invalid type of:"+node.nodeType.toString(),node.pos);
                break;
            case e_idx:
                if (node.exprList.get(0).resultType.arrayDim == 0)
                    ce.add(CompileError.ceType.ce_type, "invalid dim", node.exprList.get(0).pos);
                else if (checkExprType(node.exprList.get(1),SymbolType.intSymbolType))
                    node.resultType = node.exprList.get(0).resultType.getDerefType();
                else ce.add(CompileError.ceType.ce_type, "invalid idx", node.exprList.get(1).pos);
                break;
            case e_call:
                if (checkSubExprType(node,SymbolType.funcSymbolType,1)){
                    Vector<SymbolType> params = new Vector<>();
                    if (node.exprList.size() > 1)
                    for (ASTExprNode i: node.exprList.get(1).exprList) params.add(i.resultType);
                    SymbolType fType = node.exprList.get(0).resultType;
                    if (fType.memList.size()-1  == params.size()){
                        if (checkFunc(fType.memList, params)) {
                            node.resultType = fType.memList.firstElement();
                            node.toNode = node.exprList.firstElement().toNode;
                            //firstElement of exprList is the name of function
                        }
                        else ce.add(CompileError.ceType.ce_type, "wrong argument type", node.pos);
                    }
                    else ce.add(CompileError.ceType.ce_type, "wrong argumentList len", node.pos);
                }
                else ce.add(CompileError.ceType.ce_type,"invalid type of:"+node.nodeType.toString(),node.pos);
                break;
            case e_member:
                visitExpr(node.exprList.firstElement());
                if (node.exprList.firstElement().resultType.arrayDim > 0)
                    currentMember.append("array.");
                else if (checkSubExprType(node, SymbolType.strSymbolType,1))
                    currentMember.append("string.");
                else if (node.exprList.firstElement().resultType.type == SymbolType.symbolType.CLASS){
                    currentMember.append(node.exprList.firstElement().resultType.name + ".");
                }
                else
                    ce.add(CompileError.ceType.ce_nodecl, "no class " + node.exprList.firstElement().resultType.name, node.pos);
                if (node.exprList.get(1).nodeType == ASTNodeType.e_member || node.exprList.get(1).nodeType == ASTNodeType.p_id) {
                    visitExpr(node.exprList.get(1));
                    node.resultType = node.exprList.get(1).resultType;
                    node.instAddr = node.exprList.get(1).instAddr;
                    node.toNode = node.exprList.get(1).toNode;
                }
                else ce.add(CompileError.ceType.ce_type, "no a member of " + node.exprList.firstElement().resultType.name, node.pos);
                break;
            case e_empty:
                node.resultType = SymbolType.voidSymbolType;
           // case e_creator:
            //  node.resultType = node.exprList.get(0).resultType;
            //break;
            default:
        }

    }

    @Override
    public void visitCreatorNode(ASTCreatorNode node) {
        if (node.type.dimension > 0){
            for (ASTExprNode i :node.exprList){
                visitExpr(i);
                if (!i.resultType.equals(SymbolType.intSymbolType)) {
                    ce.add(CompileError.ceType.ce_type, "creator : index", i.pos);
                    break;
                }
            }
        }
        if (node.type.className != null){
            SymbolInfo symbol = ST.findSymbol(node.type.className);
            if (symbol == null) ce.add(CompileError.ceType.ce_nodecl, "class creator:"+node.type.className, node.pos);
            if (!symbol.type.equals(SymbolType.classSymbolType)) ce.add(CompileError.ceType.ce_type, "not class creator:"+node.type.className, node.pos);
            if (ST.findSymbol(node.type.className+"_init") != null) node.hasConstructor = true;
        }
        node.resultType = new SymbolType(node.type);
        //todo  array of class to be tested
    }

    @Override
    public void visitPrimNode(ASTPrimNode node) {
        switch (node.nodeType){
            case p_bool:
                node.resultType = SymbolType.boolSymbolType;
                break;
            case p_int:
                node.resultType = SymbolType.intSymbolType;
                break;
            case p_str:
                node.resultType = SymbolType.strSymbolType;
                break;
            case p_null:
                node.resultType = SymbolType.nullSymbolType;
                break;
            case p_id:
                String Name;
                /*
                if (currentMember.length() == 0) Name = getScopeName(node.stringValue);
                else {
                    Name = currentMember.toString() + node.stringValue;
                    currentMember = new StringBuilder();
                }
                SymbolInfo symbol = ST.findSymbol(Name);
                if (symbol == null) symbol = ST.findSymbol(node.stringValue);
                */
                    Name = currentMember.toString() + node.stringValue;
                    currentMember = new StringBuilder();
                SymbolInfo symbol = ST.findSymbol(Name);
                if (symbol == null) symbol = ST.findSymbol(getScopeName(node.stringValue));
                if (symbol == null)
                    ce.add(CompileError.ceType.ce_nodecl, "no decl of" + Name, node.pos);
                else {
                    node.resultType = symbol.type;
                    node.instAddr = symbol.reg;
                    node.toNode= symbol.startNode;
                }
                break;

            case p_this:
                if (currentClass == null) ce.add(CompileError.ceType.ce_outofclass, "this", node.pos);
                else node.resultType = new SymbolType(SymbolType.symbolType.CLASS, currentClass, 0);
                //replace by curClass
                break;
        }
    }
}