package com.company.optimization.info;

import com.company.frontend.IR.CFGInstAddr;

import java.util.HashSet;

public class RegInfo {
    public HashSet<Integer> toNodes = new HashSet<>();
    public int color = 0;
    public VarInfo varInfo;

    public RegInfo(int t){
        if (t <= CFGInstAddr.getRegSize())
        varInfo = CFGInstAddr.regList.get(t-1).info;
        else varInfo = new VarInfo();
    }
    public int getDegree() {
        return toNodes.size();
    }
}
