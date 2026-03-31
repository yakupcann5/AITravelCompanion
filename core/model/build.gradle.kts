plugins {
    id("travel.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.travel.companion.core.model"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    api(libs.navigation3.runtime)
}
