package com.company.optimization;

import com.company.frontend.IR.*;
import com.company.optimization.info.InstInfo;
import com.company.optimization.info.VarInfo;

import java.util.HashSet;

public class DataFlowAnalyzer {
    private CFG cfg;
    private HashSet<Integer> visitFlag = new HashSet<>();
    private boolean modified = true;

    public DataFlowAnalyzer(CFG _cfg) {
        cfg = _cfg;
        visitCFG();
    }
    private void visitCFG(){
        for (CFGProcess i: cfg.processList){
            modified = true;
            while(modified){// why modified
                modified = false;
                visitCFGNode(i.exitNode);
                visitFlag.clear();
            }
        }
    }
    private void visitCFGNode(CFGNode node){
        if (visitFlag.contains(node.ID)) return;
        visitFlag.add(node.ID);

        CFGInst last_inst = null;
        HashSet<Integer> liveIn = new HashSet<>();
        HashSet<Integer> liveOut = new HashSet<>();

        for (int i = node.insts.size()-1; i>=0; i--){
            CFGInst inst = node.insts.get(i);
            if (last_inst == null)
                for (CFGNode n : node.nextNodes){
                    if (n.insts.size() > 0)
                    liveOut.addAll(n.insts.get(0).info.LiveIn);
                }
            else liveOut.addAll(last_inst.info.LiveIn);
            last_inst = inst;

            HashSet<Integer> usedReg = new HashSet<>();
            InstInfo info = inst.info;
            int defReg = visitCFGInst(inst, usedReg);

            liveIn.addAll(liveOut);
            liveIn.remove(defReg);
            usedReg.remove(0);
            liveIn.addAll(usedReg);
            info.defReg = defReg;

            if (!liveIn.equals(info.LiveIn) || !liveOut.equals(info.LiveOut)){
                info.usedReg.clear();
                info.usedReg.addAll(usedReg);
                info.LiveIn.clear();
                info.LiveIn.addAll(liveIn);
                info.LiveOut.clear();
                info.LiveOut.addAll(liveOut);
                modified = true;
            }

            usedReg.clear();
            liveOut.clear();
            liveIn.clear();
        }
        node.prevNodes.forEach(this::visitCFGNode);
    }
    private int visitCFGInst(CFGInst inst, HashSet<Integer> usedReg){
        int reg1 = 0, reg2 = 0;
        if (inst.operands.size() > 0 && inst.operands.get(0).a_type == CFGInstAddr.addrType.a_reg)
            reg1 = Integer.max(0, inst.operands.get(0).getNum());
        if (inst.operands.size() > 1 && inst.operands.get(1).a_type == CFGInstAddr.addrType.a_reg)
            reg2 = Integer.max(0, inst.operands.get(1).getNum());
        int defReg = 0;
        switch (inst.op){
            case op_mov:
                defReg = reg1;
                usedReg.add(reg2);
                break;
            case op_push:
            case op_wpara:
                usedReg.add(reg1);
                break;
            case op_pop:
            case op_rpara:
                defReg = reg1;
                break;
            case op_eq:
            case op_ne:
            case op_le:
            case op_lt:
            case op_ge:
            case op_gt:
                usedReg.add(reg1);
                usedReg.add(reg2);
                break;
            case op_mult:
            case op_div:
            case op_mod:
            case op_shl:
            case op_shr:
            case op_add:
            case op_sub:
            case op_and:
            case op_or:
            case op_xor:
                defReg = reg1;
                usedReg.add(reg2);
                usedReg.add(reg1);
                break;
            case op_return:
                break;
            case op_inc:
            case op_dec:
            case op_neg:
            case op_not:
            case op_pos:
                defReg = reg1;
                usedReg.add(reg1);
                break;
        }
        for (CFGInstAddr i: inst.operands){
            if (i.a_type == CFGInstAddr.addrType.a_mem){
                if (i.addr1 != null && i.addr1.a_type == CFGInstAddr.addrType.a_reg && i.addr1.getNum()>0)
                    usedReg.add(i.addr1.getNum());
                if (i.addr2 != null && i.addr2.a_type == CFGInstAddr.addrType.a_reg && i.addr2.getNum()>0)
                    usedReg.add(i.addr2.getNum());
            }
        }
        return defReg;
    }
}
