package com.company.backend.NASM;

public class NASMImmAddr extends NASMAddr{
    private int val;
    public NASMImmAddr(int _v) {
        val = _v;
    }
    @Override
    public String toString(){
        return Integer.toString(val);
    }
}
