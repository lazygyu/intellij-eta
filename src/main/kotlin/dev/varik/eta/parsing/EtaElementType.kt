package dev.varik.eta.parsing

import com.intellij.psi.tree.IElementType
import dev.varik.eta.EtaLanguage

class EtaElementType(debugName: String) : IElementType(debugName, EtaLanguage.INSTANCE)
