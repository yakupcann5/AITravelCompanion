plugins {
    id("travel.android.feature")
    id("travel.android.jacoco")
}

android {
    namespace = "com.travel.companion.feature.gallery"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:ai"))

    implementation(libs.coil.compose)

    testImplementation(project(":core:testing"))
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}
