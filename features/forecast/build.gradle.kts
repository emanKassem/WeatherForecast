plugins {
    id("com.android.library")
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinx.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.golyv.forecast"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
        targetSdk = 35

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(project(":core:uicomponents"))
    implementation(project(":core:network"))
    implementation(project(":core:utils"))
    implementation(project(":core:database"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.compose.hilt.navigation)
    implementation(libs.androidx.material3)
    implementation(libs.compose.lifecycle.runtime)
    implementation(libs.compose.permissionsAccompainst)
    implementation(libs.google.play.services.location)
    implementation(libs.hilt.android)
    implementation(libs.kotlix.coroutinesCore)
    implementation(libs.kotlix.coroutinesAndroid)
    implementation(libs.kotlix.coroutinesTest)
    ksp(libs.hilt.compiler)
    implementation(libs.google.gson)
    implementation(libs.squareup.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.coil)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}