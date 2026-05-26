package dev.iona.ide.run

import com.intellij.execution.configurations.ConfigurationTypeBase
import com.intellij.execution.configurations.ConfigurationTypeUtil
import com.intellij.openapi.util.NotNullLazyValue
import dev.iona.lang.IonaIcons

class IonaRunConfigurationType : ConfigurationTypeBase(
    ID,
    "Iona",
    "Build and run an Iona source file",
    NotNullLazyValue.createValue { IonaIcons.FILE },
) {
    init {
        addFactory(IonaConfigurationFactory(this))
    }

    companion object {
        const val ID = "IonaRunConfiguration"

        fun getInstance(): IonaRunConfigurationType =
            ConfigurationTypeUtil.findConfigurationType(IonaRunConfigurationType::class.java)
    }
}
