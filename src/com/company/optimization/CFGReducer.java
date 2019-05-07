package com.company.optimization;


import com.company.frontend.IR.*;

import java.util.HashSet;
import java.util.Stack;

public class CFGReducer {
    private CFG cfg;
    private HashSet<Integer> visitFlag = new HashSet<>();
    private Stack<CFGNode> visitStack = new Stack<>();
    private boolean isGlobal;
    public CFGReducer(CFG _cfg){
        cfg = _cfg;
        visitCFG();
    }
    private void visitCFG(){
        isGlobal = true;
        for (CFGProcess i : cfg.processList) {
            visitCFGNode(i.entryNode);
        }
        isGlobal = false;
        while (!visitStack.empty()){
            CFGNode node = visitStack.pop();
            visitCFGNode(node);
        }
    }
    private void visitCFGNode(CFGNode node){
        if (node == null || visitFlag.contains(node.ID)) return;
        visitFlag.add(node.ID);
        while (node.nextNodes.size() == 1 &&
                (node.nextNodes.firstElement().prevNodes.size() ==1 || (node.insts.isEmpty() && !isGlobal))){
            for (int i = 0; i < node.insts.size(); i++)
                if (node.insts.get(i).op == CFGInst.InstType.op_jmp){
                    node.insts.remove(i);
                    break;
                }
            visitFlag.add(node.nextNodes.firstElement().ID);
            cfg.mergeNode(node, node.nextNodes.firstElement());
        }
        node.nextNodes.forEach(visitStack::push);
    }

}
