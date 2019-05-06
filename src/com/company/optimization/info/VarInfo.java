package com.company.optimization.info;

public class VarInfo {
    public enum valueType{
        v_const, v_undefine, v_indefine
    }
    public valueType type;

    public int constValue;
    public int readCnt = 0;
    public int writeCnt = 0;

    public VarInfo(){
        type = valueType.v_undefine;
        constValue = 0;
    }

    public int getReferrenceCnt(){
        return readCnt + writeCnt;
    }

}
