import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Android Compose ozelliklerini etkinlestiren Gradle convention plugin.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            extensions.configure<LibraryExtension> {
                buildFeatures {
                    compose = true
                }
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation", platform(libs.findLibrary("compose-bom").get()))
                add("implementation", libs.findLibrary("compose-ui-tooling-preview").get())
                add("debugImplementation", libs.findLibrary("compose-ui-tooling").get())
            }
        }
    }
}
