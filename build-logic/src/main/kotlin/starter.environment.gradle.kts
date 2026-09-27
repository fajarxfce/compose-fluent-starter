import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import tasks.GenerateEnvironment

plugins { id("starter.kmp") }

val generateEnvironment =
    tasks.register<GenerateEnvironment>("generateEnvironment") {
        environment.set(providers.gradleProperty("appEnvironment").orElse("dev"))
        outputDirectory.set(layout.buildDirectory.dir("generated/environment/kotlin"))
    }

kotlin.sourceSets.commonMain { kotlin.srcDir(generateEnvironment.flatMap { it.outputDirectory }) }

tasks.withType<KotlinCompilationTask<*>>().configureEach { dependsOn(generateEnvironment) }
