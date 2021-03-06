package com.company;

import com.company.backend.GlobalRegAllocator;
import com.company.backend.NASM.*;
import com.company.backend.NASMAdapter;
import com.company.backend.NASMBuilder;
import com.company.backend.StackAllocator;
import com.company.common.CompileError;
import com.company.frontend.*;
import com.company.frontend.AST.ASTCompilationUnitNode;
import com.company.frontend.IR.CFG;
import com.company.frontend.SymbolTable.SymbolTable;
import com.company.frontend.parser.mxLexer;
import com.company.frontend.parser.mxParser;
import com.company.optimization.*;
import org.antlr.v4.runtime.*;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        CompileError compileError = new CompileError();
        mxParser.ProgramContext root = null;
        mxParser parser = null;
        boolean isTest = true;
        try{
            mxLexer lexer;
            if (isTest) lexer = new mxLexer(CharStreams.fromStream(System.in));
            else  lexer = new mxLexer(CharStreams.fromFileName("main.mx"));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            parser = new mxParser(tokens);
            SyntaxErrorListener errorListener = new SyntaxErrorListener(compileError);
            parser.removeErrorListeners();
            parser.addErrorListener(errorListener);
            root = parser.program();
        }
        catch (IOException e) {
            throw new Error();
        }
        catch (RecognitionException e){
            compileError.print();
            throw  new Error();
        }

        ASTCompilationUnitNode cu = new ASTBuilder().visitProgram(root);
        CFG cfg = new CFG();
        SymbolTable ST = new SymbolTable(cfg,compileError);
        new GlobalSymbolTableBuilder(ST, compileError).visitCompilationUnitNode(cu);
        new SemanticAnalyzer(ST, compileError).visitCompilationUnitNode(cu);
/*
        if (compileError.getCounter() > 0) {
            compileError.print();
            throw new Error();
        }*/
        new CFGBuilder(cfg).visitCompilationUnitNode(cu);
        new CFGReducer(cfg);
        new CFGReducer(cfg);

        new NASMAdapter(cfg);//adapt twice ???

        new PeepholeOptimizer(cfg);
        new VarAnalyzer(cfg);
        new DataFlowAnalyzer(cfg);
        new UselessCodeEliminater(cfg);

        new GlobalRegAllocator(cfg);
        new StackAllocator(cfg);
        new NASMAdapter(cfg);

        BufferedWriter writer = null;

       if (isTest) writer = new BufferedWriter(new PrintWriter(System.out));
       else {
           try {
               writer = new BufferedWriter(new FileWriter("test.asm"));
           } catch (IOException e) {
               e.printStackTrace();
               return;
           }
       }
        NASM nasm = new NASM(writer);
        new NASMBuilder(cfg, nasm);
        writer.close();
    }
}
