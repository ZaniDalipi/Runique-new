plugins {

    alias(libs.plugins.runique.android.features.ui)
}

android {
    namespace = "com.zanoapps.run.presentation"
}

dependencies {

    implementation(libs.coil.compose)
    implementation(libs.google.maps.android.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.timber)

    implementation(projects.core.domain)
    implementation(projects.run.domain)

}