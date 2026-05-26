package dev.iona.toolchain

import java.io.File

object IonaToolchain {
    const val ENV_COMMAND = "IONA_DIAGNOSTICS_CMD"
    const val PROP_COMMAND = "iona.diagnostics.cmd"
    const val CONFIG_FILE = ".iona-diagnostics"

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
}
