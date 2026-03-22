package dev.varik.eta

import com.intellij.lang.Commenter

class EtaCommenter : Commenter {
    override fun getLineCommentPrefix(): String? = null
    override fun getBlockCommentPrefix(): String = "<%/* "
    override fun getBlockCommentSuffix(): String = " */%>"
    override fun getCommentedBlockCommentPrefix(): String? = null
    override fun getCommentedBlockCommentSuffix(): String? = null
}
