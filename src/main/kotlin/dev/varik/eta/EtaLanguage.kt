package dev.varik.eta

import com.intellij.lang.Language
import com.intellij.psi.templateLanguages.TemplateLanguage

class EtaLanguage private constructor() : Language("Eta"), TemplateLanguage {
    companion object {
        @JvmField
        val INSTANCE = EtaLanguage()
    }
}
