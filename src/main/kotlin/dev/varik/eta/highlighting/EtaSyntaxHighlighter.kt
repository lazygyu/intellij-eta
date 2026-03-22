package dev.varik.eta.highlighting

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import dev.varik.eta.parsing.EtaLexer
import dev.varik.eta.parsing.EtaTokenTypes

class EtaSyntaxHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer = EtaLexer()

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when (tokenType) {
            EtaTokenTypes.OPEN_TAG -> pack(EtaHighlighterColors.DELIMITER)
            EtaTokenTypes.CLOSE_TAG -> pack(EtaHighlighterColors.DELIMITER)
            EtaTokenTypes.EQUALS -> pack(EtaHighlighterColors.INTERPOLATION_MARKER)
            EtaTokenTypes.TILDE -> pack(EtaHighlighterColors.RAW_MARKER)
            EtaTokenTypes.TRIM_WS -> pack(EtaHighlighterColors.WHITESPACE_CONTROL)
            EtaTokenTypes.TRIM_NL -> pack(EtaHighlighterColors.WHITESPACE_CONTROL)
            EtaTokenTypes.INNER_JS -> pack(EtaHighlighterColors.JS_CODE)
            else -> TextAttributesKey.EMPTY_ARRAY
        }
    }
}
