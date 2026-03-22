package dev.varik.eta.parsing

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import dev.varik.eta.EtaLanguage
import dev.varik.eta.psi.EtaFile
import dev.varik.eta.psi.EtaPsiElement

class EtaParserDefinition : ParserDefinition {

    override fun createLexer(project: Project?): Lexer = EtaLexer()

    override fun createParser(project: Project?): PsiParser {
        return PsiParser { root, builder ->
            val marker = builder.mark()
            while (!builder.eof()) {
                builder.advanceLexer()
            }
            marker.done(root)
            builder.treeBuilt
        }
    }

    override fun getFileNodeType(): IFileElementType = FILE

    override fun getWhitespaceTokens(): TokenSet = EtaTokenTypes.WHITESPACES
    override fun getCommentTokens(): TokenSet = EtaTokenTypes.COMMENTS
    override fun getStringLiteralElements(): TokenSet = EtaTokenTypes.STRING_LITERALS

    override fun createElement(node: ASTNode): PsiElement = EtaPsiElement(node)
    override fun createFile(viewProvider: FileViewProvider): PsiFile = EtaFile(viewProvider)

    companion object {
        @JvmField
        val FILE = IFileElementType(EtaLanguage.INSTANCE)
    }
}
