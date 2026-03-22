package dev.varik.eta.highlighting

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey

object EtaHighlighterColors {
    @JvmField
    val DELIMITER = TextAttributesKey.createTextAttributesKey(
        "ETA_DELIMITER", DefaultLanguageHighlighterColors.BRACES
    )

    @JvmField
    val INTERPOLATION_MARKER = TextAttributesKey.createTextAttributesKey(
        "ETA_INTERPOLATION_MARKER", DefaultLanguageHighlighterColors.OPERATION_SIGN
    )

    @JvmField
    val RAW_MARKER = TextAttributesKey.createTextAttributesKey(
        "ETA_RAW_MARKER", DefaultLanguageHighlighterColors.OPERATION_SIGN
    )

    @JvmField
    val WHITESPACE_CONTROL = TextAttributesKey.createTextAttributesKey(
        "ETA_WHITESPACE_CONTROL", DefaultLanguageHighlighterColors.METADATA
    )

    @JvmField
    val JS_CODE = TextAttributesKey.createTextAttributesKey(
        "ETA_JS_CODE", DefaultLanguageHighlighterColors.TEMPLATE_LANGUAGE_COLOR
    )
}
