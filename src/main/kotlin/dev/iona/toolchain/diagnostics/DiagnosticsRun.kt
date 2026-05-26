package dev.iona.toolchain.diagnostics

data class DiagnosticsRun(
    val command: List<String>?,
    val diagnostics: List<IonaDiagnostic>,
    val exitCode: Int? = null,
    val output: String = "",
    val timedOut: Boolean = false,
    val error: Throwable? = null,
)
