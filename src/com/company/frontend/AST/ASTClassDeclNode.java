package com.company.frontend.AST;

import com.company.common.Position;

import java.util.Vector;

public class ASTClassDeclNode extends ASTStmtNode{
    public String className;
    public ASTClassDeclNode(Position pos, ASTNodeType _type, Vector<ASTStmtNode> v, String _name){
        super(pos, _type, v);
        className = _name;
    }
}
