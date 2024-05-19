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

rootProject.name = "Recipe"
include(":app")
include(":core")
include(":data")
include(":domain")
include(":features")
include(":features:saved_recipe")
include(":features:see_recipe_context")
