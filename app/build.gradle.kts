import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
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
        // Enable buildConfig to access the generated field
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Api
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    // Image
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
    //Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation("io.mockk:mockk:1.14.7") // Replace with the latest version
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    // MockK for Android tests
    androidTestImplementation("io.mockk:mockk-android:1.13.10")
}