package dev.iona.ide.run

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.RawCommandLineEditor
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent

class IonaSettingsEditor(project: Project) : SettingsEditor<IonaRunConfiguration>() {

    private val fileField = TextFieldWithBrowseButton().apply {
        addBrowseFolderListener(
            project,
            FileChooserDescriptorFactory.createSingleFileDescriptor("iona")
                .withTitle("Select Iona File"),
        )
    }

    private val argumentsField = RawCommandLineEditor()

    private val panel: JComponent = FormBuilder.createFormBuilder()
        .addLabeledComponent("Iona file:", fileField)
        .addLabeledComponent("Program arguments:", argumentsField)
        .panel

    override fun resetEditorFrom(config: IonaRunConfiguration) {
        fileField.text = config.filePath.orEmpty()
        argumentsField.text = config.programArguments.orEmpty()
    }

    override fun applyEditorTo(config: IonaRunConfiguration) {
        config.filePath = fileField.text
        config.programArguments = argumentsField.text
    }

    override fun createEditor(): JComponent = panel
}
