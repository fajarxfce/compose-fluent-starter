plugins {
    id("starter.android.application")
    id("starter.android.flavors")
}

dependencies {
    implementation(projects.apps.shared)
    implementation(libs.android.activity)
}
