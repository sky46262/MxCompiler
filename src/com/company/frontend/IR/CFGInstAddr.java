package com.company.frontend.IR;

import com.company.optimization.info.VarInfo;

import java.util.Objects;
import java.util.Vector;

public class CFGInstAddr {
    public enum addrType{
        a_static, a_label,
        a_reg, a_mem, a_stack,
        a_imm
    }
    public String strLit;
    public int lit1, lit2, lit3, lit4;
    public addrType a_type;
    public CFGInstAddr addr1, addr2;
    public VarInfo info = new VarInfo();
    public CFGInstAddr(addrType _t, int _l1, int _l2, int _l3, int _l4){
        a_type = _t;
        lit1 = _l1; //reg1
        lit2 = _l2; //reg2
        lit3 = _l3; //scale/size
        lit4 = _l4; //num
    }
    public void copy(CFGInstAddr _a){
        a_type = _a.a_type;
        lit1 = _a.lit1;
        lit2 = _a.lit2;
        lit3 = _a.lit3;
        lit4 = _a.lit4;
        addr1 = _a.addr1;
        addr2 = _a.addr2;
        //strLit = _a.strLit;
    }
    public int getNum(){
        return lit4;
    }
    public int getSize(){
        return lit3;
    }
    public boolean equals(CFGInstAddr o){
        if (o == null || this.a_type != o.a_type) return false;
        else{
            switch (this.a_type){
                case a_static:
                    return this.strLit.equals(o.strLit);
                case a_label:
                    return this.strLit.equals(o.strLit);
                case a_reg:
                    return this.lit4 == o.lit4 && this.lit3 == o.lit3;
                case a_imm:
                    return this.lit4 == o.lit4;//value in lit4
                case a_mem:
                    return Objects.equals(addr1, o.addr1) && Objects.equals(addr2, o.addr2)&& this.lit3 == o.lit3 && this.lit4 == o.lit4;
                case a_stack:
                    return this.lit4 == o.lit4;
            }
            return false;
        }
    }
    private static int stackCnt = 0;
    private static int regCnt = 0;
    public static Vector<CFGInstAddr> regList = new Vector<>();
    //strLit, lit3
    public static CFGInstAddr newStaticAddr(String name, int size){
        CFGInstAddr newAddr= new CFGInstAddr(addrType.a_static,0,0,size,0);
        newAddr.strLit = name;
        return newAddr;
    }

    //strLit
    public static CFGInstAddr newLabelAddr(String label){
        CFGInstAddr newAddr = new CFGInstAddr(addrType.a_label, 0,0,0,0);
        newAddr.strLit = label;
        return newAddr;
    }
    public static CFGInstAddr newLabelAddr(CFGNode node){
        return newLabelAddr(node.name);
    }

    //lit4
    public static CFGInstAddr newRegAddr(){
        CFGInstAddr newAddr = new CFGInstAddr(addrType.a_reg, 0,0,0,++regCnt);
        regList.add(newAddr);
        return newAddr;
    }
    public static CFGInstAddr newColoredRegAddr(int idx){
        CFGInstAddr newAddr = new CFGInstAddr(addrType.a_reg, 0,0,idx, -6);
        return newAddr;
    }
    //for some special register
    // -2
    public static CFGInstAddr newRegAddr(int idx){
        CFGInstAddr newAddr = new CFGInstAddr(addrType.a_reg, 0,0,0, idx);
        return newAddr;
    }
    public static int getRegSize(){
        return regCnt;
    }

   //lit3,lit4,addr1,addr2
    //addr1 + addr2 * lit3 + lit4
    public static CFGInstAddr newMemAddr(CFGInstAddr base, CFGInstAddr offset, int size, int num){
        CFGInstAddr newAddr = new CFGInstAddr(addrType.a_mem, 0,0,size,num);
        newAddr.addr1 = base;
        newAddr.addr2 = offset;
        return newAddr;
    }
    //lit3
    public static CFGInstAddr newStackAddr(int size){
        CFGInstAddr newAddr = new CFGInstAddr(addrType.a_stack, 0, 0, size, ++stackCnt);
        return newAddr;

    }
    //lit1, lit2
    public static CFGInstAddr newImmAddr(int l1){
        CFGInstAddr newAddr = new CFGInstAddr(addrType.a_imm, 0, 0, 0,l1);
        newAddr.info.type = VarInfo.valueType.v_const;
        newAddr.info.constValue= l1;
        return newAddr;
    }
    public boolean isConst(){
        //return a_type == addrType.a_imm;
        return info.type == VarInfo.valueType.v_const;
    }
    public int getConst(){
       // assert a_type == addrType.a_imm;
        if (info.type == VarInfo.valueType.v_const)
            return info.constValue;
        else return 0;
    }
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(a_type.toString().toLowerCase()).append(' ');
        builder.append(lit1).append(' ').append(lit2).append(' ').append(lit3).append(' ').append(lit4);
        builder.append(' ').append(strLit);
        return builder.toString();
    }
}
