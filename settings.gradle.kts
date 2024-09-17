pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Rick and Morty"
include(":app")
include(":feature:locationui")
include(":core:coreall")
include(":core:database")
include(":core:datastore")
include(":core:network")
