package com.company.backend;

import com.company.frontend.IR.*;
import com.company.optimization.InterferenceGraph;
import com.company.optimization.info.RegInfo;
import java.util.Comparator;
import java.util.*;

public class GlobalRegAllocator {
    private CFG cfg;
    private HashSet<Integer> visitFlag = new HashSet<>();
    private static int curChroma = 4;
    HashSet<Integer> colorSet = new HashSet<>();
    private InterferenceGraph graph;

    public GlobalRegAllocator(CFG _cfg){
        cfg = _cfg;
        for (int i = 1; i <= curChroma; i++) {
            colorSet.add(i);
        }
        visitCFG();
    }
    private void visitCFG(){
        for (CFGProcess i : cfg.processList) {
            graph = new InterferenceGraph(i);
            visitInterferenceGraph(graph);
            visitCFGNode(i.entryNode);
        }
    }

    private class RegPair{
        int a;
        RegInfo b;
        public RegPair(int _a, RegInfo _b){
            a = _a;
            b = _b;
        }
    }
    private void visitInterferenceGraph(InterferenceGraph graph) {
        PriorityQueue<RegPair> delQueue = new PriorityQueue<>((o1, o2) -> {
            float t = spillMetric(o2.b) - spillMetric(o1.b);
            if (t < 0) return -1;
            if (t > 0) return 1;
            return 0;
        });
        for (Map.Entry<Integer, RegInfo> entry : graph.map.entrySet()) {
            delQueue.add(new RegPair(entry.getKey(),entry.getValue()));
        }
        while (!delQueue.isEmpty()){
            RegPair pair = delQueue.poll();
            colorNode(pair.a);
        }
    }

    private boolean colorNode(Integer a) {
        RegInfo r = graph.getNode(a);
        HashSet<Integer> col = new HashSet<>(colorSet);
        for (Integer i : r.toNodes) {
            RegInfo r1 = graph.getNode(i);
            if (r1.color != 0) col.remove(r1.color);
        }
        if (col.isEmpty()){
            r.color = -1;
            return false;
        }
        r.color = col.iterator().next();
        return true;
    }

    private void visitCFGNode(CFGNode node){
        if (visitFlag.contains(node.ID)) return;
        visitFlag.add(node.ID);
        node.insts.forEach(this::visitCFGInst);
        node.nextNodes.forEach(this::visitCFGNode);
    }
    private void visitCFGInst(CFGInst inst){
        Vector<CFGInstAddr> operands = inst.operands;
        for (int i = 0; i < operands.size(); i++){
            CFGInstAddr addr = operands.get(i);
            assert (addr != null);
            if (addr.a_type == CFGInstAddr.addrType.a_reg && addr.getNum() > 0){
                int color = graph.getNode(addr.getNum()).color;
               if (color >= 0)
                    addr.copy(CFGInstAddr.newColoredRegAddr(color));
                else
                    addr.copy(CFGInstAddr.newStackAddr(8));
            }
        }
    }

    private float spillMetric(RegInfo info){
        return (float)(25 + info.varInfo.getReferrenceCnt()) / info.getDegree();
    }
}