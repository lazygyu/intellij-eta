package dev.varik.eta.highlighting

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import dev.varik.eta.EtaIcons
import javax.swing.Icon

class EtaColorSettingsPage : ColorSettingsPage {

    override fun getIcon(): Icon = EtaIcons.FILE

    override fun getHighlighter(): SyntaxHighlighter = EtaSyntaxHighlighter()

    override fun getDemoText(): String = """
<h1>Welcome</h1>
<% if (it.showGreeting) { %>
  <p>Hello, <%= it.name %>!</p>
  <div><%~ it.rawHtml %></div>
  <%_ it.trimmedValue _%>
  <%- it.newlineTrimmed -%>
<% } %>
<ul>
<% it.items.forEach(function(item) { %>
  <li><%= item.title %></li>
<% }) %>
</ul>
<%~ include("./footer") %>
""".trimIndent()

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey>? = null

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = DESCRIPTORS

    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

    override fun getDisplayName(): String = "Eta Template"

    companion object {
        private val DESCRIPTORS = arrayOf(
            AttributesDescriptor("Delimiter (<% %>)", EtaHighlighterColors.DELIMITER),
            AttributesDescriptor("Interpolation marker (=)", EtaHighlighterColors.INTERPOLATION_MARKER),
            AttributesDescriptor("Raw output marker (~)", EtaHighlighterColors.RAW_MARKER),
            AttributesDescriptor("Whitespace control (_ -)", EtaHighlighterColors.WHITESPACE_CONTROL),
            AttributesDescriptor("JavaScript code", EtaHighlighterColors.JS_CODE),
        )
    }
}
