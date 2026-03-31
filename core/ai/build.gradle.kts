plugins {
    id("travel.android.library")
    id("travel.android.hilt")
    id("travel.android.jacoco")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.travel.companion.core.ai"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.ai)
    // TODO: firebase-ai-ondevice API stabilize olunca eklenecek
    // implementation(libs.firebase.ai.ondevice)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)

    testImplementation(project(":core:testing"))
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.mockk)
}
