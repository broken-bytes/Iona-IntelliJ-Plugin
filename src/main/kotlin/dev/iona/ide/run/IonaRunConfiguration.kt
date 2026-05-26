package dev.iona.ide.run

import com.intellij.execution.ExecutionException
import com.intellij.execution.Executor
import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.util.execution.ParametersListUtil
import dev.iona.toolchain.IonaToolchain
import java.io.File
import java.nio.charset.StandardCharsets

class IonaRunConfiguration(project: Project, factory: IonaConfigurationFactory, name: String?) :
    RunConfigurationBase<IonaRunConfigurationOptions>(project, factory, name) {

    public override fun getOptions(): IonaRunConfigurationOptions =
        super.getOptions() as IonaRunConfigurationOptions

    var filePath: String?
        get() = options.filePath
        set(value) { options.filePath = value }

    var programArguments: String?
        get() = options.programArguments
        set(value) { options.programArguments = value }

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> =
        IonaSettingsEditor(project)

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState {
        val base = IonaToolchain.resolveCommand() ?: throw ExecutionException(
            "Iona compiler command not configured: set -D${IonaToolchain.PROP_COMMAND}, " +
                "\$${IonaToolchain.ENV_COMMAND}, or write the command to ~/${IonaToolchain.CONFIG_FILE}"
        )
        val file = filePath?.takeIf { it.isNotBlank() }
            ?: throw ExecutionException("No Iona file specified in the run configuration")

        return object : CommandLineState(environment) {
            override fun startProcess(): ProcessHandler {
                val commandLine = GeneralCommandLine(base)
                    .withParameters(ParametersListUtil.parse(programArguments.orEmpty()))
                    .withParameters(file)
                    .withWorkDirectory(File(file).parent)
                    .withCharset(StandardCharsets.UTF_8)
                val handler = OSProcessHandler(commandLine)
                ProcessTerminatedListener.attach(handler)
                return handler
            }
        }
    }
}
