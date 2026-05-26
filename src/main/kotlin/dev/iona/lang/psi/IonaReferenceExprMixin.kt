package dev.iona.lang.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiReference

abstract class IonaReferenceExprMixin(node: ASTNode) : ASTWrapperPsiElement(node) {
    override fun getReference(): PsiReference = IonaReference(this, text)
}
