package com.company.frontend.AST;

import com.company.common.Position;

public class ASTPrimNode extends ASTExprNode{
    public int intValue;
    public String stringValue ;
    public ASTPrimNode(Position pos, ASTNodeType _type, int i, String s){
        super(pos, _type,null);
        intValue = i;
        stringValue = s;
    }
}
