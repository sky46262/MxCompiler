package com.company.frontend.AST;

import com.company.common.Position;
import com.company.frontend.IR.CFGNode;

import java.util.Vector;

public class ASTStmtNode extends ASTBaseNode{
    public Vector<ASTStmtNode> stmtList;

    public CFGNode startNode = null;
    public CFGNode endNode = null;
    //CFGNode of statement

    public ASTStmtNode(Position pos, ASTNodeType type, Vector<ASTStmtNode> v){
        super(pos, type);
        stmtList = v;
    }
}
