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

    public CFGNode mergeNode(CFGNode n1, CFGNode n2){
        n1.nextNodes.clear();
        n1.nextNodes.addAll(n2.nextNodes);

        n1.insts.addAll(n2.insts);
        for (CFGNode i : n2.nextNodes){
            int idx = i.prevNodes.indexOf(n2);
            if (idx != -1) i.prevNodes.set(idx, n1);
        }
        for (CFGNode i : n2.prevNodes){
            if (i.ID != n1.ID){
                int idx = i.nextNodes.indexOf(n2);
                if (idx != -1) i.nextNodes.set(idx, n1);
                n1.prevNodes.add(i);
            }
        }
        nodes.remove(n2);
        return n1;
    }
    public CFGProcess getProc(String name){
        for (CFGProcess i: processList){
            if (i.name.equals(name)) return i;
        }
        return null;
    }
}
