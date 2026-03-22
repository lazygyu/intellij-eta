package dev.varik.eta.psi

import com.intellij.lang.ASTFactory
import com.intellij.psi.impl.source.tree.LeafElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.templateLanguages.OuterLanguageElementImpl
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.OuterLanguageElementType

class EtaASTFactory : ASTFactory() {
    override fun createLeaf(type: IElementType, text: CharSequence): LeafElement {
        if (type is OuterLanguageElementType) {
            return OuterLanguageElementImpl(type, text)
        }
        return LeafPsiElement(type, text)
    }
}
