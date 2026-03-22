package dev.varik.eta.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import dev.varik.eta.EtaFileType
import dev.varik.eta.EtaLanguage

class EtaFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, EtaLanguage.INSTANCE) {
    override fun getFileType(): FileType = EtaFileType.INSTANCE
    override fun toString(): String = "Eta File"
}
