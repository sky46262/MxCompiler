package com.company.backend.NASM;

import java.io.*;

public class NASM {
    public enum SectionType{
        BSS, DATA, TEXT
    }
    private SectionType curSection;
    private java.io.BufferedWriter writer;

    public NASM(BufferedWriter _w){
        writer = _w;
        genHeader();
    }
    private void genHeader(){
        genLine(false, "default rel");
        String libPath = "./lib/lib.asm";
        BufferedReader  reader = null;
        try {
            reader = new BufferedReader(new FileReader(libPath));
            String t;
            for (t = reader.readLine(); t != null; t= reader.readLine()){
                writer.write(t);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
    public void genLine(boolean indent, String str){
        try {
            if (indent) writer.write('\t');
            if (str != null) writer.write(str);
            writer.newLine();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void genLine(){
        genLine(false, null);
    }
    public void genText(String str){
        if (curSection != SectionType.TEXT) defSection(SectionType.TEXT);
        genLine(true, str);
    }

    public void defGlobal(String str){
        genLine(false, "global " + str);
    }
    public void defSection(SectionType type){
        if (curSection == type) return;
        genLine(false, "SECTION ." + type.toString().toLowerCase());
        curSection = type;
     }
    public void defLabel(String str){
        if (curSection != SectionType.TEXT) defSection(SectionType.TEXT);
        genLine(false, str+":");
    }
}
