package dev.iona.ide.run

import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.openapi.components.BaseState
import com.intellij.openapi.project.Project

class IonaConfigurationFactory(type: ConfigurationType) : ConfigurationFactory(type) {

    override fun getId(): String = "IonaRunConfigurationFactory"

    override fun createTemplateConfiguration(project: Project): RunConfiguration =
        IonaRunConfiguration(project, this, "Iona")

    override fun getOptionsClass(): Class<out BaseState> = IonaRunConfigurationOptions::class.java
}
