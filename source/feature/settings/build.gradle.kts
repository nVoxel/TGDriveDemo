plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.decompose)
            implementation(libs.decompose.extensions)
            implementation(libs.mvikotlin)
            implementation(libs.mvikotlin.extensions.coroutines)
            implementation(libs.essenty.lifecycle)
            implementation(libs.essenty.lifecycle.coroutines)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.koin.core)

            implementation(projects.source.feature.favoritesApi)
            implementation(projects.source.feature.mainApi)
            implementation(projects.source.utils)
        }
    }
}

android {
    namespace = "com.voxeldev.tgdrive.settings"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
