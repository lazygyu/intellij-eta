package dev.varik.eta.injection

import com.intellij.lang.injection.MultiHostInjector
import com.intellij.lang.injection.MultiHostRegistrar
import com.intellij.lang.javascript.JavaScriptSupportLoader
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLanguageInjectionHost
import com.intellij.psi.util.PsiTreeUtil
import dev.varik.eta.psi.EtaJsBlock

class EtaJavaScriptInjector : MultiHostInjector {

    companion object {
        // Declare Eta built-in variables and functions as JS prefix
        private const val ETA_PREAMBLE = """
/** @type {any} */
var it;
/** @param {string} path @param {object} [data] @returns {string} */
function include(path, data) {}
/** @param {string} path @param {object} [data] @returns {Promise<string>} */
function includeAsync(path, data) {}
/** @param {string} path @param {object} [data] @returns {void} */
function layout(path, data) {}
;
"""
    }

    override fun getLanguagesToInject(registrar: MultiHostRegistrar, context: PsiElement) {
        if (context !is EtaJsBlock) return
        if (!context.isValidHost) return

        val file = context.containingFile ?: return
        val allBlocks = PsiTreeUtil.findChildrenOfType(file, EtaJsBlock::class.java)
        if (allBlocks.isEmpty()) return

        // Only process from the first JS block to avoid duplicate injections
        if (allBlocks.first() != context) return

        val jsLanguage = JavaScriptSupportLoader.ECMA_SCRIPT_6 ?: return

        registrar.startInjecting(jsLanguage)

        var isFirst = true
        for (block in allBlocks) {
            if (block.textLength == 0) continue

            val prefix = if (isFirst) ETA_PREAMBLE else "\n"
            registrar.addPlace(
                prefix,
                "\n",
                block as PsiLanguageInjectionHost,
                TextRange(0, block.textLength)
            )
            isFirst = false
        }

        registrar.doneInjecting()
    }

    override fun elementsToInjectIn(): List<Class<out PsiElement>> {
        return listOf(EtaJsBlock::class.java)
    }
}
