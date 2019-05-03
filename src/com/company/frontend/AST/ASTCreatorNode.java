package com.company.frontend.AST;

import com.company.common.Position;

import java.util.Vector;

public class ASTCreatorNode extends ASTExprNode {
    public ASTTypeNode type;
    public boolean hasConstructor = false;

    public ASTCreatorNode(Position pos, ASTNodeType _type, Vector<ASTExprNode> _v, ASTTypeNode _t){
        super(pos, _type, _v);
        type = _t;
    }
}
