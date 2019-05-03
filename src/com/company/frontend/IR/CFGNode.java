package com.company.frontend.IR;

import java.util.Vector;

public class CFGNode {
    public String name;
    public int ID;
    public Vector<CFGInst> insts = new Vector<>();
    public Vector<CFGNode> prevNodes = new Vector<>();
    public Vector<CFGNode> nextNodes = new Vector<>();

    public CFGNode(int _id) {
        ID = _id;
        name = "_Label_" + _id;
    }
    public CFGInst addInst(CFGInst.InstType _t){
        CFGInst newInst = new CFGInst(_t);
        insts.add(newInst);
        return newInst;
    }
    public void addInst(CFGInst inst){
        insts.add(inst);
    }

    public void addInst(Vector<CFGInst> inst){
        insts.addAll(inst);
    }

    //linkto -> linkfrom
    public void linkTo(CFGNode node){
        for (CFGNode i: nextNodes){
            if (i.ID == node.ID) return;
        }
        nextNodes.add(node);
        node.linkFrom(this);
    }

    private void linkFrom(CFGNode node){
        for (CFGNode i: prevNodes){
            if (i.ID == node.ID) return;
        }
        prevNodes.add(node);
        //node.linkTo(this);
    }

}
