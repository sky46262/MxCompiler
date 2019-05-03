package com.company.backend.NASM;

public enum NASMWordType {
    BYTE, WORD, DWORD, QWORD;

    public static int getSize(NASMWordType _t){
        switch (_t){
            case BYTE:
                return 1;
            case WORD:
                return 2;
            case DWORD:
                return 4;
            case QWORD:
                return 8;
        }
        return 0;
    }
}
