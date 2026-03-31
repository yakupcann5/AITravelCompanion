plugins {
    id("travel.android.feature")
    id("travel.android.jacoco")
}

android {
    namespace = "com.travel.companion.feature.translator"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:ai"))

    testImplementation(project(":core:testing"))
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}
