grammar mx;
program:
    progStatement* EOF;

classDeclaration:
	'class' Identifier '{'
		classStatement*
	'}';
progStatement:
    classDeclaration | functionDeclaration |declaration;

classStatement:
    declaration
    | functionDeclaration
    | constructFunction
    ;
constructFunction:
	Identifier '(' ')' block;

functionDeclaration:
	(typeName | 'void') Identifier '(' (argumentList)? ')' block;

argumentList:
    	declarator (',' declarator)*;

block:
	'{' blockStatement*  '}';
blockStatement:
    block | statement | declaration;

statement:
    block
	| type='if' '(' expression ')' statement  ('else' statement)?
	| type='while' '(' expression ')' statement
	| type='for' '(' expression';' (expression)? ';'  expression')' statement
	| type='return' expression? ';'
	| type='break' ';'
	| type='continue'';'
	| expression ';'
	| ';'
	;

declaration:
    declarator ';';
expressionList:
	expression (',' expression)*;
expression:
    constant
    | Identifier
    | '(' expression ')'
    | expression op='.' expression
    | expression op='(' expressionList? ')'
    | expression op='[' expression ']'
        |   expression op=('++'|'--')
        |   <assoc=right> op='new' creator
        |   <assoc=right> op=('~'|'!') expression
        |   <assoc=right> op=('++'|'--') expression
        |   <assoc=right> op=('+'|'-') expression
        |   expression op=('*'|'/'|'%') expression
        |   expression op=('+'|'-') expression
        |   expression op=('<<'|'>>') expression
        |   expression op=('<='|'>='|'<'|'>') expression
        |   expression op=('=='|'!=') expression
        |   expression op='&' expression
        |   expression op='^' expression
        |   expression op='|' expression
        |   expression op='&&' expression
        |   expression op='||' expression
        |   <assoc=right> expression op='=' expression
	;
    
creator:
	notArrayTypeName (LB expression RB)* (LB RB)*;

declarator:
	typeName Identifier ('=' expression)?;


typeName:
	notArrayTypeName (LB RB)*;
notArrayTypeName:
    primitiveTypeName | classTypeName;
classTypeName:
    Identifier;
primitiveTypeName:
	'bool' | 'int' | 'string';
constant:
    BoolConstant
    | IntegerConstant
    | StringConstant
    | PointConstant;
BoolConstant:
    'true' | 'false';
PointConstant:
    'this' | 'null';
IntegerConstant:
	([1-9] [0-9]*)
	| '0' ;
StringConstant:
	'"' ( ~('"'|'\\')+ | EscapeSequence)? '"';
Identifier:
	[a-zA-Z] ([0-9a-zA-Z_])*;

fragment

EscapeSequence:
	'\\'('n'|'"'|'\\');

LB    : '[' ;
RB    : ']' ;
LP    : '(' ;
RP    : ')' ;
LBB   : '{' ;
RBB   : '}' ;

SL_COMMENT : '//'.*?'\r'?'\n' -> skip ;
COMMENT : '/*' .*? '*/' -> skip ;
NEWLINE : ('\r'?'\n')+ -> skip ;
WS : (' '|'\t'|'\n')+ -> channel(HIDDEN);
