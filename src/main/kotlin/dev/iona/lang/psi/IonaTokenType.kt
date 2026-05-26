package dev.iona.lang.psi

import com.intellij.psi.tree.IElementType
import dev.iona.lang.IonaLanguage
import org.jetbrains.annotations.NonNls

class IonaTokenType(@NonNls debugName: String) : IElementType(debugName, IonaLanguage) {
    override fun toString(): String = "IonaTokenType.${super.toString()}"
}
