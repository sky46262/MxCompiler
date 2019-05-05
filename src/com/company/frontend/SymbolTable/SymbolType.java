package com.company.frontend.SymbolTable;

import com.company.frontend.AST.ASTTypeNode;

import java.util.Vector;

public class SymbolType {
    public enum symbolType{
        BOOL, INT, STR, VOID, FUNC, CLASS, NULL
    }
    public static SymbolType intSymbolType = new SymbolType(symbolType.INT);
    public static SymbolType boolSymbolType = new SymbolType(symbolType.BOOL);
    public static SymbolType strSymbolType = new SymbolType(symbolType.STR);
    public static SymbolType voidSymbolType = new SymbolType(symbolType.VOID);
    public static SymbolType funcSymbolType = new SymbolType(symbolType.FUNC);
    public static SymbolType classSymbolType = new SymbolType(symbolType.CLASS);
    public static SymbolType nullSymbolType = new SymbolType(symbolType.NULL);
    public symbolType type;
    public String name;
    public int arrayDim;
    public Vector<SymbolType> memList;
    public Integer ClassMemSize = 0;
    public SymbolType(symbolType _t){
        type = _t;
        name = null;
        arrayDim = 0;
        memList = null;
    }
    public SymbolType(symbolType _t, String _n, int _d){
        type = _t;
        name = _n;
        arrayDim = _d;
        memList = null;
    }
    //for class
    public SymbolType(symbolType _t, String _n, int _d, Vector<SymbolType> _v) {
        type = _t;
        name = _n;
        arrayDim = _d;
        memList = _v;
    }
    public SymbolType(Vector<SymbolType> _v){
        type = symbolType.FUNC;
        name = null;
        arrayDim = 0;
        memList = _v;
    }

    public SymbolType(ASTTypeNode node){
        switch (node.nodeType){
            case t_bool:
                type = symbolType.BOOL;
                break;
            case t_void:
                type = symbolType.VOID;
                break;
            case t_int:
                type =  symbolType.INT;
                break;
            case t_str:
                type = symbolType.STR;
                break;
            case t_class:
                type = symbolType.CLASS;
                break;
        }
        name = node.className;
        arrayDim = node.dimension;
    }
    public SymbolType getDerefType(){
        assert arrayDim >= 1;
            return new SymbolType(type, name, arrayDim -1);
    }

    public boolean equals(SymbolType _t){
        if (_t == null) return false;
        if (type == symbolType.NULL)  return _t.arrayDim >0 || _t.type == symbolType.NULL || _t.type == symbolType.CLASS;
        if (_t.type == symbolType.NULL) return arrayDim >0 || type == symbolType.CLASS;
        if ((type == _t.type) && (arrayDim == _t.arrayDim)) {
            if (type == symbolType.CLASS) {
                if (name != null) return name.equals(_t.name);
                else return _t.name == null;
            }
            else return true;
        }
        return false;
    }
    public int getClassMemSize(){
        if(type == symbolType.CLASS) return ClassMemSize;
        else return 0;
    }
    public int getMemSize() {
        if (arrayDim >0) return 8;
        switch (type){
            case CLASS:
            case INT:
            case STR:
            case NULL:
            case BOOL:
                return 8;
                default:
                    return 0;
        }
    }
}
