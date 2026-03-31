import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Feature modulleri icin ortak bagimlilik ve plugin yapilandirmasi.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("travel.android.library")
            pluginManager.apply("travel.android.compose")
            pluginManager.apply("travel.android.hilt")

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                add("implementation", project(":core:model"))
                add("implementation", project(":core:common"))
                add("implementation", project(":core:designsystem"))
                add("implementation", project(":core:ui"))
                add("implementation", libs.findLibrary("hilt-navigation-compose").get())
                add("implementation", libs.findLibrary("lifecycle-runtime-compose").get())
                add("implementation", libs.findLibrary("lifecycle-viewmodel-compose").get())
                add("implementation", libs.findLibrary("kotlinx-coroutines-core").get())

                add("testImplementation", project(":core:testing"))
            }
        }
    }
}
