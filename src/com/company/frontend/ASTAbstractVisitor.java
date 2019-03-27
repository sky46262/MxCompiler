package com.company.frontend;

import com.company.frontend.AST.*;

public interface ASTAbstractVisitor  {
    void visitCompilationUnitNode(ASTCompilationUnitNode node);
    void visitClassDeclNode(ASTClassDeclNode node);
    void visitFuncDeclNode(ASTFuncDeclNode node);
    void visitVarDeclNode(ASTDeclNode node);
    void visitTypeNode(ASTTypeNode node);
    void visitStmtNode(ASTStmtNode node);
    void visitExprNode(ASTExprNode node);
    void visitPrimNode(ASTPrimNode node);
    void visitCreatorNode(ASTCreatorNode node);
}
