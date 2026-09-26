import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.navigation.safeargs)
    id("kotlin-parcelize")
    alias(libs.plugins.google.services)
}

val localProperties = Properties().apply {
    rootProject.file("app/appconfig.properties").inputStream().use { inputStream ->
        load(inputStream)
    }
}

android {
    signingConfigs {
        getByName("debug") {
            storeFile = file(localProperties.getProperty("DEBUG_FILE_PATH"))
            storePassword = localProperties.getProperty("DEBUG_PASSWORD")
            keyAlias = localProperties.getProperty("DEBUG_ALIAS")
            keyPassword = localProperties.getProperty("DEBUG_PASSWORD")
        }
        create("release") {
            storeFile = file(localProperties.getProperty("RELEASE_FILE_PATH"))
            storePassword = localProperties.getProperty("RELEASE_PASSWORD")
            keyAlias = localProperties.getProperty("RELEASE_ALIAS")
            keyPassword = localProperties.getProperty("RELEASE_PASSWORD")
        }
    }
    namespace = "com.example.photoprofile"
    compileSdk {
        version = release(36)
    }

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.example.photoprofile"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "MY_SECRET_API_KEY", "\"${localProperties.getProperty("MY_SECRET_API_KEY")}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Api
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    // Image
    implementation(libs.glide)
    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    // Koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)

    // Firebase & Identity
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services)
    implementation(libs.googleid)

    // Test
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.arch.core.testing)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.mockk.android)
}
