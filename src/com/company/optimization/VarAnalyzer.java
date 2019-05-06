package com.company.optimization;

import com.company.frontend.IR.*;
import com.company.optimization.info.VarInfo;

import java.util.HashSet;

//calc value of var for register allocation
public class VarAnalyzer {
    CFG cfg;
    private HashSet<Integer> visitFlag = new HashSet<>();

    public VarAnalyzer(CFG _cfg){
        cfg = _cfg;
        visitCFG();
    }
    private void visitCFG(){
        for (CFGProcess i: cfg.processList){
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
        VarInfo info1 = null;
        VarInfo info2 = null;
        if (inst.operands.size() > 0) info1 = inst.operands.get(0).info;
        if (inst.operands.size() > 1) info2 = inst.operands.get(1).info;
        switch (inst.op){
            case op_mov:
                info1.writeCnt++;
                info2.readCnt++;
                break;
            case op_push:
            case op_wpara:
                info1.readCnt++;
                break;
            case op_pop:
            case op_rpara:
            case op_inc:
            case op_dec:
                info1.writeCnt++;
                break;
            case op_eq:
            case op_ne:
            case op_le:
            case op_lt:
            case op_ge:
            case op_gt:
                info1.readCnt++;
                info2.readCnt++;
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
                info1.writeCnt++;
                info2.readCnt++;
                break;
            case op_return:
                break;
            case op_neg:
            case op_not:
            case op_pos:
                info1.writeCnt++;
                info1.readCnt++;
                break;
        }
        for (CFGInstAddr i: inst.operands){
            if (i.a_type == CFGInstAddr.addrType.a_mem){
                if (i.addr1 != null)
                    ++i.addr1.info.readCnt;
                if (i.addr2 != null)
                    ++i.addr2.info.readCnt;
            }
        }
     }
    //todo const
}
