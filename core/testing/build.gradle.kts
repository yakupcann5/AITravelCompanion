plugins {
    id("travel.android.library")
    id("travel.android.hilt")
}

android {
    namespace = "com.travel.companion.core.testing"
}

dependencies {
    api(libs.junit)
    api(libs.kotlinx.coroutines.test)
    api(libs.mockk)
    api(libs.turbine)
    api(libs.hilt.android.testing)
    api(libs.junit.ext)
    api(libs.espresso.core)
    api(libs.compose.ui.test.junit4)
    debugApi(libs.compose.ui.test.manifest)

    implementation(project(":core:model"))
    implementation(project(":core:common"))
}
