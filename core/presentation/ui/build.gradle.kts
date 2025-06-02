plugins {
    alias(libs.plugins.runique.android.library.compose)
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
}

android {
    namespace = "com.zanoapps.core.presentation.ui"


}

dependencies {

    dependencies {
        implementation ("com.google.accompanist:accompanist-pager:0.28.0")
        implementation ("com.google.accompanist:accompanist-pager-indicators:0.28.0")


            // Collapsing toolbar
            implementation ("com.google.accompanist:accompanist-collapsing-toolbar:0.28.0")

            // Horizontal pager and pager indicators
            implementation ("com.google.accompanist:accompanist-pager:0.28.0")
            implementation ("com.google.accompanist:accompanist-pager-indicators:0.28.0")

        implementation ("com.google.accompanist:accompanist-navigation-animation:0.36.0")


            implementation("com.google.android.material:material:1.12.0") // Use latest version


    }


    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    implementation(projects.core.domain)
    implementation(projects.core.presentation.designsystem)

}