plugins {
    id("travel.android.library")
    id("travel.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.travel.companion.core.network"
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)
    implementation(libs.kotlinx.serialization.json)
}
