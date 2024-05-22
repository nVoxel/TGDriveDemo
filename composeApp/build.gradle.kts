import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.compose)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.activity.compose)
            implementation(libs.ui.tooling.preview)

            implementation(libs.decompose)
            implementation(libs.mvikotlin)
            implementation(libs.mvikotlin.main)
            implementation(libs.koin)

            implementation(projects.source.telegram.tdlibKtx)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(libs.decompose)
            implementation(libs.decompose.extensions)

            implementation(compose.material3)
            implementation(libs.material3)
            implementation(libs.material.icons.extended)

            implementation(projects.source.data.database)
            implementation(projects.source.data.network)
            implementation(projects.source.root)
            implementation(projects.source.feature.authApi)
            implementation(projects.source.feature.auth)
            implementation(projects.source.feature.favorites)
            implementation(projects.source.feature.favoritesApi)
            implementation(projects.source.feature.files)
            implementation(projects.source.feature.filesApi)
            implementation(projects.source.feature.main)
            implementation(projects.source.feature.mainApi)
            implementation(projects.source.feature.settings)
            implementation(projects.source.utils)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)

            implementation(libs.decompose)
            implementation(libs.mvikotlin)
            implementation(libs.mvikotlin.main)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            implementation(projects.source.telegram.tdlibKtx)
        }
    }
}

android {
    namespace = "com.voxeldev.tgdrive"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.voxeldev.tgdrive"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dependencies {
        debugImplementation(libs.ui.tooling)
    }
}

compose.desktop {
    application {
        mainClass = "com.voxeldev.tgdrive.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.voxeldev.tgdrive"
            packageVersion = "1.0.0"
        }
    }
}
