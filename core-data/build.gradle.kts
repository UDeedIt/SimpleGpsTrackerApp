plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
}
//plugins {
//    alias(libs.plugins.android.library)
//    alias(libs.plugins.android.built.in1.kotlin)
//    alias(libs.plugins.kotlin.serialization)
//}

android {
    namespace = "pro.udeedit.demo.simplegpstracker.core.data"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

//    kotlinOptions {
//        jvmTarget = "17"
//    }

    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    testImplementation(libs.junit)

    // Ktor client
    implementation("io.ktor:ktor-client-android:3.0.0")
    implementation("io.ktor:ktor-client-content-negotiation:3.0.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.0")
    implementation("io.ktor:ktor-client-logging:3.0.0")

    // Kotlin Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    // DataStore (later)
    implementation(libs.androidx.datastore.preferences)


    implementation(project(":core-domain"))
}