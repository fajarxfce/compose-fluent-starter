plugins { id("starter.android.library") }

kotlin.sourceSets {
    getByName("commonMain").dependencies {
        api(projects.core.sync.domain)
        api(projects.core.sync.data)
        implementation(libs.coroutines.core)
    }
    getByName("androidMain").dependencies { api(libs.work.runtime) }
    getByName("commonTest").dependencies { implementation(libs.coroutines.test) }
}
