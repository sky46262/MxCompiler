package com.company.frontend.SymbolTable;

import com.company.frontend.IR.CFGInstAddr;
import com.company.frontend.IR.CFGNode;

public class SymbolInfo {
    public SymbolType type;
    public Integer id;
    public CFGInstAddr reg = null;
    public CFGNode startNode = null;
    public  SymbolInfo(SymbolType _t, Integer _i){
        type = _t;
        id = _i;
    }

}
