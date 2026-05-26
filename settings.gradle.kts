import org.jetbrains.intellij.platform.gradle.extensions.intellijPlatform

rootProject.name = "iona-intellij-plugin"

pluginManagement {
    plugins {
        id("org.jetbrains.kotlin.jvm") version "2.4.0-RC"
        id("org.jetbrains.changelog") version "2.5.0"
        id("org.jetbrains.grammarkit") version "2023.3.0.3"
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    id("org.jetbrains.intellij.platform.settings") version "2.16.0"
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        mavenCentral()

        intellijPlatform {
            defaultRepositories()
        }
    }
}
