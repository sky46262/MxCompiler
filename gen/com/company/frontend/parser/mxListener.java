// Generated from D:/2019Spring/compiler/src/com/company/frontend/parser\mx.g4 by ANTLR 4.7.2
package com.company.frontend.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link mxParser}.
 */
public interface mxListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link mxParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(mxParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link mxParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(mxParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link mxParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(mxParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link mxParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(mxParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link mxParser#progStatement}.
	 * @param ctx the parse tree
	 */
	void enterProgStatement(mxParser.ProgStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link mxParser#progStatement}.
	 * @param ctx the parse tree
	 */
	void exitProgStatement(mxParser.ProgStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link mxParser#classStatement}.
	 * @param ctx the parse tree
	 */
	void enterClassStatement(mxParser.ClassStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link mxParser#classStatement}.
	 * @param ctx the parse tree
	 */
	void exitClassStatement(mxParser.ClassStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link mxParser#constructFunction}.
	 * @param ctx the parse tree
	 */
	void enterConstructFunction(mxParser.ConstructFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link mxParser#constructFunction}.
	 * @param ctx the parse tree
	 */
	void exitConstructFunction(mxParser.ConstructFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link mxParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(mxParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link mxParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(mxParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link mxParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(mxParser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link mxParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(mxParser.ArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link mxParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(mxParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link mxParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(mxParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link mxParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatement(mxParser.BlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link mxParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatement(mxParser.BlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link mxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(mxParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link mxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(mxParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link mxParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(mxParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link mxParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(mxParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link mxParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(mxParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link mxParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(mxParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link mxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(mxParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link mxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(mxParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link mxParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterCreator(mxParser.CreatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link mxParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitCreator(mxParser.CreatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link mxParser#declarator}.
	 * @param ctx the parse tree
	 */
	void enterDeclarator(mxParser.DeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link mxParser#declarator}.
	 * @param ctx the parse tree
	 */
	void exitDeclarator(mxParser.DeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link mxParser#typeName}.
	 * @param ctx the parse tree
	 */
	void enterTypeName(mxParser.TypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link mxParser#typeName}.
	 * @param ctx the parse tree
	 */
	void exitTypeName(mxParser.TypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link mxParser#notArrayTypeName}.
	 * @param ctx the parse tree
	 */
	void enterNotArrayTypeName(mxParser.NotArrayTypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link mxParser#notArrayTypeName}.
	 * @param ctx the parse tree
	 */
	void exitNotArrayTypeName(mxParser.NotArrayTypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link mxParser#classTypeName}.
	 * @param ctx the parse tree
	 */
	void enterClassTypeName(mxParser.ClassTypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link mxParser#classTypeName}.
	 * @param ctx the parse tree
	 */
	void exitClassTypeName(mxParser.ClassTypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link mxParser#primitiveTypeName}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveTypeName(mxParser.PrimitiveTypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link mxParser#primitiveTypeName}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveTypeName(mxParser.PrimitiveTypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link mxParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterConstant(mxParser.ConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link mxParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitConstant(mxParser.ConstantContext ctx);
}