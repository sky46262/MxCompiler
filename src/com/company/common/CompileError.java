package com.company.common;

import com.company.common.Position;
//import java.util.HashMap;
import java.util.Vector;

public class CompileError {
    private Vector<String> msgList = new Vector<>();
    private int counter;
    public enum ceType{
        ce_default,
        ce_syntax,
        ce_invalid_constructor,
        ce_invalid_type,
        ce_redef,
        ce_nodecl,
        ce_outofloop,
        ce_outofclass,
        ce_type,
        ce_lvalue,
    }

    public int getCounter() {
        return counter;
    }

    public void add(ceType type, String msg, Position pos){
        String m = pos.toString() + (" error: ") + type.toString() + ":" + msg +"\n";
        msgList.add(m);
        //System.out.println(m);
        //counter++;
        print();
        throw new Error();
    }
    public void print(){
        msgList.forEach(System.err::println);
    }
}
