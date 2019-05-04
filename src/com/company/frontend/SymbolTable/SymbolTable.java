package com.company.frontend.SymbolTable;

import com.company.common.CompileError;
import com.company.frontend.AST.ASTBaseNode;
import com.company.frontend.AST.ASTDeclNode;
import com.company.frontend.IR.CFG;
import com.company.frontend.IR.CFGInstAddr;

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
    private CFG cfg;
    public SymbolTable(CFG _cfg, CompileError _ce){
        cfg = _cfg;
        ce = _ce;
        tableStack = new Stack<>();
    }
    public void pop(){ tableStack.pop(); }
    public void push(String name){ tableStack.push(new ScopeSymbolTable(name));}
    public void pushSymbol(String str, SymbolInfo info){
        //if (info == null) ???
        tableStack.peek().table.put(str,info);
    }
    public SymbolInfo pushSymbol(String str, SymbolType type, ASTBaseNode node){
        return pushSymbol(str, type, node, null, null);
    }
    public SymbolInfo pushSymbol(String str, SymbolType type, ASTBaseNode node, String curClassName, String curFuncName){
            //redefinition
            if (tableStack.peek().table.containsKey(str)) {
                ce.add(CompileError.ceType.ce_redef, type + " " + str, node.pos);
                return null;
            }
            SymbolInfo info = new SymbolInfo(type, ++cnt);
            if (type.type == SymbolType.symbolType.FUNC){
                if (tableStack.size() == 1){
                    info.startNode = cfg.addNode();
                    if (curClassName == null) info.startNode.name = "_func_"+str;
                    else if (curClassName == "_lib_"){
                        if (str.startsWith("string"))
                            info.startNode.name = "_lib_str_" + str.substring(7);
                        else info.startNode.name = "_lib_" + str;
                    }
                }
                else info.startNode.name = "_class_"+ str;
            }
            else if (node instanceof ASTDeclNode){
                //in function -> reg
                if (curFuncName != null){
                    ((ASTDeclNode) node).reg = info.reg = CFGInstAddr.newRegAddr();
                }
                else if (curClassName != null){
                    //member -> imm(size)
                    SymbolInfo classInfo = findSymbol(curClassName);
                    if (classInfo == null)
                        ce.add(CompileError.ceType.ce_nodecl, curClassName, node.pos);
                    else {
                        ((ASTDeclNode) node).reg = info.reg = CFGInstAddr.newImmAddr(classInfo.type.getMemSize());
                        classInfo.type.memSize += type.getMemSize();
                    }

                } else {
                    //global -> mem
                    int size = type.getMemSize();
                    ((ASTDeclNode) node).reg = info.reg =
                            CFGInstAddr.newMemAddr(CFGInstAddr.newStaticAddr("_v_"+str, size), CFGInstAddr.newImmAddr(0),0,0);
                }
            }
            tableStack.peek().table.put(str, info);
            return info;
    }
    public SymbolInfo findSymbol(String name){
        for (int i = tableStack.size() - 1; i >=0 ;i--){
            ScopeSymbolTable t = tableStack.get(i);
            if (t.table.containsKey(name)) return t.table.get(name);
        }
        //not found
        return null;
    }
}
