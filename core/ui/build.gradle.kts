plugins {
    id("travel.android.library")
    id("travel.android.compose")
}

android {
    namespace = "com.travel.companion.core.ui"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))

    implementation(libs.coil.compose)
}
