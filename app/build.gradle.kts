import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    android.buildFeatures.buildConfig = true
    namespace = "com.example.progetto_ingegneria_software"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.example.progetto_ingegneria_software"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val p = Properties()
        p.load(project.rootProject.file("local.properties").inputStream())
        manifestPlaceholders["MAPS_API_KEY"] = p.getProperty("MAPS_API_KEY")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.firebase.auth)
    implementation(libs.annotation)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.database)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.google.firebase.firestore)
    implementation(libs.okhttp)
    implementation (libs.gson)
    implementation(libs.glide.v4160)
    implementation(libs.firebase.storage)
    implementation(libs.play.services.maps)
    //implementation(libs.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.cardview)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
}

