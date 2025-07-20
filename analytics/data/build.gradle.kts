plugins {
    alias(libs.plugins.runique.android.library)
}

android {
    namespace = "com.zanoapps.analytics.data"

}

dependencies {

    implementation(libs.kotlinx.coroutines.core)

    // project module references
    implementation(projects.core.database)
    implementation(projects.core.domain)
    implementation(projects.analytics.domain)

}