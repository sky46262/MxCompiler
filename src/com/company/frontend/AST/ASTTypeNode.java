package com.company.frontend.AST;

import com.company.common.Position;

public class ASTTypeNode extends ASTBaseNode {
    public String className;
    public int dimension;
    public ASTTypeNode(Position pos, ASTNodeType type, String _name, int _d) {
        super(pos, type);
        className = _name;
        dimension = _d;
    }
}
