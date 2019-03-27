package com.company.frontend;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import com.company.common.CompileError;
import com.company.common.Position;

public class SyntaxErrorListener extends BaseErrorListener {
    private CompileError ce;

    public SyntaxErrorListener(CompileError _ce) {
        ce = _ce;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
        ce.add(CompileError.ceType.ce_syntax, msg, new Position(e.getOffendingToken()));
    }
}