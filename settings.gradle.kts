pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TravelCompanion"

include(":app")

// Core modules
include(":core:model")
include(":core:common")
include(":core:designsystem")
include(":core:ui")
include(":core:data")
include(":core:database")
include(":core:network")
include(":core:ai")
include(":core:testing")

// Feature modules
include(":feature:explore")
include(":feature:planner")
include(":feature:translator")
include(":feature:gallery")
