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
            implementation(projects.source.feature.authApi)
            implementation(projects.source.feature.filesApi)
            implementation(projects.source.feature.mainApi)

            implementation(libs.koin.core)
            api(libs.kotlinx.coroutines.core)
        }

        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)

            implementation(projects.source.data.mappers)
            api(projects.source.telegram.tdlibAndroid)
        }

        jvmMain.dependencies {
            implementation(libs.androidx.core.ktx)

            implementation(projects.source.data.mappers)
            api(projects.source.telegram.tdlibDesktop)
        }
    }
}

android {
    namespace = "kotlinx.telegram"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    sourceSets.getByName("main") {
        jniLibs.srcDir("src/androidMain/libs")
    }
}
