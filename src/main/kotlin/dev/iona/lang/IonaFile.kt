package dev.iona.lang

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.psi.FileViewProvider

class IonaFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, IonaLanguage) {
    override fun getFileType() = IonaFileType
    override fun toString() = "Iona File"
}
