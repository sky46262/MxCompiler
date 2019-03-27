package com.company.frontend.AST;

import com.company.common.Position;

public class ASTDeclNode extends ASTStmtNode{
    public String name;
    public ASTExprNode initExpr;
    public ASTTypeNode type;
    public ASTDeclNode(Position pos, ASTNodeType _type, String _n, ASTExprNode _e, ASTTypeNode _t){
        super(pos, _type, null);
        name = _n;
        initExpr = _e;
        type = _t;
    }
}
