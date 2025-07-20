plugins {
    `kotlin-dsl`
}

group = "com.zanoapps.runique_new.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)


}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "zanoapps.runiquenew"
            implementationClass = "AndroidApplicationConventionPlugin"
        }


        register("androidApplicationCompose") {
            id = "zanoapps.runiquenew.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }

        register("androidLibrary") {
            id = "zanoapps.runiquenew.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "zanoapps.runiquenew.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }


        register("androidFeatureUi") {
            id = "zanoapps.runiquenew.feature.ui"
            implementationClass = "AndroidFeatureUiConventionPlugin"
        }

        register("androidRoom") {
            id = "zanoapps.runiquenew.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }

        register("androidDynamicFeature") {
            id = "runiquenew.android.dynamic.feature"
            implementationClass = "AndroidDynamicFeatureConventionPlugin"
        }
        register("jvmLibray") {
            id = "zanoapps.runiquenew.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }

        register("jvmKtor") {
            id = "zanoapps.runiquenew.jvm.ktor"
            implementationClass = "JvmKtorConventionPlugin"
        }
    }
}