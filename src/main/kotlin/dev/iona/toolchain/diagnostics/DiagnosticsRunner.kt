package dev.iona.toolchain.diagnostics

import dev.iona.toolchain.IonaToolchain
import java.nio.file.Files
import java.util.concurrent.TimeUnit

object DiagnosticsRunner {
    private const val TIMEOUT_SECONDS = 30L

    fun runDiagnostics(sourceText: String): DiagnosticsRun {
        val command = IonaToolchain.resolveCommand() ?: return DiagnosticsRun(command = null, diagnostics = emptyList())

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
