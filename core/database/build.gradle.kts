plugins {
    id("travel.android.library")
    id("travel.android.hilt")
}

android {
    namespace = "com.travel.companion.core.database"
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
}
