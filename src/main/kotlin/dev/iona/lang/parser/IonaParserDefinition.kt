package dev.iona.lang.parser

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import dev.iona.lang.IonaFile
import dev.iona.lang.IonaLanguage
import dev.iona.lang.lexer.IonaLexerAdapter
import dev.iona.lang.psi.IonaTokenSets
import dev.iona.lang.psi.IonaTypes

class IonaParserDefinition : ParserDefinition {

    override fun createLexer(project: Project?): Lexer = IonaLexerAdapter()
    override fun createParser(project: Project?): PsiParser = IonaParser()
    override fun getFileNodeType(): IFileElementType = FILE

    // TODO: Support actual linebreaks, rn they are treated as whitespace
    override fun getWhitespaceTokens(): TokenSet = WHITE_SPACES
    override fun getCommentTokens(): TokenSet = IonaTokenSets.COMMENTS
    override fun getStringLiteralElements(): TokenSet = IonaTokenSets.STRINGS

    override fun createElement(node: ASTNode): PsiElement = IonaTypes.Factory.createElement(node)
    override fun createFile(viewProvider: FileViewProvider): PsiFile = IonaFile(viewProvider)

    companion object {
        val FILE = IFileElementType(IonaLanguage)
        val WHITE_SPACES: TokenSet = TokenSet.create(TokenType.WHITE_SPACE, IonaTypes.LINEBREAK)
    }
}
