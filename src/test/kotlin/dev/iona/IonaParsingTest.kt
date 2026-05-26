package dev.iona

import com.intellij.psi.PsiErrorElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.ParsingTestCase
import dev.iona.lang.parser.IonaParserDefinition
import java.io.File

class IonaParsingTest : ParsingTestCase("", "iona", IonaParserDefinition()) {

    override fun getTestDataPath(): String = "src/test/testData"

    private fun assertParsesCleanly(fileName: String) {
        val text = File(getTestDataPath(), fileName).readText()
        val file = createPsiFile(fileName.removeSuffix(".iona"), text)
        val errors = PsiTreeUtil.collectElementsOfType(file, PsiErrorElement::class.java)
        if (errors.isNotEmpty()) {
            val report = errors.joinToString("\n") { e ->
                "  @${e.textRange}: ${e.errorDescription}  near «${e.text.take(40)}»"
            }
            fail("$fileName produced ${errors.size} parse error(s):\n$report")
        }
    }

    fun testCalculator() = assertParsesCleanly("calculator.iona")
    fun testAsync() = assertParsesCleanly("async_test.iona")
    fun testSleep() = assertParsesCleanly("sleep_test.iona")
}
