package com.company.backend;

import com.company.frontend.IR.*;

import java.util.HashSet;
import java.util.Vector;

public class GlobalRegAllocator {
    private CFG cfg;
    private HashSet<Integer> visitFlag = new HashSet<>();
    private int maxColor = 5;
    private static int curChrom = 4;
    HashSet<Integer> colorSet = new HashSet<>();

    public GlobalRegAllocator(CFG _cfg){
        cfg = _cfg;
        for (int i = 1; i <= curChrom; i++) {
            colorSet.add(i);
        }
        visitCFG();
    }
    private void visitCFG(){
        for (CFGProcess i : cfg.processList) {
            visitCFGNode(i.entryNode);
        }
    }
    private void visitCFGNode(CFGNode node){
        if (visitFlag.contains(node.ID)) return;
        visitFlag.add(node.ID);
        node.insts.forEach(this::visitCFGInst);
        node.nextNodes.forEach(this::visitCFGNode);
    }
    private void visitCFGInst(CFGInst inst){
        Vector<CFGInstAddr> operands = inst.operands;
        for (int i = 0; i < operands.size(); i++){
            CFGInstAddr addr = operands.get(i);
            assert (addr != null);
            if (addr.a_type == CFGInstAddr.addrType.a_reg && addr.getNum() > 0){
                addr.copy(CFGInstAddr.newStackAddr(8));
            }
        }
    }
}
//TODO coloring