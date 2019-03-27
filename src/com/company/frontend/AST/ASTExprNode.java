package com.company.frontend.AST;

import com.company.common.Position;
import com.company.frontend.SymbolTable.SymbolType;

import java.util.Vector;

public class ASTExprNode extends ASTStmtNode{
    public Vector<ASTExprNode> exprList;
    public SymbolType resultType = null;
    public ASTExprNode(Position pos, ASTNodeType type, Vector<ASTExprNode> v){
        super(pos, type,null);
        exprList = v;
    }
}
