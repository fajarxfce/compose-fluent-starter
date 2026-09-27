package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*

@CacheableTask
abstract class GenerateEnvironment : DefaultTask() {
    @get:Input abstract val environment: Property<String>
    @get:OutputDirectory abstract val outputDirectory: DirectoryProperty

    @TaskAction
    fun generate() {
        val value = environment.get()
        require(value in setOf("dev", "staging", "prod")) {
            "appEnvironment must be dev, staging, or prod."
        }
        val output =
            outputDirectory.file("dev/fajar/starter/common/config/BuildEnvironment.kt").get().asFile
        output.parentFile.mkdirs()
        output.writeText(
            """
            package dev.fajar.starter.common.config
            object BuildEnvironment {
                val current: AppEnvironment = AppEnvironment.${value.replaceFirstChar { it.uppercase() }}
            }
        """
                .trimIndent() + "\n"
        )
    }
}
