package dev.iona

import com.intellij.openapi.editor.impl.DocumentImpl
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import dev.iona.ide.annotator.toTextRange
import dev.iona.toolchain.diagnostics.IonaDiagnostic

class IonaDiagnosticTest : BasePlatformTestCase() {

    private val sampleJson = """
        {
          "schema": "iona-diagnostics/v1",
          "diagnostics": [
            { "severity":"error","code":"C0004","message":"Top level definition `Console` is not defined",
              "file":"t.iona","lineStart":2,"lineEnd":2,"columnStart":5,"columnEnd":12 }
          ]
        }
    """.trimIndent()

    fun testParsesDiagnostics() {
        val diags = IonaDiagnostic.parseAll(sampleJson)
        assertEquals(1, diags.size)
        val d = diags[0]
        assertEquals("C0004", d.code)
        assertTrue(d.isError)
        assertEquals(2, d.lineStart)
        assertEquals(5, d.columnStart)
        assertEquals(12, d.columnEnd)
    }

    fun testBlankAndGarbageAreEmpty() {
        assertTrue(IonaDiagnostic.parseAll("").isEmpty())
        assertTrue(IonaDiagnostic.parseAll("not json").isEmpty())
    }

    fun testMapsSpanToOffsets() {
        val doc = DocumentImpl("module M\n    Console\n")
        val d = IonaDiagnostic.parseAll(sampleJson).first()
        val range = d.toTextRange(doc)
        assertEquals(13, range.startOffset)
        assertEquals(20, range.endOffset)
        assertEquals("Console", doc.getText(range))
    }
}
