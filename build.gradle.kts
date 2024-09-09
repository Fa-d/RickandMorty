
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt.android) apply false
    kotlin("plugin.serialization").version(libs.versions.kotlin).apply(false)
    id("androidx.room").version("2.6.1").apply(false)
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose) apply false
}