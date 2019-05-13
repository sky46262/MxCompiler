package com.company.optimization;

import com.company.frontend.IR.*;

import java.util.HashSet;
import java.util.Vector;

public class UselessCodeEliminater {
    private HashSet<Integer> visitFlag = new HashSet<>();
    CFG cfg;
    public UselessCodeEliminater(CFG _cfg){
        cfg = _cfg;
        visitCFG();
    }
    private void visitCFG(){
        for (CFGProcess cfgProcess : cfg.processList) {
            visitCFGNode(cfgProcess.entryNode);
        }
    }
    private void visitCFGNode(CFGNode node){
        if (visitFlag.contains(node.ID)) return;
        visitFlag.add(node.ID);

        Vector<CFGInst> newList = new Vector<>();
        for (CFGInst inst : node.insts) if (inst.info.defReg.size() > 0){
            boolean flag = false;
            for (Integer defReg : inst.info.defReg) {
                if (defReg > CFGInstAddr.getRegSize() || inst.info.LiveOut.contains(defReg)) flag = true;
            }
            if (flag) newList.add(inst);
        }
        else newList.add(inst);
        node.insts = newList;
        node.nextNodes.forEach(this::visitCFGNode);
    }
}
