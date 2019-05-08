package com.company.frontend.IR;

import com.company.optimization.info.InstInfo;

import java.util.Vector;

public class CFGInst {
    public enum InstType{
        op_add, op_sub, op_mult, op_div, op_mod,
        op_pos, op_neg, op_not,
        op_and, op_or, op_xor, op_shl, op_shr,
        op_eq, op_ne, op_le, op_ge, op_lt, op_gt,
        op_jmp, op_jcc, //condition code
        op_call, op_return,op_mov,
        op_nop, op_push, op_pop,
        op_wpara, op_rpara, //w for call, r for decl
        op_inc, op_dec,
        op_lea//load effective address
        //op_phi, op_int
    }
    public InstType op;
    public Vector<CFGInstAddr> operands;
    public InstInfo info = new InstInfo();
    public CFGInst(InstType _op){
        op = _op;
        operands = new Vector<>();
    }

    public CFGInst(InstType _op, Vector<CFGInstAddr> _oprs){
        op = _op;
        operands = _oprs;
    }

    public CFGInst(InstType _op, CFGInstAddr opr1, CFGInstAddr opr2){
        op = _op;
        operands = new Vector<>();
        if (opr1 != null) addOperand(opr1);
        if (opr2 != null) addOperand(opr2);
    }

    public void addOperand(CFGInstAddr addr){
        assert addr != null;
        operands.add(addr);
    }
    public static boolean isCommutable(InstType op){
        switch (op){
            case op_add:
            case op_mult:
            case op_and:
            case op_or:
            case op_xor:
            case op_eq:
            case op_ne:
                return true;
            default:
                return false;
        }
    }
    public boolean isCommutable(){return isCommutable(this.op); }
    public boolean isCompare(){
        return isCompare(this.op);
    }
    public static boolean isCompare(InstType op){
        switch (op){
            case op_eq:
            case op_ge:
            case op_le:
            case op_gt:
            case op_lt:
            case op_ne:
                return true;
        }
        return false;
    }

}
