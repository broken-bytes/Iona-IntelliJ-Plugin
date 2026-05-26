package dev.iona.ide.run

import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.actions.LazyRunConfigurationProducer
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiElement
import dev.iona.lang.IonaFileType

class IonaRunConfigurationProducer : LazyRunConfigurationProducer<IonaRunConfiguration>() {

    override fun getConfigurationFactory(): ConfigurationFactory =
        IonaRunConfigurationType.getInstance().configurationFactories.first()

    override fun setupConfigurationFromContext(
        configuration: IonaRunConfiguration,
        context: ConfigurationContext,
        sourceElement: Ref<PsiElement>,
    ): Boolean {
        val file = context.location?.virtualFile ?: return false
        if (file.fileType != IonaFileType) return false
        configuration.filePath = file.path
        configuration.name = file.name
        return true
    }

    override fun isConfigurationFromContext(
        configuration: IonaRunConfiguration,
        context: ConfigurationContext,
    ): Boolean {
        val file = context.location?.virtualFile ?: return false
        return file.fileType == IonaFileType && file.path == configuration.filePath
    }
}
