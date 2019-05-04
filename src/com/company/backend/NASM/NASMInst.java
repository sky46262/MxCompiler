package com.company.backend.NASM;

import com.company.frontend.IR.CFGInst;

public class NASMInst {
    public enum InstType{
        //DB, DW, DD, DQ,
        MOV, PUSH, POP,
        //CWTL, CLTQ, CQTO,
        INC, DEC, NEG, NOT,
        LEAQ, ADD, SUB, XOR, OR, AND,
        SAR, SAL, // arithmetic shift
        IMUL, MUL, IDIV, DIV,
        CMP, TEST, CQO,
        SETE, SETNE, SETS, SETNS, SETG, SETGE, SETL, SETLE, SETA, SETAE, SETB, SETBE,
        JMP, JE, JNE, JS, JNS, JG, JGE, JL, JLE, JA, JAE, JB, JBE,
        CMOVE, CMOVNE, CMOVS, CMOVNS, CMOVG, CMOVGE, CMOVL, CMOVLE, CMOVA, CMOVAE, CMOVB, CMOVBE,
        CALL, LEAVE, RET, NOP
    }
    public static InstType newJccRevOp(CFGInst.InstType cmp){
        switch (cmp){
            case op_eq:
                return InstType.JNE;
            case op_ne:
                return InstType.JE;
            case op_le:
                return InstType.JG;
            case op_ge:
                return InstType.JL;
            case op_lt:
                return InstType.JGE;
            case op_gt:
                return InstType.JLE;
        }
        //reverse
        return InstType.JE;
    }
    public static InstType newJccOp(CFGInst.InstType cmp){
        switch (cmp){
            case op_eq:
                return InstType.JE;
            case op_ne:
                return InstType.JNE;
            case op_le:
                return InstType.JLE;
            case op_ge:
                return InstType.JGE;
            case op_lt:
                return InstType.JL;
            case op_gt:
                return InstType.JG;
        }
        //??? JZ
        return InstType.JNE;
    }

    public InstType op;
    public NASMAddr opr1;
    public NASMAddr opr2;

    public NASMInst(InstType _op, NASMAddr _opr1, NASMAddr _opr2){
        op = _op;
        opr1 = _opr1;
        opr2 = _opr2;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(op.name().toLowerCase());
        if (opr1 != null) sb.append('\t').append(opr1);
        if (opr2 != null) sb.append(", ").append(opr2);
        return sb.toString();
    }
}
