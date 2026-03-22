package dev.varik.eta.parsing

import com.intellij.psi.TokenType
import com.intellij.psi.tree.TokenSet

object EtaTokenTypes {
    // Delimiters
    @JvmField val OPEN_TAG = EtaElementType("OPEN_TAG")
    @JvmField val CLOSE_TAG = EtaElementType("CLOSE_TAG")

    // Tag type modifiers (immediately after <%)
    @JvmField val EQUALS = EtaElementType("EQUALS")       // = (interpolation)
    @JvmField val TILDE = EtaElementType("TILDE")         // ~ (raw output)

    // Whitespace control markers
    @JvmField val TRIM_WS = EtaElementType("TRIM_WS")     // _ (trim all whitespace)
    @JvmField val TRIM_NL = EtaElementType("TRIM_NL")     // - (trim newline)

    // Content tokens
    @JvmField val TEMPLATE_DATA = EtaElementType("TEMPLATE_DATA")
    @JvmField val INNER_JS = EtaElementType("INNER_JS")

    // Token sets
    @JvmField val WHITESPACES = TokenSet.create(TokenType.WHITE_SPACE)
    @JvmField val COMMENTS = TokenSet.EMPTY
    @JvmField val STRING_LITERALS = TokenSet.EMPTY
}
