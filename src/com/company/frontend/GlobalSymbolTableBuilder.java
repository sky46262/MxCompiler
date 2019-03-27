package com.company.frontend;


import com.company.common.CompileError;
import com.company.frontend.AST.*;
import com.company.frontend.SymbolTable.SymbolInfo;
import com.company.frontend.SymbolTable.SymbolTable;
import com.company.frontend.SymbolTable.SymbolType;

import java.util.Vector;

public class GlobalSymbolTableBuilder extends ASTBaseVisitor{
    private SymbolTable ST;
    private CompileError ce;
    private String currentClass = null;
    private String currentFunc = null;
    private Vector<SymbolType> currentPara = new Vector<>();

    public GlobalSymbolTableBuilder(SymbolTable _s,  CompileError _c){
        ST = _s;
        ce = _c;
    }

   private String getScopeName(){
        return (currentClass == null?"":(currentClass + '.'));
    }

    @Override
    public void visitCompilationUnitNode(ASTCompilationUnitNode node) {
         ST.push("global");

         Vector<SymbolType> list = new Vector<>();
         list.add(SymbolType.voidSymbolType);
         list.add(SymbolType.strSymbolType);
        ST.pushSymbol("print", new SymbolType(new Vector<>(list)), node);
        ST.pushSymbol("println", new SymbolType(new Vector<>(list)), node);
        list.clear();
        list.add(SymbolType.strSymbolType);
        ST.pushSymbol("getString", new SymbolType(new Vector<>(list)), node);
        list.add(SymbolType.intSymbolType);
        ST.pushSymbol("toString", new SymbolType(new Vector<>(list)), node);
        list.add(SymbolType.intSymbolType);
        ST.pushSymbol("string.substring", new SymbolType(new Vector<>(list)), node);
        list.clear();
        list.add(SymbolType.intSymbolType);
        ST.pushSymbol("getInt", new SymbolType(new Vector<>(list)), node);
        ST.pushSymbol("array.size", new SymbolType(new Vector<>(list)), node);
        ST.pushSymbol("string.length", new SymbolType(new Vector<>(list)), node);
        ST.pushSymbol("string.parseInt", new SymbolType(new Vector<>(list)), node);
        list.add(SymbolType.intSymbolType);
        ST.pushSymbol("string.ord", new SymbolType(new Vector<>(list)), node);
        list.clear();

        for (ASTStmtNode i: node.declList) visitStmt(i);

        SymbolInfo mainInfo = ST.findSymbol("main");
        if (mainInfo == null) ce.add(CompileError.ceType.ce_nodecl, "main", node.pos);
        else if (!mainInfo.type.memList.firstElement().equals(SymbolType.intSymbolType))
            ce.add(CompileError.ceType.ce_type, "return type of main", node.pos);

    }

    @Override
    public void visitStmtNode(ASTStmtNode node){
        for (ASTStmtNode i : node.StmtList) visitStmt(i);
    }

    @Override
    public void visitFuncDeclNode(ASTFuncDeclNode node){
        if (currentClass != null) node.setMember();
        currentPara.clear();
        currentFunc = node.funcName;
        currentPara.add(new SymbolType(node.returnType));
        visitStmt(node.paramList);
        if (node.isConstructor) {
            if (!node.funcName.equals(currentClass))
                ce.add(CompileError.ceType.ce_invalid_constructor, "the name of constructor must be" + currentClass, node.pos);
            else {
                SymbolType t = new SymbolType(new Vector<>(currentPara));
                ST.pushSymbol(getScopeName() + "_init",t ,node);
                SymbolInfo curClass = ST.findSymbol(currentClass);
                curClass.type.memList.add(t);
            }
        }
        else ST.pushSymbol(getScopeName()+node.funcName, new SymbolType(new Vector<>(currentPara)), node);
        currentFunc = null;
    }

    @Override
    public void visitClassDeclNode(ASTClassDeclNode node) {
        ST.pushSymbol(node.className, new SymbolType(SymbolType.symbolType.CLASS, node.className, 0, new Vector<>()),node);
        currentClass = node.className;
        for (ASTStmtNode i : node.StmtList) visitStmt(i);
        currentClass = null;
    }

    @Override
    public void visitVarDeclNode(ASTDeclNode node) {
        if (currentFunc != null) {
            currentPara.add(new SymbolType(node.type));
        }
        else if (currentClass != null){
            ST.pushSymbol(getScopeName() + node.name, new SymbolType(node.type), node);
        }
    }

}