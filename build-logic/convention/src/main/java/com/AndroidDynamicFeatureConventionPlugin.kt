package com

import com.android.build.api.dsl.ApplicationExtension
import com.zanoapps.convention.ExtensionType
import com.zanoapps.convention.configBuildTypes
import com.zanoapps.convention.libs
import configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure


class AndroidDynamicFeatureConventionPlugin : Plugin<Project> {

    override fun apply(projectTarget: Project) {
        projectTarget.run {
            // applying the plugins that we want to together with this convention plugin
            pluginManager.run {
                apply("com.android.application")
                //apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    applicationId = libs.findVersion("projectApplicationId").get().toString()
                    targetSdk = libs.findVersion("projectTargetSdkVersion").get().toString().toInt()

                    versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
                    versionName = libs.findVersion("projectVersionName").get().toString()
                }

                configureKotlinAndroid(this)

                configBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.APPLICATION
                )
            }
        }

    }
}