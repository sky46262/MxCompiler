package com.company.backend.NASM;

public class NASMRegAddr extends NASMAddr {
    NASMReg reg;

    public NASMRegAddr(NASMReg _r){
        reg = _r;
    }
    public NASMRegAddr(int id, NASMWordType wt){
        reg = new NASMReg(id, wt);
    }

    @Override
    public String toString(){
        return reg.toString();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof NASMRegAddr) && ((NASMRegAddr) o).reg.equals(reg);
    }
}
