package dev.varik.eta.psi

import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.OuterLanguageElementType
import dev.varik.eta.EtaLanguage

class EtaOuterElementType private constructor() : OuterLanguageElementType("ETA_FRAGMENT", EtaLanguage.INSTANCE) {
    companion object {
        @JvmField
        val INSTANCE = EtaOuterElementType()
    }
}
