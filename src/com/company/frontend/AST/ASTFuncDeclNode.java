package com.company.frontend.AST;

import com.company.common.Position;

import java.util.Vector;

public class ASTFuncDeclNode extends ASTStmtNode{
    public String funcName;
    public boolean isConstructor;
    public boolean isMember;
    public ASTTypeNode returnType;
    public ASTStmtNode paramList;
    public ASTStmtNode funcBody;
    public ASTFuncDeclNode(Position pos, ASTNodeType _type, String _name, ASTTypeNode _t, ASTStmtNode _p, ASTStmtNode _b){
        super(pos, _type, null);
        funcName = _name;
        returnType = _t;
        paramList = _p;
        funcBody = _b;
    }
    public void setMember(){
        isMember = true;
    }
    public void setConstructor(){
        isConstructor = true;
    }
}
