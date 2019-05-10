package com.company.frontend.AST;

import com.company.common.Position;
import com.company.frontend.IR.CFGInst;
import com.company.frontend.IR.CFGInstAddr;
import com.company.frontend.IR.CFGNode;
import com.company.frontend.SymbolTable.SymbolType;

import java.util.Vector;

public class ASTExprNode extends ASTStmtNode{
    public Vector<ASTExprNode> exprList;
    public SymbolType resultType = null;
    public CFGNode toNode = null;// the node of symbol(function, identifier, member)
    public CFGInstAddr instAddr = null; //the address of dest
    public Vector<CFGInst> instList = new Vector<>(); //the list of CFGInst of expression


    public ASTExprNode(Position pos, ASTNodeType type, Vector<ASTExprNode> v){
        super(pos, type,null);
        exprList = v;
    }
    public ASTExprNode(){
        super(null, ASTNodeType.e_empty, new Vector<>());
    }
    public boolean isEmpty(){
        return this.nodeType == ASTNodeType.e_empty;
    }
}
