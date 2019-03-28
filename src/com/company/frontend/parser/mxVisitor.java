// Generated from D:/2019Spring/compiler/src/com/company/frontend/parser\mx.g4 by ANTLR 4.7.2
package com.company.frontend.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link mxParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface mxVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link mxParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(mxParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link mxParser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(mxParser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link mxParser#progStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgStatement(mxParser.ProgStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link mxParser#classStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassStatement(mxParser.ClassStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link mxParser#constructFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructFunction(mxParser.ConstructFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link mxParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(mxParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link mxParser#argumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentList(mxParser.ArgumentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link mxParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(mxParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link mxParser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatement(mxParser.BlockStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link mxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(mxParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link mxParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(mxParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link mxParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(mxParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link mxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(mxParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link mxParser#creator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreator(mxParser.CreatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link mxParser#classInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassInit(mxParser.ClassInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link mxParser#arrayInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayInit(mxParser.ArrayInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link mxParser#declarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarator(mxParser.DeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link mxParser#typeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeName(mxParser.TypeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link mxParser#notArrayTypeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotArrayTypeName(mxParser.NotArrayTypeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link mxParser#classTypeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassTypeName(mxParser.ClassTypeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link mxParser#primitiveTypeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveTypeName(mxParser.PrimitiveTypeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link mxParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstant(mxParser.ConstantContext ctx);
}