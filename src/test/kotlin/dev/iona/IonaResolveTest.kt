package dev.iona

import com.intellij.testFramework.ParsingTestCase
import dev.iona.lang.parser.IonaParserDefinition
import dev.iona.lang.psi.IonaNamedElement

class IonaResolveTest : ParsingTestCase("", "iona", IonaParserDefinition()) {

    override fun getTestDataPath(): String = "src/test/testData"

    fun testLocalVarResolves() {
        val src = """
            module M

            public fn main() -> Void {
                var x = 1
                var y = x + 2
            }
        """.trimIndent()
        val file = createPsiFile("t", src)

        val useOffset = src.indexOf("x + 2")
        val ref = file.findReferenceAt(useOffset)
        assertNotNull("expected a reference at the x usage", ref)

        val resolved = ref!!.resolve()
        assertNotNull("x should resolve to its declaration", resolved)
        assertTrue("resolved target should be a named element", resolved is IonaNamedElement)
        assertEquals("x", (resolved as IonaNamedElement).name)
        assertTrue("should resolve to the 'var x' declaration", resolved.text.startsWith("var x"))
    }

    fun testParamResolves() {
        val src = """
            module M

            public fn add(a: Int32) -> Int32 {
                return a + 1
            }
        """.trimIndent()
        val file = createPsiFile("t", src)

        val useOffset = src.indexOf("a + 1")
        val resolved = file.findReferenceAt(useOffset)?.resolve()
        assertNotNull("a should resolve to the parameter", resolved)
        assertEquals("a", (resolved as IonaNamedElement).name)
    }
}
