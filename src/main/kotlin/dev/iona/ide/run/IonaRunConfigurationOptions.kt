package dev.iona.ide.run

import com.intellij.execution.configurations.RunConfigurationOptions
import com.intellij.openapi.components.StoredProperty

class IonaRunConfigurationOptions : RunConfigurationOptions() {

    private val filePathProp: StoredProperty<String?> =
        string("").provideDelegate(this, "filePath")

    private val programArgumentsProp: StoredProperty<String?> =
        string("").provideDelegate(this, "programArguments")

    var filePath: String?
        get() = filePathProp.getValue(this)
        set(value) = filePathProp.setValue(this, value)

    var programArguments: String?
        get() = programArgumentsProp.getValue(this)
        set(value) = programArgumentsProp.setValue(this, value)
}
