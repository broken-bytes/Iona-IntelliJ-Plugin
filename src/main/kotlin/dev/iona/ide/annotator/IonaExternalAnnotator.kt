package dev.iona.ide.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.ExternalAnnotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile
import dev.iona.toolchain.diagnostics.IonaDiagnostic
import dev.iona.toolchain.IonaToolchain
import dev.iona.toolchain.diagnostics.DiagnosticsRunner

class IonaExternalAnnotator : ExternalAnnotator<IonaExternalAnnotator.CollectedInfo, List<IonaDiagnostic>>() {

    data class CollectedInfo(val text: String)

    override fun collectInformation(file: PsiFile, editor: Editor, hasErrors: Boolean): CollectedInfo =
        CollectedInfo(file.text)

    override fun collectInformation(file: PsiFile): CollectedInfo = CollectedInfo(file.text)

    override fun doAnnotate(info: CollectedInfo): List<IonaDiagnostic> {
        val run = DiagnosticsRunner.runDiagnostics(info.text)
        when {
            run.command == null -> LOG.warn(
                "Iona diagnostics disabled: set -D${IonaToolchain.PROP_COMMAND}, " +
                    "\$${IonaToolchain.ENV_COMMAND}, or write the command to ~/${IonaToolchain.CONFIG_FILE}"
            )
            run.timedOut -> LOG.warn("Iona diagnostics timed out: ${run.command.joinToString(" ")}")
            run.error != null -> LOG.warn(
                "Iona diagnostics run failed (is the compiler path / dotnet on PATH?)", run.error
            )
            else -> LOG.warn(
                "Iona diagnostics: exit=${run.exitCode} found=${run.diagnostics.size}; " +
                    "compiler output:\n${run.output}"
            )
        }
        return run.diagnostics
    }

    override fun apply(file: PsiFile, diagnostics: List<IonaDiagnostic>, holder: AnnotationHolder) {
        val document = file.viewProvider.document ?: return
        for (d in diagnostics) {
            val severity = if (d.isError) HighlightSeverity.ERROR else HighlightSeverity.WARNING
            holder.newAnnotation(severity, "[${d.code}] ${d.message}")
                .range(d.toTextRange(document))
                .create()
        }
    }

    companion object {
        private val LOG = Logger.getInstance(IonaExternalAnnotator::class.java)
    }
}
