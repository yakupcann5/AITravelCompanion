plugins {
    id("travel.android.feature")
    id("travel.android.jacoco")
}

android {
    namespace = "com.travel.companion.feature.explore"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:ai"))

    implementation(libs.coil.compose)

    testImplementation(project(":core:testing"))
}
