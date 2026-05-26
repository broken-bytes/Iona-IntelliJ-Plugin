package dev.iona.lang.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;
import dev.iona.lang.psi.IonaTypes;

%%

%class _IonaLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

WHITE_SPACE     = [ \t\f]+
EOL             = \r\n | \r | \n
LINE_COMMENT    = "//" [^\r\n]*
// Non-nesting block comment, mirrors the compiler's /* ... */ scan.
BLOCK_COMMENT   = "/*" ~"*/"
IDENTIFIER      = [a-zA-Z_][a-zA-Z0-9_]*
INTEGER         = [0-9]+
DOUBLE          = [0-9]+ "." [0-9]+
FLOAT           = ([0-9]+ "." [0-9]+ | [0-9]+) "f"
STRING          = \" ( [^\\\"\r\n] | \\ . )* \"

%%

<YYINITIAL> {
  {WHITE_SPACE}      { return TokenType.WHITE_SPACE; }
  {EOL}              { return IonaTypes.LINEBREAK; }
  {LINE_COMMENT}     { return IonaTypes.LINE_COMMENT; }
  {BLOCK_COMMENT}    { return IonaTypes.BLOCK_COMMENT; }

  // Keywords (listed before IDENTIFIER; JFlex longest-match + rule order resolves ties).
  "actor"            { return IonaTypes.ACTOR; }
  "as"               { return IonaTypes.AS; }
  "async"            { return IonaTypes.ASYNC; }
  "await"            { return IonaTypes.AWAIT; }
  "break"            { return IonaTypes.BREAK; }
  "captured"         { return IonaTypes.CAPTURED; }
  "catch"            { return IonaTypes.CATCH; }
  "class"            { return IonaTypes.CLASS; }
  "continue"         { return IonaTypes.CONTINUE; }
  "contract"         { return IonaTypes.CONTRACT; }
  "defer"            { return IonaTypes.DEFER; }
  "do"               { return IonaTypes.DO; }
  "else"             { return IonaTypes.ELSE; }
  "enum"             { return IonaTypes.ENUM; }
  "false"            { return IonaTypes.FALSE; }
  "fileprivate"      { return IonaTypes.FILEPRIVATE; }
  "for"              { return IonaTypes.FOR; }
  "from"             { return IonaTypes.FROM; }
  "fn"               { return IonaTypes.FN; }
  "guard"            { return IonaTypes.GUARD; }
  "if"               { return IonaTypes.IF; }
  "in"               { return IonaTypes.IN; }
  "use"              { return IonaTypes.USE; }
  "init"             { return IonaTypes.INIT; }
  "internal"         { return IonaTypes.INTERNAL; }
  "let"              { return IonaTypes.LET; }
  "module"           { return IonaTypes.MODULE; }
  "mut"              { return IonaTypes.MUT; }
  "null"             { return IonaTypes.NULL; }
  "op"               { return IonaTypes.OP; }
  "open"             { return IonaTypes.OPEN; }
  "private"          { return IonaTypes.PRIVATE; }
  "public"           { return IonaTypes.PUBLIC; }
  "record"           { return IonaTypes.RECORD; }
  "return"           { return IonaTypes.RETURN; }
  "self"             { return IonaTypes.SELF; }
  "static"           { return IonaTypes.STATIC; }
  "struct"           { return IonaTypes.STRUCT; }
  "task"             { return IonaTypes.TASK; }
  "throw"            { return IonaTypes.THROW; }
  "throws"           { return IonaTypes.THROWS; }
  "true"             { return IonaTypes.TRUE; }
  "try"              { return IonaTypes.TRY; }
  "until"            { return IonaTypes.UNTIL; }
  "var"              { return IonaTypes.VAR; }
  "when"             { return IonaTypes.WHEN; }
  "while"            { return IonaTypes.WHILE; }
  "with"             { return IonaTypes.WITH; }
  "yield"            { return IonaTypes.YIELD; }

  // Literals (FLOAT before DOUBLE before INTEGER; longest-match handles overlap).
  {STRING}           { return IonaTypes.STRING; }
  {FLOAT}            { return IonaTypes.FLOAT; }
  {DOUBLE}           { return IonaTypes.DOUBLE; }
  {INTEGER}          { return IonaTypes.INTEGER; }
  {IDENTIFIER}       { return IonaTypes.IDENTIFIER; }

  // Operators & punctuation (multi-char win by longest-match).
  "..."              { return IonaTypes.RANGE; }
  "::"               { return IonaTypes.SCOPE; }
  "->"               { return IonaTypes.ARROW; }
  "++"               { return IonaTypes.INCREMENT; }
  "--"               { return IonaTypes.DECREMENT; }
  "<<="              { return IonaTypes.SHL_ASSIGN; }
  ">>="              { return IonaTypes.SHR_ASSIGN; }
  "<<"               { return IonaTypes.SHL; }
  ">>"               { return IonaTypes.SHR; }
  "+="               { return IonaTypes.PLUS_ASSIGN; }
  "-="               { return IonaTypes.MINUS_ASSIGN; }
  "*="               { return IonaTypes.MUL_ASSIGN; }
  "/="               { return IonaTypes.DIV_ASSIGN; }
  "%="               { return IonaTypes.MOD_ASSIGN; }
  "&="               { return IonaTypes.AND_ASSIGN; }
  "|="               { return IonaTypes.OR_ASSIGN; }
  "^="               { return IonaTypes.XOR_ASSIGN; }
  "=="               { return IonaTypes.EQUAL; }
  "!="               { return IonaTypes.NOT_EQUAL; }
  "<="               { return IonaTypes.LESS_EQUAL; }
  ">="               { return IonaTypes.GREATER_EQUAL; }
  "&&"               { return IonaTypes.AND_AND; }
  "||"               { return IonaTypes.OR_OR; }
  "+"                { return IonaTypes.PLUS; }
  "-"                { return IonaTypes.MINUS; }
  "*"                { return IonaTypes.MULTIPLY; }
  "/"                { return IonaTypes.DIVIDE; }
  "%"                { return IonaTypes.MODULO; }
  "&"                { return IonaTypes.BIT_AND; }
  "|"                { return IonaTypes.BIT_OR; }
  "^"                { return IonaTypes.XOR; }
  "~"                { return IonaTypes.BIT_INVERSE; }
  "!"                { return IonaTypes.NOT; }
  "="                { return IonaTypes.ASSIGN; }
  "<"                { return IonaTypes.LESS; }
  ">"                { return IonaTypes.GREATER; }
  "."                { return IonaTypes.DOT; }
  "?"                { return IonaTypes.OPTIONAL; }
  "@"                { return IonaTypes.ANNOTATION; }
  ":"                { return IonaTypes.COLON; }
  ","                { return IonaTypes.COMMA; }
  "("                { return IonaTypes.PAREN_LEFT; }
  ")"                { return IonaTypes.PAREN_RIGHT; }
  "["                { return IonaTypes.BRACKET_LEFT; }
  "]"                { return IonaTypes.BRACKET_RIGHT; }
  "{"                { return IonaTypes.CURLY_LEFT; }
  "}"                { return IonaTypes.CURLY_RIGHT; }
}

[^]                  { return TokenType.BAD_CHARACTER; }
