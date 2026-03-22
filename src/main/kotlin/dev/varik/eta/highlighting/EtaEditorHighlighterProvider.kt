package dev.varik.eta.highlighting

import com.intellij.openapi.editor.colors.EditorColorsScheme
import com.intellij.openapi.editor.ex.util.LayerDescriptor
import com.intellij.openapi.editor.ex.util.LayeredLexerEditorHighlighter
import com.intellij.openapi.editor.highlighter.EditorHighlighter
import com.intellij.openapi.fileTypes.EditorHighlighterProvider
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.lang.html.HTMLLanguage
import com.intellij.lang.javascript.JavaScriptSupportLoader
import dev.varik.eta.parsing.EtaTokenTypes

class EtaEditorHighlighterProvider : EditorHighlighterProvider {
    override fun getEditorHighlighter(
        project: Project?,
        fileType: FileType,
        virtualFile: VirtualFile?,
        colors: EditorColorsScheme
    ): EditorHighlighter {
        return EtaLayeredHighlighter(project, virtualFile, colors)
    }
}

class EtaLayeredHighlighter(
    project: Project?,
    virtualFile: VirtualFile?,
    colors: EditorColorsScheme
) : LayeredLexerEditorHighlighter(EtaSyntaxHighlighter(), colors) {
    init {
        val htmlHighlighter = SyntaxHighlighterFactory.getSyntaxHighlighter(
            HTMLLanguage.INSTANCE, project, virtualFile
        )
        val htmlLayer = LayerDescriptor(htmlHighlighter, "\n")
        registerLayer(EtaTokenTypes.TEMPLATE_DATA, htmlLayer)

        // JavaScript highlighting inside Eta tags
        val jsLanguage = JavaScriptSupportLoader.ECMA_SCRIPT_6
        val jsHighlighter = SyntaxHighlighterFactory.getSyntaxHighlighter(
            jsLanguage, project, virtualFile
        )
        val jsLayer = LayerDescriptor(jsHighlighter, "\n")
        registerLayer(EtaTokenTypes.INNER_JS, jsLayer)
    }
}
