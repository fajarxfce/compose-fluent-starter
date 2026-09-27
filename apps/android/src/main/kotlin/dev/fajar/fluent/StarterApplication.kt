package dev.fajar.fluent

import android.app.Application
import dev.fajar.starter.app.di.createAppContainer
import dev.fajar.starter.common.config.AppEnvironment
import dev.fajar.starter.storage.AndroidPreferenceStore

class StarterApplication : Application() {
    val environment = AppEnvironment.entries.single { it.id == BuildConfig.APP_ENVIRONMENT }
    val container by lazy { createAppContainer(AndroidPreferenceStore(this), environment) }
}
