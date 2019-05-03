package com.company.backend;

import com.company.frontend.IR.*;

import java.util.HashSet;

public class StackAllocator {
    private CFG cfg;
    private int curStackPt = 8;
    private HashSet<Integer> visitFlag = new HashSet<>();

    public StackAllocator(CFG _cfg) {
        cfg = _cfg;
        visitCFG();
    }
    private void visitCFG(){
        for (CFGProcess i: cfg.processList){
            visitCFGNode(i.entryNode);
            i.stackSize = curStackPt;
            curStackPt = 8;
        }
    }
    private void visitCFGNode(CFGNode node){
        if (visitFlag.contains(node.ID)) return;
        visitFlag.add(node.ID);

        node.insts.forEach(this::visitCFGInst);
        node.nextNodes.forEach(this::visitCFGNode);
    }
    private void visitCFGInst(CFGInst inst){
        for (CFGInstAddr i : inst.operands){
            if (i.a_type == CFGInstAddr.addrType.a_stack){
                int t = i.getSize();
                int base = curStackPt;
                curStackPt += t;
                i.a_type = CFGInstAddr.addrType.a_mem;
                i.lit1 = -1;
                i.lit2 = 0;
                i.lit4 = -base;
                i.lit3 = 0;
                i.addr1 = null;
                i.addr2 = null;
            }
        }
    }
}
