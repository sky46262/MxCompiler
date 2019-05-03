package com.company.backend.NASM;

public class NASMLabelAddr extends NASMAddr {
    private String label;

    public NASMLabelAddr(String _l) {
        label = _l;
    }

    @Override
    public String toString() {
        return label;
    }
}
