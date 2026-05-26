package dev.iona.lang

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object IonaFileType : LanguageFileType(IonaLanguage) {
    override fun getName() = "Iona File"
    override fun getDescription() = "Iona language source file"
    override fun getDefaultExtension() = "iona"
    override fun getIcon(): Icon = IonaIcons.FILE
}
