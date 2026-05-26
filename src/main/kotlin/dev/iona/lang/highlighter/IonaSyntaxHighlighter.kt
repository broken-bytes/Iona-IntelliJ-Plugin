package dev.iona.lang.highlighter

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import dev.iona.lang.lexer.IonaLexerAdapter
import dev.iona.lang.psi.IonaTokenSets
import dev.iona.lang.psi.IonaTypes

class IonaSyntaxHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer = IonaLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> = when {
        IonaTokenSets.KEYWORDS.contains(tokenType) -> pack(KEYWORD)
        IonaTokenSets.CONSTANTS.contains(tokenType) -> pack(CONSTANT)
        IonaTokenSets.NUMBERS.contains(tokenType) -> pack(NUMBER)
        IonaTokenSets.STRINGS.contains(tokenType) -> pack(STRING)
        IonaTokenSets.OPERATORS.contains(tokenType) -> pack(OPERATION)
        IonaTokenSets.PARENS.contains(tokenType) -> pack(PARENTHESES)
        IonaTokenSets.BRACKETS.contains(tokenType) -> pack(BRACKETS)
        IonaTokenSets.BRACES.contains(tokenType) -> pack(BRACES)
        tokenType == IonaTypes.LINE_COMMENT -> pack(LINE_COMMENT)
        tokenType == IonaTypes.BLOCK_COMMENT -> pack(BLOCK_COMMENT)
        tokenType == IonaTypes.IDENTIFIER -> pack(IDENTIFIER)
        tokenType == IonaTypes.COMMA -> pack(COMMA)
        tokenType == IonaTypes.COLON -> pack(COLON)
        tokenType == IonaTypes.ANNOTATION -> pack(ANNOTATION)
        tokenType == TokenType.BAD_CHARACTER -> pack(BAD_CHARACTER)
        else -> TextAttributesKey.EMPTY_ARRAY
    }

    companion object {
        val KEYWORD = key("IONA_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD)
        val CONSTANT = key("IONA_CONSTANT", DefaultLanguageHighlighterColors.CONSTANT)
        val NUMBER = key("IONA_NUMBER", DefaultLanguageHighlighterColors.NUMBER)
        val STRING = key("IONA_STRING", DefaultLanguageHighlighterColors.STRING)
        val LINE_COMMENT = key("IONA_LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val BLOCK_COMMENT = key("IONA_BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT)
        val OPERATION = key("IONA_OPERATION", DefaultLanguageHighlighterColors.OPERATION_SIGN)
        val IDENTIFIER = key("IONA_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER)
        val PARENTHESES = key("IONA_PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES)
        val BRACKETS = key("IONA_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS)
        val BRACES = key("IONA_BRACES", DefaultLanguageHighlighterColors.BRACES)
        val COMMA = key("IONA_COMMA", DefaultLanguageHighlighterColors.COMMA)
        val COLON = key("IONA_COLON", DefaultLanguageHighlighterColors.OPERATION_SIGN)
        val ANNOTATION = key("IONA_ANNOTATION", DefaultLanguageHighlighterColors.METADATA)
        val BAD_CHARACTER = key("IONA_BAD_CHARACTER", com.intellij.openapi.editor.HighlighterColors.BAD_CHARACTER)

        private fun key(name: String, fallback: TextAttributesKey) =
            createTextAttributesKey(name, fallback)
    }
}
