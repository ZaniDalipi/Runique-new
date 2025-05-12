plugins {

    alias(libs.plugins.runique.android.features.ui)
}

android {
    namespace = "com.zanoapps.auth.presentation"

}

dependencies {
    implementation(projects.auth.domain)
    implementation(projects.core.domain)

}