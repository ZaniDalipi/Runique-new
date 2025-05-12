import com.android.build.gradle.LibraryExtension
import com.zanoapps.convention.ExtensionType
import com.zanoapps.convention.configBuildTypes
import com.zanoapps.convention.configureAndroidCompose
import com.zanoapps.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin


class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {

            pluginManager.run {
                apply("zanoapps.runiquenew.library")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)

        }
    }
}