package dev.iona.lang.psi

import com.intellij.psi.tree.TokenSet
import dev.iona.lang.psi.IonaTypes as T

object IonaTokenSets {
    val KEYWORDS: TokenSet = TokenSet.create(
        T.ACTOR, T.AS, T.ASYNC, T.AWAIT, T.BREAK, T.CAPTURED, T.CATCH, T.CLASS, T.CONTINUE,
        T.CONTRACT, T.DEFER, T.DO, T.ELSE, T.ENUM, T.FILEPRIVATE, T.FOR, T.FROM, T.FN, T.GUARD,
        T.IF, T.IN, T.USE, T.INIT, T.INTERNAL, T.LET, T.MODULE, T.MUT, T.OP, T.OPEN, T.PRIVATE,
        T.PUBLIC, T.RECORD, T.RETURN, T.SELF, T.STATIC, T.STRUCT, T.TASK, T.THROW, T.THROWS,
        T.TRY, T.UNTIL, T.VAR, T.WHEN, T.WHILE, T.WITH, T.YIELD,
    )

    val CONSTANTS: TokenSet = TokenSet.create(T.TRUE, T.FALSE, T.NULL)

    val NUMBERS: TokenSet = TokenSet.create(T.INTEGER, T.DOUBLE, T.FLOAT)
    val STRINGS: TokenSet = TokenSet.create(T.STRING)
    val COMMENTS: TokenSet = TokenSet.create(T.LINE_COMMENT, T.BLOCK_COMMENT)

    val OPERATORS: TokenSet = TokenSet.create(
        T.RANGE, T.SCOPE, T.ARROW, T.INCREMENT, T.DECREMENT, T.SHL_ASSIGN, T.SHR_ASSIGN, T.SHL,
        T.SHR, T.PLUS_ASSIGN, T.MINUS_ASSIGN, T.MUL_ASSIGN, T.DIV_ASSIGN, T.MOD_ASSIGN,
        T.AND_ASSIGN, T.OR_ASSIGN, T.XOR_ASSIGN, T.EQUAL, T.NOT_EQUAL, T.LESS_EQUAL,
        T.GREATER_EQUAL, T.AND_AND, T.OR_OR, T.PLUS, T.MINUS, T.MULTIPLY, T.DIVIDE, T.MODULO,
        T.BIT_AND, T.BIT_OR, T.XOR, T.BIT_INVERSE, T.NOT, T.ASSIGN, T.LESS, T.GREATER, T.DOT,
        T.OPTIONAL,
    )

    val PARENS: TokenSet = TokenSet.create(T.PAREN_LEFT, T.PAREN_RIGHT)
    val BRACKETS: TokenSet = TokenSet.create(T.BRACKET_LEFT, T.BRACKET_RIGHT)
    val BRACES: TokenSet = TokenSet.create(T.CURLY_LEFT, T.CURLY_RIGHT)
}
