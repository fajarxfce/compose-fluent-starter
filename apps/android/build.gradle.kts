plugins {
    id("starter.android.application")
    id("starter.android.flavors")
    id("starter.firebase")
}

dependencies {
    implementation(projects.apps.shared)
    implementation(libs.android.activity)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
}
