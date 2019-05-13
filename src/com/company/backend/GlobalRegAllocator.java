package com.company.backend;

import com.company.frontend.IR.*;
import com.company.optimization.InterferenceGraph;
import com.company.optimization.info.RegInfo;
import java.util.*;

public class GlobalRegAllocator {
    private CFG cfg;
    private HashSet<Integer> visitFlag = new HashSet<>();
    private static int curChroma = 4; //todo there are still some problems
    private static int funcChroma = 4;
    //limit reg of function  or push callerSavedReg
    HashSet<Integer> colorSet = new HashSet<>();
    private InterferenceGraph graph;
    private int regSize = CFGInstAddr.getRegSize();

    public GlobalRegAllocator(CFG _cfg){
        cfg = _cfg;
        visitCFG();
    }
    private void visitCFG(){
        for (CFGProcess i : cfg.processList) {
            //not allocate callerSavedReg for function
            colorSet.clear();
            if (i.entryNode.name.equals("main"))
                for (int j = 1; j <= curChroma; j++) {
                    colorSet.add(j);
                }
            else for (int j = 1; j <= funcChroma; j++) {
                colorSet.add(j);
            }
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
       for (int i = 8; i <= 23; i++){
            RegInfo reg = graph.getNode(regSize + i);
            switch (i){
                case 8:
                    reg.color = 9;
                    break;
                case 9:
                    reg.color = 10;
                    break;
                case 12:
                    reg.color = 1;
                    break;
                case 13:
                    reg.color = 2;
                    break;
                case 14:
                    reg.color = 3;
                    break;
                case 15:
                    reg.color = 4;
                    break;
                case 23:
                    reg.color = 5;
                    break;
                case 22:
                    reg.color = 6;
                    break;
                case 19:
                    reg.color = 7;
                    break;
                case 18:
                    reg.color = 8;
                    break;
                default:
                    reg.color = -1;
            }
        }

        while (!delQueue.isEmpty()){
            RegPair pair = delQueue.poll();
            colorNode(pair.a);
        }
    }

    private boolean colorNode(Integer a) {
        RegInfo r = graph.getNode(a);
        if (r.color != 0){
            return r.color!= -1;
        }
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
        for (CFGInstAddr addr : operands) {
            assert (addr != null);
            if (addr.a_type == CFGInstAddr.addrType.a_reg && addr.getNum() > 0) {
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