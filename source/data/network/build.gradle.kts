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
        androidMain.dependencies {
            implementation(libs.koin)

            implementation(projects.source.data.mappers)
        }

        commonMain.dependencies {
            implementation(libs.koin.core)

            implementation(projects.source.feature.authApi)
            implementation(projects.source.feature.filesApi)
            implementation(projects.source.feature.mainApi)
            implementation(projects.source.telegram.tdlibKtx)
            implementation(projects.source.utils)
        }

        jvmMain.dependencies {
            implementation(projects.source.data.mappers)
        }
    }
}

android {
    namespace = "com.voxeldev.tgdrive.network"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
