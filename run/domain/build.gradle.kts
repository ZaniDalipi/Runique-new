plugins {
    alias(libs.plugins.runique.jvm.libary)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(projects.core.domain)
}
