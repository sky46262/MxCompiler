package com.company.optimization;

import com.company.frontend.IR.*;

import java.util.HashSet;
import java.util.Vector;

public class PeepholeOptimizer {
    private CFG cfg;
    private HashSet<Integer> visitFlag = new HashSet<>();
    public PeepholeOptimizer(CFG _cfg){
        cfg = _cfg;
        visitCFG();
    }
    private void visitCFG(){
        for (CFGProcess i : cfg.processList){
            visitCFGNode(i.entryNode);
        }
    }

    private void visitCFGNode(CFGNode node) {
        if (visitFlag.contains(node.ID)) return;
        visitFlag.add(node.ID);
        Vector<CFGInst> newList = new Vector<>();
        for (int i = 0; i < node.insts.size(); i++){
            CFGInst inst = node.insts.get(i);
            CFGInst next_inst = null, nnext_inst = null;
            if (i+1 < node.insts.size()) next_inst = node.insts.get(i+1);
            if (i+2 < node.insts.size()) nnext_inst = node.insts.get(i+2);
            /*if (inst.op == CFGInst.InstType.op_mov && next_inst != null && nnext_inst != null){
                CFGInstAddr opr1 = inst.operands.get(0);
                CFGInstAddr opr2 = inst.operands.get(1);
                if (next_inst.operands.size() > 0 && next_inst.operands.get(0).equals(opr1) &&
                    nnext_inst.op == CFGInst.InstType.op_mov && nnext_inst.operands.get(0).equals(opr2)
                        && nnext_inst.operands.get(1).equals(opr1)){
                    next_inst.operands.set(0, opr2);
                    newList.add(next_inst);
                    i+=2;
                    continue;
                }
            }*/
            // mov opr1 opr2
            // xxx opr1
            // mov opr2 opr1
            // turn to
            // xxx opr2
            if (inst.op == CFGInst.InstType.op_add || inst.op == CFGInst.InstType.op_sub){
                CFGInstAddr opr2 = inst.operands.get(1);
                if (opr2.isConst() && opr2.getConst() == 0) continue;
            }
            // add 0
            if (inst.op == CFGInst.InstType.op_mov && (next_inst != null && next_inst.op == CFGInst.InstType.op_mov)){
                CFGInstAddr opr1 = inst.operands.get(0);
                CFGInstAddr opr2 = inst.operands.get(1);
                if (opr1.a_type == CFGInstAddr.addrType.a_mem && next_inst.operands.get(1).equals(opr1)){
                    next_inst.operands.set(0, opr2);
                }
                if (opr2.a_type == CFGInstAddr.addrType.a_mem && next_inst.operands.get(1).equals(opr2)){
                    next_inst.operands.set(0, opr1);
                }
            }
            // mov reg1 mem or mov mem reg1
            // mov reg2 mem
            newList.add(inst);
        }
        node.insts = newList;
        node.nextNodes.forEach(this::visitCFGNode);
    }

}
