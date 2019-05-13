package com.company.frontend.IR;

public class CFGProcess {
    public String name;
    public CFGNode entryNode;
    public CFGNode exitNode;
    public int paramCnt;

    public boolean isCaller;
    public boolean isCallee;

    public int stackSize;
    //stackSize of process

    public CFGProcess(CFGNode _s, CFGNode _e){
        entryNode = _s;
        exitNode = _e;
        name = _s.name;
    }

}
