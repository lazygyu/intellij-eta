package dev.varik.eta.file

import com.intellij.lang.Language
import com.intellij.lang.LanguageParserDefinitions
import com.intellij.lang.html.HTMLLanguage
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.MultiplePsiFilesPerDocumentFileViewProvider
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.impl.source.PsiFileImpl
import com.intellij.psi.templateLanguages.ConfigurableTemplateLanguageFileViewProvider
import com.intellij.psi.templateLanguages.TemplateDataElementType
import com.intellij.psi.templateLanguages.TemplateDataLanguageMappings
import dev.varik.eta.EtaLanguage
import dev.varik.eta.parsing.EtaTokenTypes
import dev.varik.eta.psi.EtaOuterElementType
import java.util.concurrent.ConcurrentHashMap

class EtaFileViewProvider(
    manager: PsiManager,
    virtualFile: VirtualFile,
    eventSystemEnabled: Boolean
) : MultiplePsiFilesPerDocumentFileViewProvider(manager, virtualFile, eventSystemEnabled),
    ConfigurableTemplateLanguageFileViewProvider {

    private val templateDataLanguage: Language = computeTemplateDataLanguage(manager, virtualFile)

    override fun getBaseLanguage(): Language = EtaLanguage.INSTANCE

    override fun getTemplateDataLanguage(): Language = templateDataLanguage

    override fun getLanguages(): Set<Language> = setOf(baseLanguage, templateDataLanguage)

    override fun cloneInner(fileCopy: VirtualFile): MultiplePsiFilesPerDocumentFileViewProvider {
        return EtaFileViewProvider(manager, fileCopy, false)
    }

    override fun createFile(lang: Language): PsiFile? {
        val parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(lang) ?: return null

        return if (lang.isKindOf(templateDataLanguage)) {
            val file = parserDefinition.createFile(this) as PsiFileImpl
            file.contentElementType = getTemplateDataElementType(templateDataLanguage)
            file
        } else if (lang.isKindOf(baseLanguage)) {
            parserDefinition.createFile(this)
        } else {
            null
        }
    }

    companion object {
        private val TEMPLATE_DATA_TO_LANG = ConcurrentHashMap<String, TemplateDataElementType>()

        private fun getTemplateDataElementType(lang: Language): TemplateDataElementType {
            return TEMPLATE_DATA_TO_LANG.computeIfAbsent(lang.id) {
                TemplateDataElementType(
                    "ETA_TEMPLATE_DATA",
                    EtaLanguage.INSTANCE,
                    EtaTokenTypes.TEMPLATE_DATA,
                    EtaOuterElementType.INSTANCE
                )
            }
        }

        private fun computeTemplateDataLanguage(manager: PsiManager, file: VirtualFile): Language {
            val mappings = TemplateDataLanguageMappings.getInstance(manager.project)
            val dataLang = mappings.getMapping(file)
            if (dataLang != null) return dataLang

            return HTMLLanguage.INSTANCE
        }
    }
}
