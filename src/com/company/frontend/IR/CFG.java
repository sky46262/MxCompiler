package com.company.frontend.IR;

import java.util.HashSet;
import java.util.Vector;

public class CFG {
    private HashSet<CFGNode> nodes = new HashSet<>();
    public Vector<CFGData> dataList = new Vector<>(); //list of static data
    public Vector<CFGProcess> processList = new Vector<>();
    public CFGNode entryNode = null;

    public CFG(){}

    public void addNode(CFGNode node) {nodes.add(node);}
    public CFGNode addNode(){
        int idx = nodes.size();
        CFGNode newNode = new CFGNode(idx);
        nodes.add(newNode);
        return newNode;
    }

    //public CFGNode mergeNode(CFGNode n1, CFGNode n2){}
    public CFGProcess getProc(String name){
        for (CFGProcess i: processList){
            if (i.name.equals(name)) return i;
        }
        return null;
    }
}
