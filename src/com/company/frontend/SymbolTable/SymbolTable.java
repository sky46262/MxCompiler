package com.company.frontend.SymbolTable;

import com.company.common.CompileError;
import com.company.frontend.AST.ASTBaseNode;

import java.util.HashMap;
import java.util.Stack;

public class SymbolTable {
    public class ScopeSymbolTable{
        HashMap<String, SymbolInfo> table;
        String scopeName;
        ScopeSymbolTable(String name){
            scopeName = name;
            table = new HashMap<>();
        }
    }

    public Stack<ScopeSymbolTable> tableStack;
    private CompileError ce;
    private Integer cnt = 0;
    public SymbolTable(CompileError _ce){
        ce = _ce;
        tableStack = new Stack<>();
    }
    public void pop(){ tableStack.pop(); }
    public void push(String name){ tableStack.push(new ScopeSymbolTable(name));}
    public void pushSymbol(String str, SymbolInfo info){
        //if (info == null) ???
        tableStack.peek().table.put(str,info);
    }
    public void pushSymbol(String str, SymbolType type, ASTBaseNode node){
        //redefinition
        if (tableStack.peek().table.containsKey(str)) {
            ce.add(CompileError.ceType.ce_redef, type + " " + str, node.pos);
            return;
        }
        SymbolInfo info = new SymbolInfo(type, ++cnt);
        //TODO cfg&IR
        tableStack.peek().table.put(str, info);
    }
    public SymbolInfo findSymbol(String name){
        for (int i = tableStack.size() - 1; i >=0 ;i--){
            ScopeSymbolTable t = tableStack.elementAt(i);
            if (t.table.containsKey(name)) return t.table.get(name);
        }
        //not found
        return null;
    }
}
