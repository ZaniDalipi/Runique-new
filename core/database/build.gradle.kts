plugins {
    alias(libs.plugins.runique.android.library)
    alias(libs.plugins.runique.android.room)
}

android {
    namespace = "com.zanoapps.core.database"

}

dependencies {

  implementation(libs.org.mongodb.bson)

    implementation(projects.core.domain)
}