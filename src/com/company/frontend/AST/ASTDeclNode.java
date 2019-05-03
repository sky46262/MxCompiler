package com.company.frontend.AST;

import com.company.common.Position;
import com.company.frontend.IR.CFGInstAddr;

public class ASTDeclNode extends ASTStmtNode{
    public String name;
    public ASTExprNode initExpr;
    public ASTTypeNode type;

    public CFGInstAddr reg = null;
    //the address of declared variable

    public ASTDeclNode(Position pos, ASTNodeType _type, String _n, ASTExprNode _e, ASTTypeNode _t){
        super(pos, _type, null);
        name = _n;
        initExpr = _e;
        type = _t;
    }
}
