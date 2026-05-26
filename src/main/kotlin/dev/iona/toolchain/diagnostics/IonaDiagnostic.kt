package dev.iona.toolchain.diagnostics

import com.google.gson.Gson

data class IonaDiagnostic(
    val severity: String,
    val code: String,
    val message: String,
    val file: String,
    val lineStart: Int,
    val lineEnd: Int,
    val columnStart: Int,
    val columnEnd: Int,
) {
    val isError: Boolean get() = severity.equals("error", ignoreCase = true)

    companion object {
        private data class Payload(val schema: String?, val diagnostics: List<IonaDiagnostic>?)

        fun parseAll(json: String): List<IonaDiagnostic> {
            if (json.isBlank()) return emptyList()
            return try {
                Gson().fromJson(json, Payload::class.java)?.diagnostics ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}