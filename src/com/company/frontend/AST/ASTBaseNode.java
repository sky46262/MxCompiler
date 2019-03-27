package com.company.frontend.AST;
import com.company.common.Position;

public class ASTBaseNode {
    public Position pos;
    public ASTNodeType nodeType;
    public ASTBaseNode(Position _pos, ASTNodeType _type){
        pos = _pos;
        nodeType = _type;
    }
}
/* Extend Structure
    enum NodeType
    Base
        CompilationUnit
        Stmt
            Expr
                Prim
            Decl
            FuncDecl
            ClassDecl
        Type

   Include Structure
    CompilationUnit
        Stmt
    FuncDel
        Expr
        Type
        Stmt
    ClassDel
        Stmt
    Decl
        Type
        Expr
    Stmt
        Stmt
    Expr
        Expr
 */