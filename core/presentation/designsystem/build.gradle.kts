plugins {
    alias(libs.plugins.runique.android.library.compose)
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"


}

android {
    namespace = "com.zanoapps.core.presentation.designsystem"


}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.graphics)
    debugImplementation(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.material3)
}