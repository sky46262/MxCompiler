package com.company.backend;

import com.company.backend.NASM.NASMAddr;
import com.company.frontend.IR.*;

import javax.management.InstanceAlreadyExistsException;
import java.util.HashSet;
import java.util.Vector;

import static com.company.frontend.IR.CFGInst.isCompare;

public class NASMAdapter {
    private CFG cfg;
    private HashSet<Integer> visitFlag = new HashSet<>();
    private int curStackPt = 8;
    public NASMAdapter(CFG _cfg) {
        cfg = _cfg;
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
        Vector<CFGInst> newList = new Vector<>();
        for (CFGInst inst : node.insts) {
            Vector<CFGInstAddr> operands = inst.operands;
            switch (operands.size()){
                case 3:{
                    if (operands.get(0).equals(operands.get(1))){
                        visitCFGInst(newList, inst.op, operands.get(0), operands.get(2));
                    }
                    else if (operands.get(0).equals(operands.get(2))){
                        if (inst.isCommutable())
                            visitCFGInst(newList, inst.op, operands.get(0), operands.get(1));
                        else {
                            CFGInstAddr tmp = CFGInstAddr.newRegAddr();
                            visitCFGInst(newList, CFGInst.InstType.op_mov, tmp, operands.get(1));
                            visitCFGInst(newList, inst.op, tmp, operands.get(2));
                            visitCFGInst(newList, CFGInst.InstType.op_mov, operands.get(0), tmp);
                        }
                    }
                    else {
                        visitCFGInst(newList, CFGInst.InstType.op_mov, operands.get(0), operands.get(1));
                        visitCFGInst(newList, inst.op, operands.get(0), operands.get(2));
                    }
                    break;
                }
                case 2:

                    visitCFGInst(newList, inst.op, operands.get(0), operands.get(1));
                    break;
                case 1:
                    visitCFGInst(newList, inst.op, operands.get(0), null);
                    break;
                default:
                    visitCFGInst(newList, inst.op, null, null);
            }
        }
        node.insts = newList;
        for (CFGInst inst : node.insts){
            if (inst.op == CFGInst.InstType.op_mov){
                CFGInstAddr opr1 = inst.operands.get(0);
                CFGInstAddr opr2 = inst.operands.get(1);
                assert opr1 != null && opr2 != null && !(opr1.a_type == CFGInstAddr.addrType.a_mem && opr2.a_type == CFGInstAddr.addrType.a_mem);
            }
        }
        node.nextNodes.forEach(this::visitCFGNode);
    }
    private void visitCFGInst(Vector<CFGInst> list, CFGInst.InstType type, CFGInstAddr opr1, CFGInstAddr opr2){
        switch (type){
            case op_div:
                if (opr2 != null && opr2.isConst()){
                    int num = opr2.getConst();
                    assert num != 0;
                    if (isExp2(num)){
                        int k = 0;
                        while (num != 1) {
                            num >>= 1;
                            k++;
                        }
                        if (k >= 1){
                            visitCFGInst(list, CFGInst.InstType.op_shr, opr1, CFGInstAddr.newImmAddr(k));
                        }
                        return;
                    }
                    CFGInstAddr tmp = CFGInstAddr.newRegAddr();
                    visitCFGInst(list, CFGInst.InstType.op_mov, tmp, opr2);
                    opr2 = tmp;
                }
                break;
            case op_mod:
                if (opr2 != null && opr2.isConst()){
                    int num = opr2.getConst();
                    assert num != 0;
                    if (isExp2(num)){
                        int k = 0;
                        while (num != 1) {
                            num >>= 1;
                            k++;
                        }
                        if (k >= 1){
                            visitCFGInst(list, CFGInst.InstType.op_and, opr1, CFGInstAddr.newImmAddr((1<<k)-1));
                        }
                        else visitCFGInst(list, CFGInst.InstType.op_mov, opr1, CFGInstAddr.newImmAddr(0));
                        return;
                    }
                    CFGInstAddr tmp = CFGInstAddr.newRegAddr();
                    visitCFGInst(list, CFGInst.InstType.op_mov, tmp, opr2);
                    opr2 = tmp;
                }
                break;
            case op_mult: {
                if (opr2 != null && opr2.isConst()){
                    int num = opr2.getConst();
                    assert num != 0;
                    if (isExp2(num)){
                        int k = 0;
                        while (num != 1) {
                            num >>= 1;
                            k++;
                        }
                        if (k > 2){
                            visitCFGInst(list, CFGInst.InstType.op_shl, opr1, CFGInstAddr.newImmAddr(k));
                        }
                        else {
                            for (int j = 0; j < k; j++){
                                visitCFGInst(list, CFGInst.InstType.op_add, opr1, opr1);
                            }
                        }
                        return;
                    }
                    CFGInstAddr tmp = CFGInstAddr.newRegAddr();
                    visitCFGInst(list, CFGInst.InstType.op_mov, tmp, opr2);
                    opr2 = tmp;
                    if (opr1.a_type != CFGInstAddr.addrType.a_reg) {
                        tmp = CFGInstAddr.newRegAddr();
                        visitCFGInst(list, CFGInst.InstType.op_mov, tmp, opr1);
                        visitCFGInst(list, CFGInst.InstType.op_mult, tmp, opr2);
                        visitCFGInst(list, CFGInst.InstType.op_mov, opr1, tmp);
                        return;
                    }
                    //!!!
                    break;
                }
            }
            break;
            case op_not:
            case op_neg:
                if (opr2 != null) {
                    visitCFGInst(list, CFGInst.InstType.op_mov, opr1, opr2);
                    opr2 = null;
                }
                break;
        }
        if (opr1 != null && opr2 != null && (opr1.a_type == CFGInstAddr.addrType.a_mem || opr1.a_type == CFGInstAddr.addrType.a_static) &&
                (opr2.a_type == CFGInstAddr.addrType.a_mem || opr2.a_type == CFGInstAddr.addrType.a_static)){
            CFGInstAddr tmp =  CFGInstAddr.newRegAddr();
            visitCFGInst(list, CFGInst.InstType.op_mov, tmp, opr2);
            visitCFGInst(list, type, opr1, tmp);
            return;
        }
        if (isCompare(type) && opr1.a_type == CFGInstAddr.addrType.a_imm && opr2.a_type == CFGInstAddr.addrType.a_imm){
            CFGInstAddr tmp = CFGInstAddr.newRegAddr();
            visitCFGInst(list, CFGInst.InstType.op_mov, tmp, opr1);
            visitCFGInst(list, type, tmp, opr2);
            return;
        }
        CFGInstAddr newOpr2 = visitCFGInstAddr(list, opr2); //opr2 first (right associated)
        CFGInstAddr newOpr1 = visitCFGInstAddr(list, opr1);
        //assert !(type == CFGInst.InstType.op_mov && newOpr1.a_type == CFGInstAddr.addrType.a_mem && newOpr2.a_type == CFGInstAddr.addrType.a_mem);
        if (type == CFGInst.InstType.op_mov && newOpr1.equals(newOpr2)) return;
        list.add(new CFGInst(type, newOpr1, newOpr2));
    }
    private CFGInstAddr visitCFGInstAddr(Vector<CFGInst> list, CFGInstAddr opr){
        if (opr == null || opr.a_type != CFGInstAddr.addrType.a_mem) return opr;
        if (opr.addr1 != null) {
            CFGInstAddr t = new CFGInstAddr(opr.a_type, opr.lit1, opr.lit2, opr.lit3, opr.lit4);
            if (opr.addr1.a_type == CFGInstAddr.addrType.a_mem) {
                CFGInstAddr tmp = CFGInstAddr.newRegAddr();
                visitCFGInst(list, CFGInst.InstType.op_mov, tmp, opr.addr1);
                t.addr1 = tmp;
            }
            else t.addr1 = opr.addr1;
            if (opr.addr2.a_type == CFGInstAddr.addrType.a_mem || opr.addr2.a_type == CFGInstAddr.addrType.a_static){
                CFGInstAddr tmp = CFGInstAddr.newRegAddr();
                visitCFGInst(list, CFGInst.InstType.op_mov, tmp, opr.addr2);
                t.addr2 = tmp;
            }
            else t.addr2 = opr.addr2;
            return t;
        }
        else return opr;
    }
    private boolean isExp2(int num){
        return (num > 0) && (num & (num-1)) == 0;
    }
}
