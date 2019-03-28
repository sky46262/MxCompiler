// Generated from D:/2019Spring/compiler/src/com/company/frontend/parser\mx.g4 by ANTLR 4.7.2
package com.company.frontend.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class mxParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, BoolConstant=40, PointConstant=41, IntegerConstant=42, StringConstant=43, 
		Identifier=44, LB=45, RB=46, LP=47, RP=48, LBB=49, RBB=50, SL_COMMENT=51, 
		COMMENT=52, NEWLINE=53, WS=54;
	public static final int
		RULE_program = 0, RULE_classDeclaration = 1, RULE_progStatement = 2, RULE_classStatement = 3, 
		RULE_constructFunction = 4, RULE_functionDeclaration = 5, RULE_argumentList = 6, 
		RULE_block = 7, RULE_blockStatement = 8, RULE_statement = 9, RULE_declaration = 10, 
		RULE_expressionList = 11, RULE_expression = 12, RULE_creator = 13, RULE_classInit = 14, 
		RULE_arrayInit = 15, RULE_declarator = 16, RULE_typeName = 17, RULE_notArrayTypeName = 18, 
		RULE_classTypeName = 19, RULE_primitiveTypeName = 20, RULE_constant = 21;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "classDeclaration", "progStatement", "classStatement", "constructFunction", 
			"functionDeclaration", "argumentList", "block", "blockStatement", "statement", 
			"declaration", "expressionList", "expression", "creator", "classInit", 
			"arrayInit", "declarator", "typeName", "notArrayTypeName", "classTypeName", 
			"primitiveTypeName", "constant"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'class'", "'void'", "','", "'if'", "'else'", "'while'", "'for'", 
			"';'", "'return'", "'break'", "'continue'", "'.'", "'++'", "'--'", "'new'", 
			"'~'", "'!'", "'+'", "'-'", "'*'", "'/'", "'%'", "'<<'", "'>>'", "'<='", 
			"'>='", "'<'", "'>'", "'=='", "'!='", "'&'", "'^'", "'|'", "'&&'", "'||'", 
			"'='", "'bool'", "'int'", "'string'", null, null, null, null, null, "'['", 
			"']'", "'('", "')'", "'{'", "'}'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, "BoolConstant", "PointConstant", "IntegerConstant", 
			"StringConstant", "Identifier", "LB", "RB", "LP", "RP", "LBB", "RBB", 
			"SL_COMMENT", "COMMENT", "NEWLINE", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "mx.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public mxParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(mxParser.EOF, 0); }
		public List<ProgStatementContext> progStatement() {
			return getRuleContexts(ProgStatementContext.class);
		}
		public ProgStatementContext progStatement(int i) {
			return getRuleContext(ProgStatementContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof mxVisitor ) return ((mxVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << Identifier))) != 0)) {
				{
				{
				setState(44);
				progStatement();
				}
				}
				setState(49);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(50);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDeclarationContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(mxParser.Identifier, 0); }
		public TerminalNode LBB() { return getToken(mxParser.LBB, 0); }
		public TerminalNode RBB() { return getToken(mxParser.RBB, 0); }
		public List<ClassStatementContext> classStatement() {
			return getRuleContexts(ClassStatementContext.class);
		}
		public ClassStatementContext classStatement(int i) {
			return getRuleContext(ClassStatementContext.class,i);
		}
		public ClassDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).enterClassDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).exitClassDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof mxVisitor ) return ((mxVisitor<? extends T>)visitor).visitClassDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDeclarationContext classDeclaration() throws RecognitionException {
		ClassDeclarationContext _localctx = new ClassDeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_classDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			match(T__0);
			setState(53);
			match(Identifier);
			setState(54);
			match(LBB);
			setState(58);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << Identifier))) != 0)) {
				{
				{
				setState(55);
				classStatement();
				}
				}
				setState(60);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(61);
			match(RBB);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProgStatementContext extends ParserRuleContext {
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public FunctionDeclarationContext functionDeclaration() {
			return getRuleContext(FunctionDeclarationContext.class,0);
		}
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public ProgStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_progStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).enterProgStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).exitProgStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof mxVisitor ) return ((mxVisitor<? extends T>)visitor).visitProgStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgStatementContext progStatement() throws RecognitionException {
		ProgStatementContext _localctx = new ProgStatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_progStatement);
		try {
			setState(66);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(63);
				classDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(64);
				functionDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(65);
				declaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassStatementContext extends ParserRuleContext {
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public FunctionDeclarationContext functionDeclaration() {
			return getRuleContext(FunctionDeclarationContext.class,0);
		}
		public ConstructFunctionContext constructFunction() {
			return getRuleContext(ConstructFunctionContext.class,0);
		}
		public ClassStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).enterClassStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).exitClassStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof mxVisitor ) return ((mxVisitor<? extends T>)visitor).visitClassStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassStatementContext classStatement() throws RecognitionException {
		ClassStatementContext _localctx = new ClassStatementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_classStatement);
		try {
			setState(71);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(68);
				declaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(69);
				functionDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(70);
				constructFunction();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstructFunctionContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(mxParser.Identifier, 0); }
		public TerminalNode LP() { return getToken(mxParser.LP, 0); }
		public TerminalNode RP() { return getToken(mxParser.RP, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ConstructFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructFunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).enterConstructFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).exitConstructFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof mxVisitor ) return ((mxVisitor<? extends T>)visitor).visitConstructFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructFunctionContext constructFunction() throws RecognitionException {
		ConstructFunctionContext _localctx = new ConstructFunctionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_constructFunction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			match(Identifier);
			setState(74);
			match(LP);
			setState(75);
			match(RP);
			setState(76);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDeclarationContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(mxParser.Identifier, 0); }
		public TerminalNode LP() { return getToken(mxParser.LP, 0); }
		public TerminalNode RP() { return getToken(mxParser.RP, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public FunctionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).enterFunctionDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).exitFunctionDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof mxVisitor ) return ((mxVisitor<? extends T>)visitor).visitFunctionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDeclarationContext functionDeclaration() throws RecognitionException {
		FunctionDeclarationContext _localctx = new FunctionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_functionDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__36:
			case T__37:
			case T__38:
			case Identifier:
				{
				setState(78);
				typeName();
				}
				break;
			case T__1:
				{
				setState(79);
				match(T__1);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(82);
			match(Identifier);
			setState(83);
			match(LP);
			setState(85);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << Identifier))) != 0)) {
				{
				setState(84);
				argumentList();
				}
			}

			setState(87);
			match(RP);
			setState(88);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumentListContext extends ParserRuleContext {
		public List<DeclaratorContext> declarator() {
			return getRuleContexts(DeclaratorContext.class);
		}
		public DeclaratorContext declarator(int i) {
			return getRuleContext(DeclaratorContext.class,i);
		}
		public ArgumentListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).enterArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).exitArgumentList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof mxVisitor ) return ((mxVisitor<? extends T>)visitor).visitArgumentList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentListContext argumentList() throws RecognitionException {
		ArgumentListContext _localctx = new ArgumentListContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_argumentList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			declarator();
			setState(95);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(91);
				match(T__2);
				setState(92);
				declarator();
				}
				}
				setState(97);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public TerminalNode LBB() { return getToken(mxParser.LBB, 0); }
		public TerminalNode RBB() { return getToken(mxParser.RBB, 0); }
		public List<BlockStatementContext> blockStatement() {
			return getRuleContexts(BlockStatementContext.class);
		}
		public BlockStatementContext blockStatement(int i) {
			return getRuleContext(BlockStatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof mxVisitor ) return ((mxVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(LBB);
			setState(102);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << BoolConstant) | (1L << PointConstant) | (1L << IntegerConstant) | (1L << StringConstant) | (1L << Identifier) | (1L << LP) | (1L << LBB))) != 0)) {
				{
				{
				setState(99);
				blockStatement();
				}
				}
				setState(104);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(105);
			match(RBB);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockStatementContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public BlockStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).enterBlockStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).exitBlockStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof mxVisitor ) return ((mxVisitor<? extends T>)visitor).visitBlockStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockStatementContext blockStatement() throws RecognitionException {
		BlockStatementContext _localctx = new BlockStatementContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_blockStatement);
		try {
			setState(110);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(107);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(108);
				statement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(109);
				declaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public Token type;
		public ExpressionContext init;
		public ExpressionContext cond;
		public ExpressionContext step;
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode LP() { return getToken(mxParser.LP, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode RP() { return getToken(mxParser.RP, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof mxVisitor ) return ((mxVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_statement);
		int _la;
		try {
			setState(156);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBB:
				enterOuterAlt(_localctx, 1);
				{
				setState(112);
				block();
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 2);
				{
				setState(113);
				((StatementContext)_localctx).type = match(T__3);
				setState(114);
				match(LP);
				setState(115);
				expression(0);
				setState(116);
				match(RP);
				setState(117);
				statement();
				setState(120);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
				case 1:
					{
					setState(118);
					match(T__4);
					setState(119);
					statement();
					}
					break;
				}
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 3);
				{
				setState(122);
				((StatementContext)_localctx).type = match(T__5);
				setState(123);
				match(LP);
				setState(124);
				expression(0);
				setState(125);
				match(RP);
				setState(126);
				statement();
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 4);
				{
				setState(128);
				((StatementContext)_localctx).type = match(T__6);
				setState(129);
				match(LP);
				setState(131);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << BoolConstant) | (1L << PointConstant) | (1L << IntegerConstant) | (1L << StringConstant) | (1L << Identifier) | (1L << LP))) != 0)) {
					{
					setState(130);
					((StatementContext)_localctx).init = expression(0);
					}
				}

				setState(133);
				match(T__7);
				setState(135);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << BoolConstant) | (1L << PointConstant) | (1L << IntegerConstant) | (1L << StringConstant) | (1L << Identifier) | (1L << LP))) != 0)) {
					{
					setState(134);
					((StatementContext)_localctx).cond = expression(0);
					}
				}

				setState(137);
				match(T__7);
				setState(139);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << BoolConstant) | (1L << PointConstant) | (1L << IntegerConstant) | (1L << StringConstant) | (1L << Identifier) | (1L << LP))) != 0)) {
					{
					setState(138);
					((StatementContext)_localctx).step = expression(0);
					}
				}

				setState(141);
				match(RP);
				setState(142);
				statement();
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 5);
				{
				setState(143);
				((StatementContext)_localctx).type = match(T__8);
				setState(145);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << BoolConstant) | (1L << PointConstant) | (1L << IntegerConstant) | (1L << StringConstant) | (1L << Identifier) | (1L << LP))) != 0)) {
					{
					setState(144);
					expression(0);
					}
				}

				setState(147);
				match(T__7);
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 6);
				{
				setState(148);
				((StatementContext)_localctx).type = match(T__9);
				setState(149);
				match(T__7);
				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 7);
				{
				setState(150);
				((StatementContext)_localctx).type = match(T__10);
				setState(151);
				match(T__7);
				}
				break;
			case T__12:
			case T__13:
			case T__14:
			case T__15:
			case T__16:
			case T__17:
			case T__18:
			case BoolConstant:
			case PointConstant:
			case IntegerConstant:
			case StringConstant:
			case Identifier:
			case LP:
				enterOuterAlt(_localctx, 8);
				{
				setState(152);
				expression(0);
				setState(153);
				match(T__7);
				}
				break;
			case T__7:
				enterOuterAlt(_localctx, 9);
				{
				setState(155);
				match(T__7);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public DeclaratorContext declarator() {
			return getRuleContext(DeclaratorContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).exitDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof mxVisitor ) return ((mxVisitor<? extends T>)visitor).visitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			declarator();
			setState(159);
			match(T__7);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).enterExpressionList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).exitExpressionList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof mxVisitor ) return ((mxVisitor<? extends T>)visitor).visitExpressionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(161);
			expression(0);
			setState(166);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(162);
				match(T__2);
				setState(163);
				expression(0);
				}
				}
				setState(168);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public Token op;
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(mxParser.Identifier, 0); }
		public TerminalNode LP() { return getToken(mxParser.LP, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode RP() { return getToken(mxParser.RP, 0); }
		public CreatorContext creator() {
			return getRuleContext(CreatorContext.class,0);
		}
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public TerminalNode RB() { return getToken(mxParser.RB, 0); }
		public TerminalNode LB() { return getToken(mxParser.LB, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof mxVisitor ) return ((mxVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 24;
		enterRecursionRule(_localctx, 24, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(184);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BoolConstant:
			case PointConstant:
			case IntegerConstant:
			case StringConstant:
				{
				setState(170);
				constant();
				}
				break;
			case Identifier:
				{
				setState(171);
				match(Identifier);
				}
				break;
			case LP:
				{
				setState(172);
				match(LP);
				setState(173);
				expression(0);
				setState(174);
				match(RP);
				}
				break;
			case T__14:
				{
				setState(176);
				((ExpressionContext)_localctx).op = match(T__14);
				setState(177);
				creator();
				}
				break;
			case T__15:
			case T__16:
				{
				setState(178);
				((ExpressionContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__15 || _la==T__16) ) {
					((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(179);
				expression(14);
				}
				break;
			case T__12:
			case T__13:
				{
				setState(180);
				((ExpressionContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__12 || _la==T__13) ) {
					((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(181);
				expression(13);
				}
				break;
			case T__17:
			case T__18:
				{
				setState(182);
				((ExpressionContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__17 || _la==T__18) ) {
					((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(183);
				expression(12);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(237);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(235);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(186);
						if (!(precpred(_ctx, 19))) throw new FailedPredicateException(this, "precpred(_ctx, 19)");
						setState(187);
						((ExpressionContext)_localctx).op = match(T__11);
						setState(188);
						expression(20);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(189);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(190);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__19) | (1L << T__20) | (1L << T__21))) != 0)) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(191);
						expression(12);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(192);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(193);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__17 || _la==T__18) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(194);
						expression(11);
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(195);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(196);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__22 || _la==T__23) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(197);
						expression(10);
						}
						break;
					case 5:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(198);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(199);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27))) != 0)) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(200);
						expression(9);
						}
						break;
					case 6:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(201);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(202);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__28 || _la==T__29) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(203);
						expression(8);
						}
						break;
					case 7:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(204);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(205);
						((ExpressionContext)_localctx).op = match(T__30);
						setState(206);
						expression(7);
						}
						break;
					case 8:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(207);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(208);
						((ExpressionContext)_localctx).op = match(T__31);
						setState(209);
						expression(6);
						}
						break;
					case 9:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(210);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(211);
						((ExpressionContext)_localctx).op = match(T__32);
						setState(212);
						expression(5);
						}
						break;
					case 10:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(213);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(214);
						((ExpressionContext)_localctx).op = match(T__33);
						setState(215);
						expression(4);
						}
						break;
					case 11:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(216);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(217);
						((ExpressionContext)_localctx).op = match(T__34);
						setState(218);
						expression(3);
						}
						break;
					case 12:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(219);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(220);
						((ExpressionContext)_localctx).op = match(T__35);
						setState(221);
						expression(1);
						}
						break;
					case 13:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(222);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(223);
						((ExpressionContext)_localctx).op = match(LP);
						setState(225);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << BoolConstant) | (1L << PointConstant) | (1L << IntegerConstant) | (1L << StringConstant) | (1L << Identifier) | (1L << LP))) != 0)) {
							{
							setState(224);
							expressionList();
							}
						}

						setState(227);
						match(RP);
						}
						break;
					case 14:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(228);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(229);
						((ExpressionContext)_localctx).op = match(LB);
						setState(230);
						expression(0);
						setState(231);
						match(RB);
						}
						break;
					case 15:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(233);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(234);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__12 || _la==T__13) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					}
					} 
				}
				setState(239);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class CreatorContext extends ParserRuleContext {
		public NotArrayTypeNameContext notArrayTypeName() {
			return getRuleContext(NotArrayTypeNameContext.class,0);
		}
		public ClassInitContext classInit() {
			return getRuleContext(ClassInitContext.class,0);
		}
		public ArrayInitContext arrayInit() {
			return getRuleContext(ArrayInitContext.class,0);
		}
		public CreatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_creator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).enterCreator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).exitCreator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof mxVisitor ) return ((mxVisitor<? extends T>)visitor).visitCreator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreatorContext creator() throws RecognitionException {
		CreatorContext _localctx = new CreatorContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_creator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(240);
			notArrayTypeName();
			setState(243);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				{
				setState(241);
				classInit();
				}
				break;
			case 2:
				{
				setState(242);
				arrayInit();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassInitContext extends ParserRuleContext {
		public TerminalNode LP() { return getToken(mxParser.LP, 0); }
		public TerminalNode RP() { return getToken(mxParser.RP, 0); }
		public ClassInitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classInit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).enterClassInit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).exitClassInit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof mxVisitor ) return ((mxVisitor<? extends T>)visitor).visitClassInit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassInitContext classInit() throws RecognitionException {
		ClassInitContext _localctx = new ClassInitContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_classInit);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(245);
			match(LP);
			setState(246);
			match(RP);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayInitContext extends ParserRuleContext {
		public List<TerminalNode> LB() { return getTokens(mxParser.LB); }
		public TerminalNode LB(int i) {
			return getToken(mxParser.LB, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> RB() { return getTokens(mxParser.RB); }
		public TerminalNode RB(int i) {
			return getToken(mxParser.RB, i);
		}
		public ArrayInitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayInit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).enterArrayInit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).exitArrayInit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof mxVisitor ) return ((mxVisitor<? extends T>)visitor).visitArrayInit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayInitContext arrayInit() throws RecognitionException {
		ArrayInitContext _localctx = new ArrayInitContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_arrayInit);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(254);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(248);
					match(LB);
					setState(249);
					expression(0);
					setState(250);
					match(RB);
					}
					} 
				}
				setState(256);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			}
			setState(261);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(257);
					match(LB);
					setState(258);
					match(RB);
					}
					} 
				}
				setState(263);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclaratorContext extends ParserRuleContext {
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(mxParser.Identifier, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public DeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).enterDeclarator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).exitDeclarator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof mxVisitor ) return ((mxVisitor<? extends T>)visitor).visitDeclarator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclaratorContext declarator() throws RecognitionException {
		DeclaratorContext _localctx = new DeclaratorContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_declarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(264);
			typeName();
			setState(265);
			match(Identifier);
			setState(268);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__35) {
				{
				setState(266);
				match(T__35);
				setState(267);
				expression(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeNameContext extends ParserRuleContext {
		public NotArrayTypeNameContext notArrayTypeName() {
			return getRuleContext(NotArrayTypeNameContext.class,0);
		}
		public List<TerminalNode> LB() { return getTokens(mxParser.LB); }
		public TerminalNode LB(int i) {
			return getToken(mxParser.LB, i);
		}
		public List<TerminalNode> RB() { return getTokens(mxParser.RB); }
		public TerminalNode RB(int i) {
			return getToken(mxParser.RB, i);
		}
		public TypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).enterTypeName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).exitTypeName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof mxVisitor ) return ((mxVisitor<? extends T>)visitor).visitTypeName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeNameContext typeName() throws RecognitionException {
		TypeNameContext _localctx = new TypeNameContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_typeName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(270);
			notArrayTypeName();
			setState(275);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LB) {
				{
				{
				setState(271);
				match(LB);
				setState(272);
				match(RB);
				}
				}
				setState(277);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NotArrayTypeNameContext extends ParserRuleContext {
		public PrimitiveTypeNameContext primitiveTypeName() {
			return getRuleContext(PrimitiveTypeNameContext.class,0);
		}
		public ClassTypeNameContext classTypeName() {
			return getRuleContext(ClassTypeNameContext.class,0);
		}
		public NotArrayTypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_notArrayTypeName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).enterNotArrayTypeName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).exitNotArrayTypeName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof mxVisitor ) return ((mxVisitor<? extends T>)visitor).visitNotArrayTypeName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NotArrayTypeNameContext notArrayTypeName() throws RecognitionException {
		NotArrayTypeNameContext _localctx = new NotArrayTypeNameContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_notArrayTypeName);
		try {
			setState(280);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__36:
			case T__37:
			case T__38:
				enterOuterAlt(_localctx, 1);
				{
				setState(278);
				primitiveTypeName();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(279);
				classTypeName();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassTypeNameContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(mxParser.Identifier, 0); }
		public ClassTypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classTypeName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).enterClassTypeName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).exitClassTypeName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof mxVisitor ) return ((mxVisitor<? extends T>)visitor).visitClassTypeName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassTypeNameContext classTypeName() throws RecognitionException {
		ClassTypeNameContext _localctx = new ClassTypeNameContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_classTypeName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(282);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimitiveTypeNameContext extends ParserRuleContext {
		public PrimitiveTypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitiveTypeName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).enterPrimitiveTypeName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).exitPrimitiveTypeName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof mxVisitor ) return ((mxVisitor<? extends T>)visitor).visitPrimitiveTypeName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimitiveTypeNameContext primitiveTypeName() throws RecognitionException {
		PrimitiveTypeNameContext _localctx = new PrimitiveTypeNameContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_primitiveTypeName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(284);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__36) | (1L << T__37) | (1L << T__38))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstantContext extends ParserRuleContext {
		public TerminalNode BoolConstant() { return getToken(mxParser.BoolConstant, 0); }
		public TerminalNode IntegerConstant() { return getToken(mxParser.IntegerConstant, 0); }
		public TerminalNode StringConstant() { return getToken(mxParser.StringConstant, 0); }
		public TerminalNode PointConstant() { return getToken(mxParser.PointConstant, 0); }
		public ConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constant; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).enterConstant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof mxListener ) ((mxListener)listener).exitConstant(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof mxVisitor ) return ((mxVisitor<? extends T>)visitor).visitConstant(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstantContext constant() throws RecognitionException {
		ConstantContext _localctx = new ConstantContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_constant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(286);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BoolConstant) | (1L << PointConstant) | (1L << IntegerConstant) | (1L << StringConstant))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 12:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 19);
		case 1:
			return precpred(_ctx, 11);
		case 2:
			return precpred(_ctx, 10);
		case 3:
			return precpred(_ctx, 9);
		case 4:
			return precpred(_ctx, 8);
		case 5:
			return precpred(_ctx, 7);
		case 6:
			return precpred(_ctx, 6);
		case 7:
			return precpred(_ctx, 5);
		case 8:
			return precpred(_ctx, 4);
		case 9:
			return precpred(_ctx, 3);
		case 10:
			return precpred(_ctx, 2);
		case 11:
			return precpred(_ctx, 1);
		case 12:
			return precpred(_ctx, 18);
		case 13:
			return precpred(_ctx, 17);
		case 14:
			return precpred(_ctx, 16);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\38\u0123\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\3\2\7\2\60\n\2\f\2"+
		"\16\2\63\13\2\3\2\3\2\3\3\3\3\3\3\3\3\7\3;\n\3\f\3\16\3>\13\3\3\3\3\3"+
		"\3\4\3\4\3\4\5\4E\n\4\3\5\3\5\3\5\5\5J\n\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7"+
		"\5\7S\n\7\3\7\3\7\3\7\5\7X\n\7\3\7\3\7\3\7\3\b\3\b\3\b\7\b`\n\b\f\b\16"+
		"\bc\13\b\3\t\3\t\7\tg\n\t\f\t\16\tj\13\t\3\t\3\t\3\n\3\n\3\n\5\nq\n\n"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13{\n\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\5\13\u0086\n\13\3\13\3\13\5\13\u008a\n\13\3"+
		"\13\3\13\5\13\u008e\n\13\3\13\3\13\3\13\3\13\5\13\u0094\n\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13\u009f\n\13\3\f\3\f\3\f\3\r\3"+
		"\r\3\r\7\r\u00a7\n\r\f\r\16\r\u00aa\13\r\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u00bb\n\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u00e4\n\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\7\16\u00ee\n\16\f\16\16\16\u00f1\13\16"+
		"\3\17\3\17\3\17\5\17\u00f6\n\17\3\20\3\20\3\20\3\21\3\21\3\21\3\21\7\21"+
		"\u00ff\n\21\f\21\16\21\u0102\13\21\3\21\3\21\7\21\u0106\n\21\f\21\16\21"+
		"\u0109\13\21\3\22\3\22\3\22\3\22\5\22\u010f\n\22\3\23\3\23\3\23\7\23\u0114"+
		"\n\23\f\23\16\23\u0117\13\23\3\24\3\24\5\24\u011b\n\24\3\25\3\25\3\26"+
		"\3\26\3\27\3\27\3\27\2\3\32\30\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36"+
		" \"$&(*,\2\13\3\2\22\23\3\2\17\20\3\2\24\25\3\2\26\30\3\2\31\32\3\2\33"+
		"\36\3\2\37 \3\2\')\3\2*-\2\u0142\2\61\3\2\2\2\4\66\3\2\2\2\6D\3\2\2\2"+
		"\bI\3\2\2\2\nK\3\2\2\2\fR\3\2\2\2\16\\\3\2\2\2\20d\3\2\2\2\22p\3\2\2\2"+
		"\24\u009e\3\2\2\2\26\u00a0\3\2\2\2\30\u00a3\3\2\2\2\32\u00ba\3\2\2\2\34"+
		"\u00f2\3\2\2\2\36\u00f7\3\2\2\2 \u0100\3\2\2\2\"\u010a\3\2\2\2$\u0110"+
		"\3\2\2\2&\u011a\3\2\2\2(\u011c\3\2\2\2*\u011e\3\2\2\2,\u0120\3\2\2\2."+
		"\60\5\6\4\2/.\3\2\2\2\60\63\3\2\2\2\61/\3\2\2\2\61\62\3\2\2\2\62\64\3"+
		"\2\2\2\63\61\3\2\2\2\64\65\7\2\2\3\65\3\3\2\2\2\66\67\7\3\2\2\678\7.\2"+
		"\28<\7\63\2\29;\5\b\5\2:9\3\2\2\2;>\3\2\2\2<:\3\2\2\2<=\3\2\2\2=?\3\2"+
		"\2\2><\3\2\2\2?@\7\64\2\2@\5\3\2\2\2AE\5\4\3\2BE\5\f\7\2CE\5\26\f\2DA"+
		"\3\2\2\2DB\3\2\2\2DC\3\2\2\2E\7\3\2\2\2FJ\5\26\f\2GJ\5\f\7\2HJ\5\n\6\2"+
		"IF\3\2\2\2IG\3\2\2\2IH\3\2\2\2J\t\3\2\2\2KL\7.\2\2LM\7\61\2\2MN\7\62\2"+
		"\2NO\5\20\t\2O\13\3\2\2\2PS\5$\23\2QS\7\4\2\2RP\3\2\2\2RQ\3\2\2\2ST\3"+
		"\2\2\2TU\7.\2\2UW\7\61\2\2VX\5\16\b\2WV\3\2\2\2WX\3\2\2\2XY\3\2\2\2YZ"+
		"\7\62\2\2Z[\5\20\t\2[\r\3\2\2\2\\a\5\"\22\2]^\7\5\2\2^`\5\"\22\2_]\3\2"+
		"\2\2`c\3\2\2\2a_\3\2\2\2ab\3\2\2\2b\17\3\2\2\2ca\3\2\2\2dh\7\63\2\2eg"+
		"\5\22\n\2fe\3\2\2\2gj\3\2\2\2hf\3\2\2\2hi\3\2\2\2ik\3\2\2\2jh\3\2\2\2"+
		"kl\7\64\2\2l\21\3\2\2\2mq\5\20\t\2nq\5\24\13\2oq\5\26\f\2pm\3\2\2\2pn"+
		"\3\2\2\2po\3\2\2\2q\23\3\2\2\2r\u009f\5\20\t\2st\7\6\2\2tu\7\61\2\2uv"+
		"\5\32\16\2vw\7\62\2\2wz\5\24\13\2xy\7\7\2\2y{\5\24\13\2zx\3\2\2\2z{\3"+
		"\2\2\2{\u009f\3\2\2\2|}\7\b\2\2}~\7\61\2\2~\177\5\32\16\2\177\u0080\7"+
		"\62\2\2\u0080\u0081\5\24\13\2\u0081\u009f\3\2\2\2\u0082\u0083\7\t\2\2"+
		"\u0083\u0085\7\61\2\2\u0084\u0086\5\32\16\2\u0085\u0084\3\2\2\2\u0085"+
		"\u0086\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0089\7\n\2\2\u0088\u008a\5\32"+
		"\16\2\u0089\u0088\3\2\2\2\u0089\u008a\3\2\2\2\u008a\u008b\3\2\2\2\u008b"+
		"\u008d\7\n\2\2\u008c\u008e\5\32\16\2\u008d\u008c\3\2\2\2\u008d\u008e\3"+
		"\2\2\2\u008e\u008f\3\2\2\2\u008f\u0090\7\62\2\2\u0090\u009f\5\24\13\2"+
		"\u0091\u0093\7\13\2\2\u0092\u0094\5\32\16\2\u0093\u0092\3\2\2\2\u0093"+
		"\u0094\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u009f\7\n\2\2\u0096\u0097\7\f"+
		"\2\2\u0097\u009f\7\n\2\2\u0098\u0099\7\r\2\2\u0099\u009f\7\n\2\2\u009a"+
		"\u009b\5\32\16\2\u009b\u009c\7\n\2\2\u009c\u009f\3\2\2\2\u009d\u009f\7"+
		"\n\2\2\u009er\3\2\2\2\u009es\3\2\2\2\u009e|\3\2\2\2\u009e\u0082\3\2\2"+
		"\2\u009e\u0091\3\2\2\2\u009e\u0096\3\2\2\2\u009e\u0098\3\2\2\2\u009e\u009a"+
		"\3\2\2\2\u009e\u009d\3\2\2\2\u009f\25\3\2\2\2\u00a0\u00a1\5\"\22\2\u00a1"+
		"\u00a2\7\n\2\2\u00a2\27\3\2\2\2\u00a3\u00a8\5\32\16\2\u00a4\u00a5\7\5"+
		"\2\2\u00a5\u00a7\5\32\16\2\u00a6\u00a4\3\2\2\2\u00a7\u00aa\3\2\2\2\u00a8"+
		"\u00a6\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\31\3\2\2\2\u00aa\u00a8\3\2\2"+
		"\2\u00ab\u00ac\b\16\1\2\u00ac\u00bb\5,\27\2\u00ad\u00bb\7.\2\2\u00ae\u00af"+
		"\7\61\2\2\u00af\u00b0\5\32\16\2\u00b0\u00b1\7\62\2\2\u00b1\u00bb\3\2\2"+
		"\2\u00b2\u00b3\7\21\2\2\u00b3\u00bb\5\34\17\2\u00b4\u00b5\t\2\2\2\u00b5"+
		"\u00bb\5\32\16\20\u00b6\u00b7\t\3\2\2\u00b7\u00bb\5\32\16\17\u00b8\u00b9"+
		"\t\4\2\2\u00b9\u00bb\5\32\16\16\u00ba\u00ab\3\2\2\2\u00ba\u00ad\3\2\2"+
		"\2\u00ba\u00ae\3\2\2\2\u00ba\u00b2\3\2\2\2\u00ba\u00b4\3\2\2\2\u00ba\u00b6"+
		"\3\2\2\2\u00ba\u00b8\3\2\2\2\u00bb\u00ef\3\2\2\2\u00bc\u00bd\f\25\2\2"+
		"\u00bd\u00be\7\16\2\2\u00be\u00ee\5\32\16\26\u00bf\u00c0\f\r\2\2\u00c0"+
		"\u00c1\t\5\2\2\u00c1\u00ee\5\32\16\16\u00c2\u00c3\f\f\2\2\u00c3\u00c4"+
		"\t\4\2\2\u00c4\u00ee\5\32\16\r\u00c5\u00c6\f\13\2\2\u00c6\u00c7\t\6\2"+
		"\2\u00c7\u00ee\5\32\16\f\u00c8\u00c9\f\n\2\2\u00c9\u00ca\t\7\2\2\u00ca"+
		"\u00ee\5\32\16\13\u00cb\u00cc\f\t\2\2\u00cc\u00cd\t\b\2\2\u00cd\u00ee"+
		"\5\32\16\n\u00ce\u00cf\f\b\2\2\u00cf\u00d0\7!\2\2\u00d0\u00ee\5\32\16"+
		"\t\u00d1\u00d2\f\7\2\2\u00d2\u00d3\7\"\2\2\u00d3\u00ee\5\32\16\b\u00d4"+
		"\u00d5\f\6\2\2\u00d5\u00d6\7#\2\2\u00d6\u00ee\5\32\16\7\u00d7\u00d8\f"+
		"\5\2\2\u00d8\u00d9\7$\2\2\u00d9\u00ee\5\32\16\6\u00da\u00db\f\4\2\2\u00db"+
		"\u00dc\7%\2\2\u00dc\u00ee\5\32\16\5\u00dd\u00de\f\3\2\2\u00de\u00df\7"+
		"&\2\2\u00df\u00ee\5\32\16\3\u00e0\u00e1\f\24\2\2\u00e1\u00e3\7\61\2\2"+
		"\u00e2\u00e4\5\30\r\2\u00e3\u00e2\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4\u00e5"+
		"\3\2\2\2\u00e5\u00ee\7\62\2\2\u00e6\u00e7\f\23\2\2\u00e7\u00e8\7/\2\2"+
		"\u00e8\u00e9\5\32\16\2\u00e9\u00ea\7\60\2\2\u00ea\u00ee\3\2\2\2\u00eb"+
		"\u00ec\f\22\2\2\u00ec\u00ee\t\3\2\2\u00ed\u00bc\3\2\2\2\u00ed\u00bf\3"+
		"\2\2\2\u00ed\u00c2\3\2\2\2\u00ed\u00c5\3\2\2\2\u00ed\u00c8\3\2\2\2\u00ed"+
		"\u00cb\3\2\2\2\u00ed\u00ce\3\2\2\2\u00ed\u00d1\3\2\2\2\u00ed\u00d4\3\2"+
		"\2\2\u00ed\u00d7\3\2\2\2\u00ed\u00da\3\2\2\2\u00ed\u00dd\3\2\2\2\u00ed"+
		"\u00e0\3\2\2\2\u00ed\u00e6\3\2\2\2\u00ed\u00eb\3\2\2\2\u00ee\u00f1\3\2"+
		"\2\2\u00ef\u00ed\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0\33\3\2\2\2\u00f1\u00ef"+
		"\3\2\2\2\u00f2\u00f5\5&\24\2\u00f3\u00f6\5\36\20\2\u00f4\u00f6\5 \21\2"+
		"\u00f5\u00f3\3\2\2\2\u00f5\u00f4\3\2\2\2\u00f6\35\3\2\2\2\u00f7\u00f8"+
		"\7\61\2\2\u00f8\u00f9\7\62\2\2\u00f9\37\3\2\2\2\u00fa\u00fb\7/\2\2\u00fb"+
		"\u00fc\5\32\16\2\u00fc\u00fd\7\60\2\2\u00fd\u00ff\3\2\2\2\u00fe\u00fa"+
		"\3\2\2\2\u00ff\u0102\3\2\2\2\u0100\u00fe\3\2\2\2\u0100\u0101\3\2\2\2\u0101"+
		"\u0107\3\2\2\2\u0102\u0100\3\2\2\2\u0103\u0104\7/\2\2\u0104\u0106\7\60"+
		"\2\2\u0105\u0103\3\2\2\2\u0106\u0109\3\2\2\2\u0107\u0105\3\2\2\2\u0107"+
		"\u0108\3\2\2\2\u0108!\3\2\2\2\u0109\u0107\3\2\2\2\u010a\u010b\5$\23\2"+
		"\u010b\u010e\7.\2\2\u010c\u010d\7&\2\2\u010d\u010f\5\32\16\2\u010e\u010c"+
		"\3\2\2\2\u010e\u010f\3\2\2\2\u010f#\3\2\2\2\u0110\u0115\5&\24\2\u0111"+
		"\u0112\7/\2\2\u0112\u0114\7\60\2\2\u0113\u0111\3\2\2\2\u0114\u0117\3\2"+
		"\2\2\u0115\u0113\3\2\2\2\u0115\u0116\3\2\2\2\u0116%\3\2\2\2\u0117\u0115"+
		"\3\2\2\2\u0118\u011b\5*\26\2\u0119\u011b\5(\25\2\u011a\u0118\3\2\2\2\u011a"+
		"\u0119\3\2\2\2\u011b\'\3\2\2\2\u011c\u011d\7.\2\2\u011d)\3\2\2\2\u011e"+
		"\u011f\t\t\2\2\u011f+\3\2\2\2\u0120\u0121\t\n\2\2\u0121-\3\2\2\2\34\61"+
		"<DIRWahpz\u0085\u0089\u008d\u0093\u009e\u00a8\u00ba\u00e3\u00ed\u00ef"+
		"\u00f5\u0100\u0107\u010e\u0115\u011a";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}