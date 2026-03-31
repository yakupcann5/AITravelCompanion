plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "travel.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = "travel.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "travel.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidFeature") {
            id = "travel.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidJacoco") {
            id = "travel.android.jacoco"
            implementationClass = "AndroidJacocoConventionPlugin"
        }
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.hilt.gradlePlugin)
}
