
plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.biofit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.biofit"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("mysql:mysql-connector-java:8.0.27")
    implementation("com.android.volley:volley:1.2.1")
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui) // Añade esta línea para Volley
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.github.Gruzer:simple-gauge-android:0.3.1")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.airbnb.android:lottie:6.0.0")


}
