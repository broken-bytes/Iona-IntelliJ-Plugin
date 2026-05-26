package dev.iona.ide.annotator

import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import dev.iona.toolchain.IonaDiagnostic

fun IonaDiagnostic.toTextRange(doc: Document): TextRange {
    val lastLine = (doc.lineCount - 1).coerceAtLeast(0)
    val sLine = (lineStart - 1).coerceIn(0, lastLine)
    val eLine = (lineEnd - 1).coerceIn(0, lastLine)
    val start = (doc.getLineStartOffset(sLine) + (columnStart - 1).coerceAtLeast(0))
        .coerceIn(0, doc.textLength)
    var end = (doc.getLineStartOffset(eLine) + (columnEnd - 1).coerceAtLeast(0))
        .coerceIn(0, doc.textLength)
    if (end <= start) end = (start + 1).coerceAtMost(doc.textLength)
    return TextRange(start, end)
}
