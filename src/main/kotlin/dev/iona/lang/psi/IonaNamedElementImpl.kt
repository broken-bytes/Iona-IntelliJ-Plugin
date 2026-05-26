package dev.iona.lang.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.util.IncorrectOperationException

abstract class IonaNamedElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), IonaNamedElement {

    override fun getNameIdentifier(): PsiElement? =
        node.findChildByType(IonaTypes.IDENTIFIER)?.psi

    override fun getName(): String? = nameIdentifier?.text

    override fun setName(name: String): PsiElement =
        throw IncorrectOperationException("Renaming Iona declarations is not yet supported")
}
