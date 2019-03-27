package com.company;

import com.company.common.CompileError;
import com.company.frontend.AST.ASTCompilationUnitNode;
import com.company.frontend.ASTBuilder;
import com.company.frontend.GlobalSymbolTableBuilder;
import com.company.frontend.SemanticAnalyzer;
import com.company.frontend.SymbolTable.SymbolTable;
import com.company.frontend.SyntaxErrorListener;
import com.company.frontend.parser.mxLexer;
import com.company.frontend.parser.mxParser;
import org.antlr.v4.runtime.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        CompileError compileError = new CompileError();
        mxParser.ProgramContext root = null;
        mxParser parser = null;
        try{
            //mxLexer lexer = new mxLexer(CharStreams.fromStream(System.in));
            mxLexer lexer = new mxLexer(CharStreams.fromFileName("main.mx"));
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

        SymbolTable ST = new SymbolTable(compileError);
        new GlobalSymbolTableBuilder(ST, compileError).visitCompilationUnitNode(cu);
        new SemanticAnalyzer(ST, compileError).visitCompilationUnitNode(cu);
/*
        if (compileError.getCounter() > 0) {
            compileError.print();
            throw new Error();
        }*/
    }
}
