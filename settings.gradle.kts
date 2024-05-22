rootProject.name = "TGDrive"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(":composeApp")
include(":shared")
include(":source:root")
include(":source:feature:favorites")
include(":source:feature:files")
include(":source:feature:settings")
include(":source:feature:favorites-api")
include(":source:feature:files-api")
include(":source:feature:settings-api")
include(":source:feature:main")
include(":source:feature:auth")
include(":source:feature:auth-api")
include(":source:data:network")
include(":source:telegram:tdlib-android")
include(":source:telegram:tdlib-desktop")
include(":source:telegram:tdlib-ktx")
include(":source:utils")
include(":source:feature:main-api")
include(":source:data:mappers")
include(":source:data:database")
