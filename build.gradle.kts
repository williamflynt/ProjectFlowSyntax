import org.gradle.internal.os.OperatingSystem

buildscript {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.0.10"
    id("org.jetbrains.intellij") version "1.17.4"
    id("io.github.tree-sitter.ktreesitter-plugin") version "0.22.4"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
    id("io.gitlab.arturbosch.detekt") version "1.23.6"
}

group = "projectflowsyntax"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.tree-sitter:ktreesitter:0.22.4")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
}

// Configure Gradle IntelliJ Plugin
intellij {
    version.set("2023.2.5")
    type.set("IC") // Target IDE Platform
}

configurations {
    // For `ktreesitter-plugin`.
    grammar {
        baseDir = File(projectDir, "grammar")
        grammarName = "project_flow_syntax"
        className = "ProjectFlowSyntax"
        packageName = "projectflowsyntax"
        files =
            arrayOf(
                baseDir.get().resolve("grammar/src/parser.c"),
            )
    }
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    buildSearchableOptions {
        enabled = false
    }

    runIde {
        autoReloadPlugins = true
    }

    patchPluginXml {
        sinceBuild.set("232")
        untilBuild.set("242.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

    // Variables for compiling our JNI treesitter wrapper.
    val grammarSrcDir = File(projectDir, "grammar/src")
    val treeSitterVendorApiDir = File(projectDir, "src/main/vendor/tree_sitter/lib/include")
    val jniSrcDir = File(projectDir, "src/main/resources/jni")
    val jniOutDir = File(projectDir, "libs")

    // Detecting operating system to compile a library for our grammar.
    val os: OperatingSystem = OperatingSystem.current()
    val sharedLibExtension =
        when {
            os.isWindows -> ".dll"
            os.isMacOsX -> ".dylib"
            else -> ".so"
        }
    val javaIncludesDir =
        when {
            os.isWindows -> "windows"
            os.isMacOsX -> "darwin"
            else -> "linux"
        }

    /*
    Build steps to get `tree_sitter` source dependency.
     */
    register<Exec>("cloneTreesitter") {
        val targetDir = File("src/main/vendor/tree_sitter")
        commandLine =
            if (targetDir.exists()) {
                listOf("echo", "Directory 'src/main/vendor/tree_sitter' already exists. Skipping git clone.")
            } else {
                listOf("git", "clone", "https://github.com/tree-sitter/tree-sitter.git", targetDir.absolutePath)
            }
        group = "build"
        description = "Clone treesitter repo."
    }

    named("compileKotlin") {
        dependsOn("cloneTreesitter")
    }

    /*
    Build steps that involve JNI compile.
     */

    register<Exec>("treesitterGenerate") {
        group = "build"
        description = "Generate files using treesitter."
        workingDir = File("${projectDir.absolutePath}/grammar")
        commandLine = listOf("tree-sitter", "generate")
    }

    register<Exec>("compileTreeSitterGrammar") {
        group = "build"
        description = "Compile the Tree-sitter grammar into a shared library."
        dependsOn("generateGrammarFiles")
        commandLine =
            listOf(
                "gcc",
                "-shared",
                "-o",
                "$jniOutDir/libproject_flow_syntax$sharedLibExtension",
                "-fPIC",
                "-I",
                grammarSrcDir.absolutePath,
                "${grammarSrcDir.absolutePath}/parser.c",
            )
        outputs.dir(jniOutDir) // Make sure `outputDir` exists.
        outputs.upToDateWhen { false } // No cache.
    }

    named("compileTreeSitterGrammar") {
        dependsOn("treesitterGenerate")
    }

    register<Exec>("compileJniWrapper") {
        group = "build"
        description = "Compile the JNI wrapper into a shared library."
        dependsOn("compileTreeSitterGrammar")
        commandLine =
            listOf(
                "gcc",
                "-shared",
                "-o",
                "$jniOutDir/libtreesitter_wrapper$sharedLibExtension",
                "-fPIC",
                "-I",
                "${System.getProperty("java.home")}/include",
                "-I",
                "${System.getProperty("java.home")}/include/$javaIncludesDir",
                "-I",
                "$treeSitterVendorApiDir",
                "-I",
                grammarSrcDir.absolutePath,
                "${jniSrcDir.absolutePath}/treesitter_wrapper.cpp",
                "-L",
                jniOutDir.absolutePath,
                "-lproject_flow_syntax",
            )
        outputs.dir(jniOutDir)
        outputs.upToDateWhen { false } // No cache.
    }

    // This puts our compile libraries in a place accessible to the bundled plugin.
    prepareSandbox {
        from(jniOutDir.absolutePath) {
            into("${intellij.pluginName.get()}/lib/")
        }
    }

    named("processResources") {
        dependsOn("compileJniWrapper")
    }

    // Build our treesitter JNI wrapper before the Kotlin source.
    named("build") {
        dependsOn("compileJniWrapper")
    }
}
