package com.company.optimization;

import com.company.frontend.IR.*;
import com.company.optimization.info.InstInfo;
import com.company.optimization.info.VarInfo;

import java.util.HashSet;

//16 RAX 19 RDX 20 RSP 21 RBP
public class DataFlowAnalyzer {
    private CFG cfg;
    private HashSet<Integer> visitFlag = new HashSet<>();
    private boolean modified = true;
    private int regSize = CFGInstAddr.getRegSize();

    private int[] parameterReg = {23, 22, 19, 18, 8, 9};

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
                    //TODO
                    //if (n.insts.size() > 0)
                    liveOut.addAll(n.insts.get(0).info.LiveIn);
                }
            else liveOut.addAll(last_inst.info.LiveIn);
            last_inst = inst;

            HashSet<Integer> usedReg = new HashSet<>();
            HashSet<Integer> defReg = new HashSet<>();
            InstInfo info = inst.info;
            visitCFGInst(inst, usedReg, defReg);

            liveIn.addAll(liveOut);
            usedReg.remove(0);
            defReg.remove(0);
            liveIn.removeAll(defReg);
            liveIn.addAll(usedReg);

            if (!liveIn.equals(info.LiveIn) || !liveOut.equals(info.LiveOut)){
                info.defReg.clear();
                info.defReg.addAll(defReg);
                info.usedReg.clear();
                info.usedReg.addAll(usedReg);
                info.LiveIn.clear();
                info.LiveIn.addAll(liveIn);
                info.LiveOut.clear();
                info.LiveOut.addAll(liveOut);
                modified = true;
            }

            defReg.clear();
            usedReg.clear();
            liveOut.clear();
            liveIn.clear();
        }
        node.prevNodes.forEach(this::visitCFGNode);
    }
    private void visitCFGInst(CFGInst inst, HashSet<Integer> usedReg, HashSet<Integer> defReg){
        int reg1 = 0, reg2 = 0;
        if (inst.operands.size() > 0 && inst.operands.get(0).a_type == CFGInstAddr.addrType.a_reg)
            reg1 = getReg(inst.operands.get(0).getNum());
        if (inst.operands.size() > 1 && inst.operands.get(1).a_type == CFGInstAddr.addrType.a_reg)
            reg2 = getReg(inst.operands.get(1).getNum());
        switch (inst.op){
            case op_mov:
                defReg.add(reg1);
                usedReg.add(reg2);
                break;
            case op_push:
                usedReg.add(reg1);
                break;
            case op_wpara:
                usedReg.add(reg1);
            {
                int num = inst.operands.get(1).getNum();
                if (num <6){
                    usedReg.add(regSize + parameterReg[num]);
                    defReg.add(regSize + parameterReg[num]);
                }
            }
                break;
            case op_pop:
                defReg.add(reg1);
                break;
            case op_rpara:
                defReg.add(reg1);
            {
                int num = inst.operands.get(1).getNum();
                if (num <6) {
                    usedReg.add(regSize + parameterReg[num]);
                    defReg.add(regSize + parameterReg[num]);
                }
            }
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
                defReg.add(getReg(-4));
                usedReg.add(getReg(-4));
                defReg.add(getReg(-5));
                usedReg.add(getReg(-5));
                defReg.add(reg1);
                usedReg.add(reg2);
                usedReg.add(reg1);
                break;
            case op_shl:
            case op_shr:
                defReg.add(regSize + 18);
                usedReg.add(regSize + 18);
            case op_add:
            case op_sub:
            case op_and:
            case op_or:
            case op_xor:
                defReg.add(reg1);
                usedReg.add(reg2);
                usedReg.add(reg1);
                break;
            case op_return:
                defReg.add(regSize + 20);
                break;
            case op_inc:
            case op_dec:
            case op_neg:
            case op_not:
            case op_pos:
                defReg.add(reg1);
                usedReg.add(reg1);
                break;
            case op_call:
               defReg.add(regSize + 20);
                break;
            case op_lea:
                defReg.add(reg1);
                usedReg.add(reg2);
                break;
        }
        for (CFGInstAddr i: inst.operands){
            if (i.a_type == CFGInstAddr.addrType.a_mem){
                if (i.addr1 != null && i.addr1.a_type == CFGInstAddr.addrType.a_reg)
                    usedReg.add(getReg(i.addr1.getNum()));
               // else if (i.addr1 == null) usedReg.add(regSize + 21);
                if (i.addr2 != null && i.addr2.a_type == CFGInstAddr.addrType.a_reg)
                    usedReg.add(getReg(i.addr2.getNum()));
            }
        }
    }

    private int getReg(int num) {
        switch (num){
            case -1:
                return regSize + 21;
            case -2:
                return regSize + 16;
            case -3:
                return regSize + 20;
            case -4:
                return regSize + 16;
            case -5:
                return regSize + 19;
                default:
                    return Integer.max(num,0);
        }
    }
}
