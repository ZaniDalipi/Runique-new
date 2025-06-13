plugins {
    alias(libs.plugins.runique.android.library)
    alias(libs.plugins.runique.jvm.ktor)
}

android {
    namespace = "com.zanoapps.core.data"
}

dependencies {

    implementation(libs.bundles.koin)
    implementation(libs.timber)

    implementation(projects.core.domain)
    implementation(projects.core.database)
}