package dev.iona.lang.psi

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.util.PsiTreeUtil

class IonaReference(element: PsiElement, private val name: String) :
    PsiReferenceBase<PsiElement>(element, TextRange(0, element.textLength)) {

    override fun resolve(): PsiElement? {
        val file = element.containingFile ?: return null
        val refOffset = element.textRange.startOffset

        val candidates = PsiTreeUtil
            .collectElementsOfType(file, IonaNamedElement::class.java)
            .filter { it.name == name }

        return candidates
            .filter { it.textRange.startOffset < refOffset }
            .maxByOrNull { it.textRange.startOffset }
            ?: candidates.first()
    }

    override fun getVariants(): Array<Any> {
        val file = element.containingFile ?: return emptyArray()

        return PsiTreeUtil.collectElementsOfType(file, IonaNamedElement::class.java)
            .mapNotNull { it.name }
            .distinct()
            .toTypedArray()
    }
}
