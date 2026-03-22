package dev.varik.eta

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

class EtaFileType private constructor() : LanguageFileType(EtaLanguage.INSTANCE) {
    companion object {
        @JvmField
        val INSTANCE = EtaFileType()
    }

    override fun getName(): String = "Eta"
    override fun getDescription(): String = "Eta template file"
    override fun getDefaultExtension(): String = "eta"
    override fun getIcon(): Icon = EtaIcons.FILE
}
