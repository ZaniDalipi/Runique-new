plugins {
    alias(libs.plugins.runique.android.application.compose)
    alias(libs.plugins.runique.jvm.ktor)

    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.zanoapps.runiquenew"
    kotlinOptions {
        jvmTarget = "11"
    }



    dependencies {


        dependencies {
            implementation("org.jetbrains:annotations:23.0.0") {
                exclude(group = "com.intellij", module = "annotations")
            }
            // OR if it's a transitive dependency
            configurations.all {
                exclude(group = "com.intellij", module = "annotations")
            }
        }


        // Coil
        implementation(libs.coil.compose)

        // Compose
        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.material.icons.extended)
        implementation(libs.androidx.compose.ui)
        implementation(libs.androidx.compose.ui.graphics)
        implementation(libs.androidx.compose.ui.tooling.preview)
        implementation(libs.androidx.compose.material3)
        implementation(libs.androidx.lifecycle.viewmodel.compose)
        implementation(libs.androidx.lifecycle.runtime.compose)
        implementation(libs.androidx.navigation.compose)

        // Core
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)

        // Crypto
        implementation(libs.androidx.security.crypto.ktx)

        // this kind of implementation is that makes the code that is included from this library to be available
        // to modules that depend on this module
        api(libs.core)

        implementation(libs.bundles.koin)

        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(libs.androidx.compose.ui.test.junit4)
        debugImplementation(libs.androidx.compose.ui.tooling)
        debugImplementation(libs.androidx.compose.ui.test.manifest)

        // Location
        implementation(libs.google.android.gms.play.services.location)

        // Splash screen
        implementation(libs.androidx.core.splashscreen)

        // Timber
        implementation(libs.timber)


        implementation(projects.core.presentation.designsystem)
        implementation(projects.core.presentation.ui)
        implementation(projects.core.domain)
        implementation(projects.core.data)
        implementation(projects.core.database)

        implementation(projects.auth.presentation)
        implementation(projects.auth.domain)
        implementation(projects.auth.data)

        implementation(projects.run.presentation)
        implementation(projects.run.domain)
        implementation(projects.run.data)
        implementation(projects.run.location)
        implementation(projects.run.network)
    }
}
dependencies {
    implementation(libs.androidx.core.ktx)
}
