package com.company.frontend.AST;

import com.company.common.Position;

import java.util.Vector;

public class ASTCompilationUnitNode extends ASTBaseNode {
    public Vector<ASTStmtNode> declList;
    public ASTCompilationUnitNode(Position pos, Vector<ASTStmtNode> v){
        super(pos, ASTNodeType.cu_root);
        declList = v;
    }
}
