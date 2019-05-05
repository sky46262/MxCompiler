package com.company.frontend;
import com.company.common.Position;
import com.company.frontend.AST.*;
import com.company.frontend.parser.mxBaseVisitor;
import com.company.frontend.parser.mxParser;

import java.util.Vector;

public class ASTBuilder extends mxBaseVisitor<ASTBaseNode>{
    @Override public ASTCompilationUnitNode visitProgram(mxParser.ProgramContext ctx) {
        Vector<ASTStmtNode> v = new Vector<>();
        for (mxParser.ProgStatementContext i : ctx.progStatement()){
            v.add(visitProgStatement(i));
        }
        return new ASTCompilationUnitNode(new Position(ctx), v);
    }
    @Override public ASTStmtNode visitProgStatement(mxParser.ProgStatementContext ctx) {
            if (ctx.classDeclaration() != null)  return visitClassDeclaration(ctx.classDeclaration());
            if (ctx.declaration() != null) return visitDeclaration(ctx.declaration());
            if (ctx.functionDeclaration() != null) return visitFunctionDeclaration(ctx.functionDeclaration());
            return new ASTStmtNode(new Position(ctx), ASTNodeType.s_empty, null);
    }

    @Override public ASTClassDeclNode visitClassDeclaration(mxParser.ClassDeclarationContext ctx) {
            Vector<ASTStmtNode> v = new Vector<>();
            for (mxParser.ClassStatementContext i : ctx.classStatement()){
                v.add(visitClassStatement(i));
            }
            return new ASTClassDeclNode(new Position(ctx), ASTNodeType.s_classdecl, v, ctx.Identifier().getText());
    }
    @Override public ASTStmtNode visitClassStatement(mxParser.ClassStatementContext ctx){
            if (ctx.constructFunction() != null) return visitConstructFunction(ctx.constructFunction());
            if (ctx.declaration() != null) return visitDeclaration(ctx.declaration());
            if (ctx.functionDeclaration() != null) return visitFunctionDeclaration(ctx.functionDeclaration());
            return new ASTStmtNode(new Position(ctx), ASTNodeType.s_empty, null);
    }

    @Override public ASTFuncDeclNode visitConstructFunction(mxParser.ConstructFunctionContext ctx) {
            ASTFuncDeclNode node =  new ASTFuncDeclNode(new Position(ctx), ASTNodeType.s_funcdecl,
                    ctx.Identifier().getText(), new ASTTypeNode(new Position(ctx),ASTNodeType.t_void,null,0), null, visitBlock(ctx.block()));
            node.setConstructor();
            return node;
    }

    @Override public ASTFuncDeclNode visitFunctionDeclaration(mxParser.FunctionDeclarationContext ctx) {
        ASTTypeNode type;
        if (ctx.typeName() != null) type = visitTypeName(ctx.typeName());
        else type = new ASTTypeNode(new Position(ctx), ASTNodeType.t_void, "null",0);
            return new ASTFuncDeclNode(new Position(ctx), ASTNodeType.s_funcdecl, ctx.Identifier().getText(),
                    type, visitArgumentList(ctx.argumentList()), visitBlock(ctx.block()));
    }

    @Override public ASTStmtNode visitArgumentList(mxParser.ArgumentListContext ctx) {
        if (ctx == null) return null;
        Vector<ASTStmtNode> v = new Vector<>();
        for (mxParser.DeclaratorContext i : ctx.declarator()){
            v.add(visitDeclarator(i));
        }
        return new ASTStmtNode(new Position(ctx), ASTNodeType.s_paramlist, v);
    }

    @Override public ASTStmtNode visitBlock(mxParser.BlockContext ctx) {
        Vector<ASTStmtNode> v = new Vector<>();
        for (mxParser.BlockStatementContext i : ctx.blockStatement()){
            v.add(visitBlockStatement(i));
        }
        return new ASTStmtNode(new Position(ctx), ASTNodeType.s_block, v);
    }

    @Override public ASTStmtNode visitBlockStatement(mxParser.BlockStatementContext ctx){
        if (ctx.block() != null) return visitBlock(ctx.block());
        if (ctx.declaration() != null) return visitDeclaration(ctx.declaration());
        if (ctx.statement() != null) return visitStatement(ctx.statement());
        return new ASTStmtNode(new Position(ctx), ASTNodeType.s_empty, null);
    }
    @Override public ASTStmtNode visitStatement(mxParser.StatementContext ctx) {
        if (ctx.block() != null) return visitBlock(ctx.block());
        if (ctx.type == null) {
            if (ctx.expression(0) != null) {
                //v.add(visitExpression(ctx.expression(0)));
                return visitExpression(ctx.expression(0));
            }
            else
                return new ASTStmtNode(new Position(ctx), ASTNodeType.s_empty, null);
        }
        Vector<ASTStmtNode> v = new Vector<>();
        switch (ctx.type.getText()){
            case "if":
                v.add(visitExpression(ctx.expression(0)));
                v.add(visitStatement(ctx.statement(0)));
                if (ctx.statement(1) != null) v.add(visitStatement(ctx.statement(1))); // else
                return new ASTStmtNode(new Position(ctx), ASTNodeType.s_if, v);
            case "for":
                if (ctx.init != null)
                v.add(visitExpression(ctx.init));
                else v.add(new ASTExprNode(new Position(ctx),ASTNodeType.e_empty,null));
                if (ctx.cond != null)
                    v.add(visitExpression(ctx.cond));
                else v.add(new ASTExprNode(new Position(ctx),ASTNodeType.e_empty,null));
                if (ctx.step != null)
                    v.add(visitExpression(ctx.step));
                else v.add(new ASTExprNode(new Position(ctx),ASTNodeType.e_empty,null));
                v.add(visitStatement(ctx.statement(0)));
                return new ASTStmtNode(new Position(ctx), ASTNodeType.s_for, v);
            case "return":
                if (ctx.expression().size() > 0) v.add(visitExpression(ctx.expression(0)));
                else v.add(new ASTExprNode(new Position(ctx),ASTNodeType.e_empty,null));
                return new ASTStmtNode(new Position(ctx), ASTNodeType.s_return, v);
            case "while":
                v.add(visitExpression(ctx.expression(0)));
                v.add(visitStatement(ctx.statement(0)));
                return new ASTStmtNode(new Position(ctx), ASTNodeType.s_while, v);
            case "break":
                return new ASTStmtNode(new Position(ctx), ASTNodeType.s_break, v);
            case "continue":
                return new ASTStmtNode(new Position(ctx), ASTNodeType.s_continue, v);
            default:
                return new ASTStmtNode(new Position(ctx), ASTNodeType.s_empty, v);
        }
    }

    @Override public ASTDeclNode visitDeclaration(mxParser.DeclarationContext ctx) {
        return visitDeclarator(ctx.declarator());
    }

    @Override public ASTDeclNode visitDeclarator(mxParser.DeclaratorContext ctx) {
        return new ASTDeclNode(new Position(ctx), ASTNodeType.s_vardecl, ctx.Identifier().getText(), visitExpression(ctx.expression()),
                visitTypeName(ctx.typeName()));
    }

    @Override public ASTExprNode visitExpressionList(mxParser.ExpressionListContext ctx) {
        Vector<ASTExprNode> v = new Vector<>();
        for (mxParser.ExpressionContext i : ctx.expression()){
            v.add(visitExpression(i));
        }
        return new ASTExprNode(new Position(ctx), ASTNodeType.e_list, v);
    }

    @Override public ASTExprNode visitExpression(mxParser.ExpressionContext ctx) {
        if (ctx == null) return new ASTExprNode();
        if (ctx.Identifier() != null) return new ASTPrimNode(new Position(ctx), ASTNodeType.p_id, 0, ctx.Identifier().getText());
        if (ctx.constant() != null) return visitConstant(ctx.constant());
        if (ctx.creator() != null)
            return visitCreator(ctx.creator());
        // expression = '(' expression ')'
        if (ctx.op == null) return visitExpression(ctx.expression(0));
        Vector<ASTExprNode> v = new Vector<>();
        for (mxParser.ExpressionContext i : ctx.expression()){
            v.add(visitExpression(i));
        }
        ASTNodeType type = ASTNodeType.e_empty;
        switch (ctx.op.getText()) {
            case ".":
                type = ASTNodeType.e_member;
                break;
            case "(":
                if (ctx.expressionList() != null) v.add(visitExpressionList(ctx.expressionList()));
                type = ASTNodeType.e_call;
                break;
            case "[":
                type = ASTNodeType.e_idx;
                break;
            case "++":
                if (ctx.getChild(1).equals(ctx.expression(0))){
                    type = ASTNodeType.e_inc_p;
                } else if (ctx.getChild(0).equals(ctx.expression(0))){
                    type = ASTNodeType.e_inc_s;
                }
                break;
            case "--":
                if (ctx.getChild(1).equals(ctx.expression(0))){
                    type = ASTNodeType.e_dec_p;
                } else if (ctx.getChild(0).equals(ctx.expression(0))){
                    type = ASTNodeType.e_dec_s;
                }
                break;
            case "+":
                if (ctx.expression().size() == 2) type = ASTNodeType.e_add;
                else type = ASTNodeType.e_pos;
                break;
            case "-":
                if (ctx.expression().size() == 2) type = ASTNodeType.e_sub;
                else type = ASTNodeType.e_neg;
                break;
            case "*":
                type = ASTNodeType.e_mult;
                break;
            case "/":
                type = ASTNodeType.e_div;
                break;
            case "%":
                type = ASTNodeType.e_mod;
                break;
            case ">>":
                type = ASTNodeType.e_shr;
                break;
            case "<<":
                type = ASTNodeType.e_shl;
                break;
            case "&":
                type = ASTNodeType.e_band;
                break;
            case "|":
                type = ASTNodeType.e_bor;
                break;
            case "~":
                type = ASTNodeType.e_bneg;
                break;
            case "^":
                type = ASTNodeType.e_bxor;
                break;
            case "&&":
                type = ASTNodeType.e_land;
                break;
            case "||":
                type = ASTNodeType.e_lor;
                break;
            case "!":
                type = ASTNodeType.e_not;
                break;
            case "=":
                type = ASTNodeType.e_asgn;
                break;
            case "==":
                type = ASTNodeType.e_eq;
                break;
            case "!=":
                type = ASTNodeType.e_ne;
                break;
            case "<=":
                type = ASTNodeType.e_le;
                break;
            case ">=":
                type = ASTNodeType.e_ge;
                break;
            case ">":
                type = ASTNodeType.e_gt;
                break;
            case "<":
                type = ASTNodeType.e_lt;
                break;
        }
        return new ASTExprNode(new Position(ctx), type, v);
    }

    @Override public ASTExprNode visitCreator(mxParser.CreatorContext ctx) {
        ASTTypeNode type = visitNotArrayTypeName(ctx.notArrayTypeName());
        Vector<ASTExprNode> v = new Vector<>();
        if (ctx.arrayInit() != null){
            type.dimension = ctx.arrayInit().LB().size();
            for (mxParser.ExpressionContext i : ctx.arrayInit().expression()){
                v.add(visitExpression(i));
            }
        }
        else {
            type.dimension = 0;
        }
        return new ASTCreatorNode(new Position(ctx), ASTNodeType.e_creator, v, type);
    }

    @Override public ASTTypeNode visitTypeName(mxParser.TypeNameContext ctx) {
        ASTTypeNode t = visitNotArrayTypeName(ctx.notArrayTypeName());
        t.dimension = ctx.LB().size();
        return t;
    }
    @Override public ASTTypeNode visitNotArrayTypeName(mxParser.NotArrayTypeNameContext ctx){
        if (ctx.classTypeName() != null) return visitClassTypeName(ctx.classTypeName());
        else if (ctx.primitiveTypeName() != null) return visitPrimitiveTypeName(ctx.primitiveTypeName());
        else return null;
    }
    @Override public ASTTypeNode visitPrimitiveTypeName(mxParser.PrimitiveTypeNameContext ctx){
        ASTNodeType type = null;
        switch (ctx.getText()){
            case "bool":
                type = ASTNodeType.t_bool;
                break;
            case "int":
                type = ASTNodeType.t_int;
                break;
            case "string":
                type  = ASTNodeType.t_str;
        }
        return new ASTTypeNode(new Position(ctx), type, null, 0);
    }
    @Override public ASTTypeNode visitClassTypeName(mxParser.ClassTypeNameContext ctx) {
        return new ASTTypeNode(new Position(ctx), ASTNodeType.t_class, ctx.getText(), 0);
     }
    @Override public ASTPrimNode visitConstant(mxParser.ConstantContext ctx) {
        if (ctx.BoolConstant() != null)
            return new ASTPrimNode(new Position(ctx), ASTNodeType.p_bool, (ctx.BoolConstant().getText().equals("true"))?1:0, "");
        if (ctx.IntegerConstant() != null)
            return new ASTPrimNode(new Position(ctx), ASTNodeType.p_int, Integer.parseInt(ctx.IntegerConstant().getText()), "");
        if (ctx.StringConstant() != null){
            String str = ctx.StringConstant().getText();
            StringBuilder builder = new StringBuilder();
            for (int i = 1; i < str.length() -1; i++){
                char c = str.charAt(i);
                if (c == '\\') {
                    char c1 = str.charAt(i+1);
                    switch (c1){
                        case 't':
                            builder.append('\t');
                            break;
                        case 'n':
                            builder.append('\n');
                            break;
                            default:
                                builder.append(c1);
                    }
                    i++;
                }
                else builder.append(c);
            }
           /* System.out.println("test: escape sequence");
            System.out.println(str);
            System.out.println(builder.toString());*/
            return new ASTPrimNode(new Position(ctx), ASTNodeType.p_str, 0,builder.toString());
        }
        if (ctx.PointConstant() != null){
            if (ctx.PointConstant().getText().equals("this"))
                return new ASTPrimNode(new Position(ctx), ASTNodeType.p_this, 0,"");
            else return new ASTPrimNode(new Position(ctx), ASTNodeType.p_null, 0, "");
        }
        return null;
    }
}
