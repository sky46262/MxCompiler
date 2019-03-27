package com.company.frontend;

import com.company.frontend.AST.*;

public class ASTBaseVisitor implements ASTAbstractVisitor {
    @Override public void visitCompilationUnitNode(ASTCompilationUnitNode node){}
    @Override public void visitClassDeclNode(ASTClassDeclNode node){}
    @Override public void visitFuncDeclNode(ASTFuncDeclNode node){}
    @Override public void visitVarDeclNode(ASTDeclNode node){}
    @Override public void visitTypeNode(ASTTypeNode node){}
    @Override public void visitStmtNode(ASTStmtNode node){}
    @Override public void visitExprNode(ASTExprNode node){}
    @Override public void visitPrimNode(ASTPrimNode node){}
    @Override public void visitCreatorNode(ASTCreatorNode node){}
    public void visitStmt(ASTStmtNode node) {
        if(node==null) return;
        if (node instanceof ASTClassDeclNode) visitClassDeclNode((ASTClassDeclNode)node);
        else if (node instanceof ASTFuncDeclNode) visitFuncDeclNode((ASTFuncDeclNode)node);
        else if (node instanceof ASTDeclNode) visitVarDeclNode((ASTDeclNode)node);
        else if (node instanceof ASTExprNode) visitExpr((ASTExprNode)node);
        else visitStmtNode(node);
    }

    public void visitExpr(ASTExprNode node) {
        if (node==null) return;
        if (node instanceof ASTPrimNode) visitPrimNode((ASTPrimNode)node);
        else if (node instanceof ASTCreatorNode) visitCreatorNode((ASTCreatorNode)node);
        else visitExprNode(node);
    }
}
