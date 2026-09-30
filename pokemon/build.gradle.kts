plugins {
    id("com.android.library")
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.hilt.android.gradle) apply false
}
android {
    namespace = "com.lexives.pokedex.pokemon"
    compileSdk {
        version = release(37)
    }
    defaultConfig {
        minSdk = 34
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}
dependencies {
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(project(":core:data"))
    implementation(project(":core:model"))
}
