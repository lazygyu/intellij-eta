package dev.varik.eta

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import dev.varik.eta.parsing.EtaTokenTypes

class EtaFoldingBuilder : FoldingBuilderEx() {

    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
        val descriptors = mutableListOf<FoldingDescriptor>()
        val node = root.node ?: return FoldingDescriptor.EMPTY_ARRAY

        collectFoldRegions(node, descriptors)
        return descriptors.toTypedArray()
    }

    private fun collectFoldRegions(node: ASTNode, descriptors: MutableList<FoldingDescriptor>) {
        // Find matching OPEN_TAG...CLOSE_TAG pairs that span multiple lines
        var child = node.firstChildNode
        var openTag: ASTNode? = null

        while (child != null) {
            when (child.elementType) {
                EtaTokenTypes.OPEN_TAG -> {
                    openTag = child
                }
                EtaTokenTypes.CLOSE_TAG -> {
                    if (openTag != null) {
                        val range = TextRange(openTag.startOffset, child.startOffset + child.textLength)
                        if (range.length > 0) {
                            descriptors.add(FoldingDescriptor(openTag, range))
                        }
                    }
                    openTag = null
                }
            }
            child = child.treeNext
        }

        // Recurse into child nodes
        var childNode = node.firstChildNode
        while (childNode != null) {
            if (childNode.firstChildNode != null) {
                collectFoldRegions(childNode, descriptors)
            }
            childNode = childNode.treeNext
        }
    }

    override fun getPlaceholderText(node: ASTNode): String = "<% ... %>"

    override fun isCollapsedByDefault(node: ASTNode): Boolean = false
}
