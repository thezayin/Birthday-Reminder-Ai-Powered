pluginManagement {
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
        maven { url = uri("https://jitpack.io") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "BirthdayReminder"
include(":app")
include(":core:values")
include(":setting")
include(":core:framework")
include(":core:components")
include(":splash")
include(":home:presentation")
include(":home:domain")
include(":home:data")
include(":calculator:data")
include(":calculator:domain")
include(":calculator:presentation")
include(":databases:domain")
include(":databases:data")
include(":databases:presentation")
include(":add-birthday")
include(":saved-birthdays")
include(":gift-ideas:domain")
include(":gift-ideas:data")
include(":gift-ideas:presentation")
include(":core:analytics")
