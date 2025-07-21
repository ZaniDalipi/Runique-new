plugins {
    alias(libs.plugins.runique.android.dynamic.feature)
}
android {
    namespace = "com.zanoapps.analytics.analytic_feature"
}

dependencies {
    implementation(project(":app"))

    api(projects.analytics.presentation)
    implementation(projects.analytics.domain)
    implementation(projects.analytics.domain)
}