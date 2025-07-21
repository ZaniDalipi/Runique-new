plugins {
    alias(libs.plugins.runique.android.features.ui)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.zanoapps.analytics.presentation"

}

dependencies {

   implementation(projects.analytics.domain)
}