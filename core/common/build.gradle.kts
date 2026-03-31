plugins {
    id("travel.android.library")
    id("travel.android.hilt")
}

android {
    namespace = "com.travel.companion.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.core.ktx)
}
