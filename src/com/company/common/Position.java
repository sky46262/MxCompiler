package com.company.common;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

public class Position {
    public int startLine, startColumn, endLine, endColumn;

    public Position(int l, int c){
        startLine = endLine = l;
        startColumn = endColumn = c;
    }
    public Position(int l, int c, int el, int ec){
        startLine = l;
        startColumn = c;
        endLine = el;
        endColumn = ec;
    }

    public Position(ParserRuleContext ctx){
        startLine = ctx.start.getLine();
        startColumn = ctx.start.getCharPositionInLine();
        endLine = ctx.stop.getLine();
        endColumn = ctx.stop.getCharPositionInLine();
    }

    public Position(Token t){
        startLine = endLine = t.getLine();
        startColumn = t.getCharPositionInLine();
        endColumn = startColumn + t.getText().length();
    }

    public Position(TerminalNode node){
        this(node.getSymbol());
    }

    public String toString(){
        return "line " + startLine + ":" + startColumn + ((startLine != endLine || startColumn != endColumn)?
                (" to line " + endLine + ":" + endColumn) : "");
    }
}
