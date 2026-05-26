import org.jetbrains.grammarkit.tasks.GenerateLexerTask
import org.jetbrains.grammarkit.tasks.GenerateParserTask
import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.intellij.platform")
    id("org.jetbrains.changelog")
    id("org.jetbrains.grammarkit")
}

group = providers.gradleProperty("pluginGroup").get()
version = providers.gradleProperty("pluginVersion").get()

repositories {
    mavenCentral()
    maven("https://redirector.kotlinlang.org/maven/dev")
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    testImplementation("junit:junit:4.13.2")

    intellijPlatform {
        intellijIdea(providers.gradleProperty("platformVersion"))
        testFramework(TestFrameworkType.Platform)
    }
}

tasks.test {
    useJUnit()
}

kotlin {
    jvmToolchain(21)
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = providers.gradleProperty("pluginSinceBuild")
            untilBuild = providers.gradleProperty("pluginUntilBuild")
        }
    }
}

// Use the local IDE cos using the builtin one runs on Rosetta
intellijPlatformTesting {
    runIde {
        register("runLocalIde") {
            localPath = file(System.getProperty("user.home") + "/Applications/IntelliJ IDEA.app")
        }
    }
}

val lexerOutDir = layout.buildDirectory.dir("generated/lexer")
val flexFile = file("src/main/kotlin/dev/iona/lang/lexer/Iona.flex")

val generateIonaLexer by tasks.registering(GenerateLexerTask::class) {
    sourceFile.set(flexFile)
    targetOutputDir.set(lexerOutDir.map { it.dir("dev/iona/lang/lexer") })
    purgeOldFiles.set(true)
}

val parserOutDir = layout.buildDirectory.dir("generated/parser")
val generateIonaParser by tasks.registering(GenerateParserTask::class) {
    sourceFile.set(file("src/main/kotlin/dev/iona/lang/parser/Iona.bnf"))
    targetRootOutputDir.set(parserOutDir)
    pathToParser.set("dev/iona/lang/parser/IonaParser.java")
    pathToPsiRoot.set("dev/iona/lang/psi")
    purgeOldFiles.set(true)
}

sourceSets["main"].java.srcDir(lexerOutDir)
sourceSets["main"].java.srcDir(parserOutDir)

tasks.named("compileKotlin") {
    dependsOn(generateIonaLexer)
    dependsOn(generateIonaParser)
}
