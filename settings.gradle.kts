rootProject.name = "lib-input"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

// Which family a pad's buttons belong to, from SDL's ids (plain JVM).
include(":pads")
