package dev.iona.toolchain

import java.io.File
import java.nio.file.Files
import java.util.concurrent.TimeUnit

object IonaToolchain {
    const val ENV_COMMAND = "IONA_DIAGNOSTICS_CMD"
    const val PROP_COMMAND = "iona.diagnostics.cmd"
    const val CONFIG_FILE = ".iona-diagnostics"

    private const val TIMEOUT_SECONDS = 30L

    fun resolveCommand(): List<String>? {
        val raw = System.getProperty(PROP_COMMAND)?.takeIf { it.isNotBlank() }
            ?: System.getenv(ENV_COMMAND)?.takeIf { it.isNotBlank() }
            ?: readConfigFile()
        if (raw.isNullOrBlank()) return null
        return raw.trim().split(Regex("\\s+"))
    }

    private fun readConfigFile(): String? = runCatching {
        File(System.getProperty("user.home"), CONFIG_FILE)
            .takeIf { it.isFile }
            ?.useLines { it.firstOrNull(String::isNotBlank) }
    }.getOrNull()

    fun runDiagnostics(sourceText: String): DiagnosticsRun {
        val command = resolveCommand() ?: return DiagnosticsRun(command = null, diagnostics = emptyList())

        val tmpSource = Files.createTempFile("iona-annot-", ".iona")
        val tmpJson = Files.createTempFile("iona-diag-", ".json")
        try {
            Files.writeString(tmpSource, sourceText)
            val argv = command + listOf(tmpSource.toString(), "--emit-diagnostics-json", tmpJson.toString())

            val process = ProcessBuilder(argv).redirectErrorStream(true).start()
            if (!process.waitFor(TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                process.destroyForcibly()
                return DiagnosticsRun(command, emptyList(), timedOut = true)
            }
            val output = process.inputStream.bufferedReader().readText()
            val json = Files.readString(tmpJson)
            return DiagnosticsRun(command, IonaDiagnostic.parseAll(json), exitCode = process.exitValue(), output = output)
        } catch (e: Exception) {
            return DiagnosticsRun(command, emptyList(), error = e)
        } finally {
            runCatching { Files.deleteIfExists(tmpSource) }
            runCatching { Files.deleteIfExists(tmpJson) }
        }
    }
}

data class DiagnosticsRun(
    val command: List<String>?,
    val diagnostics: List<IonaDiagnostic>,
    val exitCode: Int? = null,
    val output: String = "",
    val timedOut: Boolean = false,
    val error: Throwable? = null,
)
