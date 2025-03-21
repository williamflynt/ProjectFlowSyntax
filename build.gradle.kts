import com.github.gradle.node.npm.task.NpmTask
import com.github.gradle.node.npm.task.NpxTask

buildscript {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.0.10"
    id("org.jetbrains.intellij") version "1.17.4"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
    id("io.gitlab.arturbosch.detekt") version "1.23.6"
    id("com.github.node-gradle.node") version "7.1.0"
}

group = "projectflowsyntax"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.graalvm.sdk:graal-sdk:22.3.3")
    implementation("org.graalvm.js:js:22.3.3")
    implementation("org.graalvm.js:js-scriptengine:22.3.3")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
}

node {
    download = true
    nodeProjectDir = file("project-flow-syntax")
}

// Configure Gradle IntelliJ Plugin
intellij {
    version.set("2023.2.5")
    type.set("IC") // Target IDE Platform
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
}

// Task to execute npm build for GraalJS.
tasks.register<NpmTask>("npmBuildGraalJs") {
    args = listOf("run", "build:graaljs")
}

// Make the build task depend on the npm build
tasks.named("processResources") {
    dependsOn("npmBuildGraalJs")
}

// Copy the transpiled JavaScript to resources
tasks.register<Copy>("copyGraalJsToResources") {
    from("./project-flow-syntax/out/graaljs/parser.js")
    into("src/main/resources/")
    dependsOn("npmBuildGraalJs")
}

tasks.named("processResources") {
    dependsOn("copyGraalJsToResources")
}
