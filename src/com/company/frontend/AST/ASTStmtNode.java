package com.company.frontend.AST;

import com.company.common.Position;
import java.util.Vector;

public class ASTStmtNode extends ASTBaseNode{
    public Vector<ASTStmtNode> StmtList;
    public ASTStmtNode(Position pos, ASTNodeType type, Vector<ASTStmtNode> v){
        super(pos, type);
        StmtList = v;
    }
}
