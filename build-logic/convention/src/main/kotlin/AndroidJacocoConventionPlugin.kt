import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport

/**
 * JaCoCo test coverage raporlamasi icin convention plugin.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
class AndroidJacocoConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("jacoco")

            extensions.configure<JacocoPluginExtension> {
                toolVersion = "0.8.12"
            }

            afterEvaluate {
                val debugTree = fileTree("${layout.buildDirectory.get()}/tmp/kotlin-classes/debug") {
                    exclude(
                        "**/R.class",
                        "**/R\$*.class",
                        "**/BuildConfig.*",
                        "**/*_Hilt*.*",
                        "**/Hilt_*.*",
                        "**/*_Factory.*",
                        "**/*_MembersInjector.*",
                        "**/*Module_*.*",
                        "**/*_Impl.*",
                        "**/di/**",
                    )
                }

                val execData = fileTree(layout.buildDirectory) {
                    include("jacoco/testDebugUnitTest.exec")
                }

                tasks.register("jacocoTestReport", JacocoReport::class.java) {
                    dependsOn("testDebugUnitTest")

                    reports {
                        xml.required.set(true)
                        html.required.set(true)
                        csv.required.set(false)
                    }

                    classDirectories.setFrom(debugTree)
                    sourceDirectories.setFrom("${projectDir}/src/main/kotlin")
                    executionData.setFrom(execData)
                }

                tasks.register("jacocoTestCoverageVerification", JacocoCoverageVerification::class.java) {
                    dependsOn("jacocoTestReport")

                    classDirectories.setFrom(debugTree)
                    executionData.setFrom(execData)

                    violationRules {
                        rule {
                            limit {
                                counter = "LINE"
                                value = "COVEREDRATIO"
                                minimum = "0.70".toBigDecimal()
                            }
                        }
                    }
                }
            }
        }
    }
}
