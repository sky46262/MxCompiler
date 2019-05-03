package com.company.frontend.IR;

public class CFGData {
    public enum dataType{
        d_str, d_num,d_res
    }
    public dataType dType;
    public String name;
    public int size;

    public String strValue;
    public int intValue;

    public CFGData(String _name, int _value, boolean isVal){
        name = _name;
        if (isVal){
            dType = dataType.d_num;
            intValue = _value;
        }
        else{
            dType = dataType.d_res;
            size = _value;
        }
    }
    public CFGData(String _name, String _value){
        name = _name;
        dType = dataType.d_str;
        strValue = _value;
    }
}
