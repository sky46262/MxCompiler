package com.company.optimization;

import com.company.frontend.IR.CFGInst;
import com.company.frontend.IR.CFGInstAddr;
import com.company.frontend.IR.CFGNode;
import com.company.frontend.IR.CFGProcess;
import com.company.optimization.info.RegInfo;

import java.util.HashMap;
import java.util.HashSet;

public class InterferenceGraph {
    public HashMap<Integer, RegInfo> map = new HashMap<>();

    private HashSet<Integer> visitFlag = new HashSet<>();
    public InterferenceGraph(CFGProcess proc){
        map.clear();
        buildGraph(proc.entryNode);
    }
    private void buildGraph(CFGNode node){
        if (visitFlag.contains(node.ID)) return;
        visitFlag.add(node.ID);

        HashSet<Integer> liveNow = new HashSet<>();
        CFGInst last_inst = null;
        for (int i = node.insts.size()-1; i>=0; i--){
            CFGInst inst = node.insts.get(i);
            if (last_inst == null) liveNow.addAll(inst.info.LiveOut);
            last_inst = inst;

            if (inst.op == CFGInst.InstType.op_mov && inst.operands.get(1).a_type == CFGInstAddr.addrType.a_reg)
                liveNow.removeAll(inst.info.usedReg);
                for (Integer defReg : inst.info.defReg) {
                    for (Integer t : liveNow) {
                        addEdge(defReg, t);
                    }
                }
            liveNow.removeAll(inst.info.defReg);
            liveNow.addAll(inst.info.usedReg);
        }

        node.nextNodes.forEach(this::buildGraph);
    }

    public RegInfo getNode(int t){
        if (map.containsKey(t)) return map.get(t);
        else {
            RegInfo info = new RegInfo(t);
            map.put(t, info);
            return info;
        }
    }

    public void addEdge(int u, int v){
        if (u == v) return;
        getNode(u).toNodes.add(v);
        getNode(v).toNodes.add(u);
    }
}
