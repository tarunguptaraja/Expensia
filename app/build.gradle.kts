plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.tarunguptaraja.expensia"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.tarunguptaraja.expensia"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }

    flavorDimensions += "environment" // define your flavor dimension

    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".debug"
            versionCode = 1000
            versionName = "1.0"
            buildConfigField("String", "BASE_URL", "\"https://expensia-gnlg.onrender.com/api/v1/\"")
            resValue("string", "app_name", "Dev Expensia")
        }

        create("stage") {
            dimension = "environment"
            applicationIdSuffix = ".stage"
            versionCode = 1000
            versionName = "1.0"
            buildConfigField("String", "BASE_URL", "\"https://expensia-gnlg.onrender.com/api/v1/\"")
            resValue("string", "app_name", "Expensia Stage")
        }

        create("production") {
            dimension = "environment"
            versionCode = 1000
            versionName = "1.0"
            buildConfigField("String", "BASE_URL", "\"https://expensia-gnlg.onrender.com/api/v1/\"")
            resValue("string", "app_name", "Expensia")
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
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

    implementation(libs.vita)
    implementation(libs.intuit.sdp.android)
//    implementation (libs.pinview)
    implementation(libs.circleimageview)
    implementation(libs.lottie)
    implementation(libs.kodein.di.generic.jvm)
    implementation(libs.kodein.di.framework.android.x)

    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.converter.simplexml)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
}