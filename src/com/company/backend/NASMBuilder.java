package com.company.backend;

import com.company.backend.NASM.*;
import com.company.frontend.IR.*;
import java.util.HashSet;
import java.util.Stack;
import java.util.Vector;

public class NASMBuilder {
    CFG cfg;
    NASM nasm;

    private Vector<NASMInst> instList = new Vector<>();

    private int[] availableReg = {17,12, 13, 14, 15};
    private int[] parameterReg = {23, 22, 19, 18, 8, 9};
    private int parameterStackOffset = 0;

    private int[] templateReg = {10, 11};
    private int[] templateMap = {0, 0};
    private int curTemplateReg = 0;

    private Stack<Integer> curCalleeSavedReg = new Stack<>();
    private int[] calleeSaveReg = {17, 12, 13, 14, 15, 21};
    //private Stack<Integer> curCallerSavedReg = new Stack<>();
    //private int[] callerSaveReg = {16, 18, 19, 8, 9, 10, 11, 22, 23};
    private int curStackOffset = 0;

    private HashSet<Integer> visitFlag = new HashSet<>();
    private CFGProcess curProcess = null;
    private CFGInst.InstType lastOp;

    public NASMBuilder(CFG _cfg, NASM _nasm) {
        cfg = _cfg;
        nasm = _nasm;
        visitCFG();
    }

    private void visitCFG() {
        for (CFGProcess i : cfg.processList) {
            nasm.defGlobal(i.entryNode.name);
        }
        for (CFGData i : cfg.dataList) {
            nasm.defGlobal(i.name);
        }
        nasm.genLine();
        for (CFGProcess i : cfg.processList) {
            curProcess = i;
            visitCFGNode(i.entryNode);
            //curAvailReg = 0;
        }
        nasm.genLine();
        nasm.defSection(NASM.SectionType.DATA);
        for (CFGData i : cfg.dataList) {
            if (i.dType != CFGData.dataType.d_res) visitCFGData(i);
        }
        nasm.genLine();
        nasm.defSection(NASM.SectionType.BSS);
        for (CFGData i : cfg.dataList) {
            if (i.dType == CFGData.dataType.d_res) visitCFGData(i);
        }
    }

    private void visitCFGNode(CFGNode node) {
        if (visitFlag.contains(node.ID)) return;
        visitFlag.add(node.ID);
        nasm.defLabel(node.name);
        if (!node.insts.isEmpty()) {
            //after reduce, change the label
            CFGInst last_inst = node.insts.lastElement();
            if (last_inst.op == CFGInst.InstType.op_jcc || last_inst.op == CFGInst.InstType.op_jmp)
                last_inst.operands.firstElement().strLit = node.nextNodes.firstElement().name;
        }
        //TODO
        if (curProcess != null) {
            //in the front of function
            //push register and calculate stack
            if (curProcess.isCallee && curProcess.paramCnt <= 6) {
                for (int t : calleeSaveReg) curCalleeSavedReg.add(t);
            } else curCalleeSavedReg.add(21);
            pushCalleeSavedReg();
            genNASMInst(NASMInst.InstType.MOV, new NASMRegAddr(new NASMReg(21, NASMWordType.QWORD)), new NASMRegAddr(new NASMReg(20, NASMWordType.QWORD)));
            if (curProcess.stackSize > 8) {
                curStackOffset = Integer.max(getExp2(curProcess.stackSize), 32);
                genNASMInst(NASMInst.InstType.SUB, new NASMRegAddr(new NASMReg(20, NASMWordType.QWORD)), new NASMImmAddr(curStackOffset));
            }
            else curStackOffset = 0;
            curProcess = null;
        }
        node.insts.forEach(this::visitCFGInst);
        //Why add jump inst
        if (node.nextNodes.size() == 1 && (node.insts.isEmpty() || node.insts.lastElement().op != CFGInst.InstType.op_return) && visitFlag.contains(node.nextNodes.firstElement().ID))
            genNASMInst(CFGInst.InstType.op_jmp, CFGInstAddr.newLabelAddr(node.nextNodes.firstElement()), null);
        for (int i = node.nextNodes.size() - 1; i >= 0; i--) {
            CFGNode nextNode = node.nextNodes.get(i);
            if (i == 1 && visitFlag.contains(nextNode.ID))
                genNASMInst(CFGInst.InstType.op_jmp, CFGInstAddr.newLabelAddr(nextNode), null);
            else visitCFGNode(nextNode);
        }
    }

    private void visitCFGData(CFGData data) {
        nasm.genLine(false, data.name + ":");
        StringBuilder str = new StringBuilder();
        switch (data.dType){
            case d_num:
                str.append("dq\t").append(data.intValue);
                break;
            case d_res:
                str.append("resw\t").append(data.size);
                break;
            case d_str:
                str.append("db\t");
                data.strValue.chars().forEach(i-> str.append(String.format("%02X",i)).append("H, "));
                str.append("00H");
                //strLit is to long, so split into bytes
                break;
        }
        nasm.genLine(true, str.toString());
    }

    private void visitCFGInst(CFGInst inst) {
        if (inst.operands.size() == 2)
            genNASMInst(inst.op, inst.operands.get(0), inst.operands.get(1));
        else if (inst.operands.size() == 1)
            genNASMInst(inst.op, inst.operands.get(0), null);
        else genNASMInst(inst.op, null, null);
        lastOp = inst.op;
    }

    private void genNASMInst(CFGInst.InstType op, CFGInstAddr opr1, CFGInstAddr opr2) {
        if (op == CFGInst.InstType.op_mov){
            assert opr1 != null && opr2 != null && !(opr1.a_type == CFGInstAddr.addrType.a_mem && opr2.a_type == CFGInstAddr.addrType.a_mem);
        }
        if (CFGInst.isCompare(op) && opr1.a_type == CFGInstAddr.addrType.a_imm) {
            genNASMInst(op, opr2, opr1);
            return;
            //??? commute?
        }
        else if (op == CFGInst.InstType.op_div && opr2 != null){
            genNASMInst(CFGInst.InstType.op_mov, CFGInstAddr.newRegAddr(-4), opr1);
            genNASMInst(NASMInst.InstType.CQO, null, null);
            genNASMInst(CFGInst.InstType.op_div, opr2, null);
            genNASMInst(CFGInst.InstType.op_mov, opr1, CFGInstAddr.newRegAddr(-4));
            return;
        }
        else if (op == CFGInst.InstType.op_mod && opr2 != null){
            genNASMInst(CFGInst.InstType.op_mov, CFGInstAddr.newRegAddr(-4), opr1);
            genNASMInst(NASMInst.InstType.CQO, null, null);
            genNASMInst(CFGInst.InstType.op_div, opr2, null);
            genNASMInst(CFGInst.InstType.op_mov, opr1, CFGInstAddr.newRegAddr(-5));
            return;
        }
        else if (op == CFGInst.InstType.op_mult && opr2 != null){
            genNASMInst(CFGInst.InstType.op_mov, CFGInstAddr.newRegAddr(-4), opr1);
            genNASMInst(NASMInst.InstType.CQO, null, null);
            genNASMInst(CFGInst.InstType.op_mult,  opr2, null);
            genNASMInst(CFGInst.InstType.op_mov, opr1, CFGInstAddr.newRegAddr(-4));
            return;
        }
        NASMWordType wt1, wt2;
        wt1 = NASMWordType.QWORD;
        if (op == CFGInst.InstType.op_lea)
            wt2 = NASMWordType.WORD;
        else wt2 = NASMWordType.QWORD;
        NASMAddr a2 = getNASMAddr(opr2, wt2);
        NASMAddr a1 = getNASMAddr(opr1, wt1);

        if (op == CFGInst.InstType.op_mov && a1.equals(a2)) return;
        switch (op){
            case op_wpara:
                if (opr2.getNum()<6) genNASMInst(NASMInst.InstType.MOV, new NASMRegAddr(new NASMReg(parameterReg[opr2.getNum()], NASMWordType.QWORD)), a1);
                else {
                    genNASMInst(NASMInst.InstType.PUSH, a1, null);
                    parameterStackOffset += NASMWordType.getSize(wt1);
                }
                break;
            case op_rpara:
                if (opr2.getNum() >= 6){
                    if (opr1.a_type == CFGInstAddr.addrType.a_reg)
                        genNASMInst(CFGInst.InstType.op_mov, opr1, new CFGInstAddr(CFGInstAddr.addrType.a_mem,-1,0,0,8+(opr2.getNum()-5)*8));
                    else opr1.lit4 = 8 + (opr2.getNum() - 5)* 8;
                }
                else genNASMInst(NASMInst.InstType.MOV, a1, new NASMRegAddr(new NASMReg(parameterReg[opr2.getNum()], NASMWordType.QWORD)));
                break;
            case op_return:
                if (curStackOffset != 0) genNASMInst(NASMInst.InstType.ADD, new NASMRegAddr(20, NASMWordType.QWORD), new NASMImmAddr(curStackOffset));
                popCalleeSavedReg();
                genNASMInst(getNASMOp(op), a1, a2);
                break;
            case op_shl:
            case op_shr:
                if (opr2.a_type != CFGInstAddr.addrType.a_imm){
                    genNASMInst(NASMInst.InstType.MOV, new NASMRegAddr(18, NASMWordType.QWORD), a2);
                    if (op == CFGInst.InstType.op_shl) genNASMInst(NASMInst.InstType.SAL, a1, new NASMRegAddr(18, NASMWordType.BYTE));
                    else genNASMInst(NASMInst.InstType.SAR, a1, new NASMRegAddr(18, NASMWordType.BYTE));
                }
                else genNASMInst(getNASMOp(op), a1, a2);
                break;
            case op_call:
                genNASMInst(getNASMOp(op), a1, a2);
                if (parameterStackOffset > 0){
                    genNASMInst(NASMInst.InstType.ADD, new NASMRegAddr(20, NASMWordType.QWORD), new NASMImmAddr(parameterStackOffset));
                    parameterStackOffset = 0;
                }
                break;
                default:
                    genNASMInst(getNASMOp(op), a1, a2);
        }
    }

    private void genNASMInst(NASMInst.InstType op, NASMAddr opr1, NASMAddr opr2) {
        NASMInst inst = new NASMInst(op, opr1, opr2);
        instList.add(inst);
        nasm.genText(inst.toString());
    }

    private void pushCalleeSavedReg() {
        for (Integer i : curCalleeSavedReg) {
            genNASMInst(NASMInst.InstType.PUSH, new NASMRegAddr(new NASMReg(i, NASMWordType.QWORD)), null);
        }
    }

    private void popCalleeSavedReg() {
        while(!curCalleeSavedReg.empty()){
            int i = curCalleeSavedReg.pop();
            genNASMInst(NASMInst.InstType.POP, new NASMRegAddr(new NASMReg(i, NASMWordType.QWORD)), null);
        }
    }

    /*private void pushCallerSavedReg() {
        for (Integer i : curCallerSavedReg) {
            genNASMInst(NASMInst.InstType.PUSH, new NASMRegAddr(new NASMReg(i, NASMWordType.QWORD)), null);
        }
    }

    private void popCallerSavedReg() {
        while(!curCallerSavedReg.empty()){
            int i = curCallerSavedReg.pop();
            genNASMInst(NASMInst.InstType.POP, new NASMRegAddr(new NASMReg(i, NASMWordType.QWORD)), null);
        }
    }
    */

    private NASMAddr getNASMAddr(CFGInstAddr opr, NASMWordType wt){
        if (opr == null) return null;
        switch(opr.a_type){
            case a_imm:
                return new NASMImmAddr(opr.getNum());
            case a_label:
                return new NASMLabelAddr(opr.strLit);
            case a_mem:
                NASMReg base = null;
                NASMReg offset = null;
                int val = opr.getNum();
                if (opr.addr1 == null){
                    base = new NASMReg(21, NASMWordType.QWORD);
                    offset = null;
                }
                else {
                    switch (opr.addr1.a_type){
                        case a_imm:
                            base = null;
                            val += opr.addr1.getNum();
                            break;
                        case a_reg:
                            base = getNASMReg(opr.addr1, NASMWordType.QWORD);
                            break;
                        case a_static:
                            base = new NASMReg(opr.addr1.strLit);
                            break;
                    }
                    switch (opr.addr2.a_type){
                        case a_reg:
                            offset = getNASMReg(opr.addr2, NASMWordType.QWORD);
                            break;
                        case a_imm:
                            offset = null;
                            val += opr.getSize() * opr.addr2.getNum();
                            break;
                    }
                }
                return new NASMMemAddr(wt, base, offset, opr.getSize(), val);
            case a_reg:
                return new NASMRegAddr(getNASMReg(opr, wt));
            case a_static:
                return new NASMLabelAddr(opr.strLit);
        }
        return null;
    }
    private NASMReg getNASMReg(CFGInstAddr opr, NASMWordType wt){
        int num = opr.getNum();
        //16 RAX 19 RDX 20 RSP 21 RBP
        switch (num){
            case 0:
                return null;
            case -1:
                return new NASMReg(21, NASMWordType.QWORD);
            case -2:
                return new NASMReg(16, wt);
            case -3:
                return new NASMReg(20, NASMWordType.QWORD);
            case -4:
                return new NASMReg(16, wt);
            case -5:
                return new NASMReg(19, wt);
            case -6:
                return new NASMReg(availableReg[opr.lit3], wt);//colored reg

        }
        for (int i = 0; i < templateMap.length; ++i){
            if (templateMap[i] == num) return new NASMReg(templateReg[i], wt);
        }
        if (curTemplateReg == templateMap.length) curTemplateReg = 0;
        templateMap[curTemplateReg] = num;
        return new NASMReg(templateReg[curTemplateReg++], wt);
    }

    private NASMInst.InstType getNASMOp(CFGInst.InstType op){
        switch (op){
            case op_eq:
            case op_ne:
            case op_ge:
            case op_gt:
            case op_le:
            case op_lt:
                return NASMInst.InstType.CMP;
            case op_add:
                return NASMInst.InstType.ADD;
            case op_sub:
                return NASMInst.InstType.SUB;
            case op_mult:
                return NASMInst.InstType.IMUL;
            case op_div:
                return NASMInst.InstType.IDIV;
            case op_inc:
                return NASMInst.InstType.INC;
            case op_dec:
                return NASMInst.InstType.DEC;
            case op_and:
                return NASMInst.InstType.AND;
            case op_or:
                return NASMInst.InstType.OR;
            case op_xor:
                return NASMInst.InstType.XOR;
            case op_mov:
                return NASMInst.InstType.MOV;
            case op_call:
                return NASMInst.InstType.CALL;
            case op_lea:
                return NASMInst.InstType.LEA;
            case op_not:
                return NASMInst.InstType.NOT;
            case op_neg:
                return NASMInst.InstType.NEG;
            case op_shl:
                return NASMInst.InstType.SAL;
            case op_shr:
                return NASMInst.InstType.SAR;
            case op_jmp:
                return NASMInst.InstType.JMP;
            case op_jcc: // jump if false , so revOp
                return  NASMInst.newJccRevOp(lastOp);
            case op_return:
                return NASMInst.InstType.RET;
            //case op_decl
            //case op_push
            //case op_pop
            default:
                return NASMInst.InstType.NOP;
        }
    }
    private int getExp2(int x){
        int k = 1;
        for (; k < x; k <<= 1);
        return k;
    }
}
