plugins {
    id("travel.android.library")
    id("travel.android.hilt")
    id("travel.android.jacoco")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.travel.companion.core.data"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:database"))

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.datastore.preferences)

    testImplementation(project(":core:testing"))
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
}
