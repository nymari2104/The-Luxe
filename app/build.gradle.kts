plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.theluxe"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.theluxe"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField(
                "String",
                "VNPAY_HASH_SECRET",
                "\"EALUPA094PJ9QGI2M5QXZBFR9PYKKEMG\""
            )
            buildConfigField(
                "String",
                "VNPAY_TMN_CODE",
                "\"VSMM8XHX\""
            )
        }

        getByName("debug") {
            buildConfigField(
                "String",
                "VNPAY_HASH_SECRET",
                "\"EALUPA094PJ9QGI2M5QXZBFR9PYKKEMG\""
            )
            buildConfigField(
                "String",
                "VNPAY_TMN_CODE",
                "\"VSMM8XHX\""
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.9.4")
    implementation("androidx.lifecycle:lifecycle-livedata:2.9.4")

    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    implementation("androidx.recyclerview:recyclerview:1.4.0")
//    implementation("com.paypal.checkout:android-sdk:1.3.0")
    implementation(platform("com.google.firebase:firebase-bom:34.4.0"))
    implementation("com.google.android.gms:play-services-base:18.9.0") // Hoặc phiên bản mới hơn
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation(libs.firebase.messaging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}