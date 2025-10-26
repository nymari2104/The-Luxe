plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
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
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)

    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    implementation(libs.recyclerview)
    implementation(platform(libs.firebase.bom))
    implementation(libs.play.services.base) // Hoặc phiên bản mới hơn
    implementation(libs.google.firebase.messaging)
    implementation(libs.firebase.analytics)
    implementation(libs.gson)
    implementation(libs.firebase.messaging)
    
    // Glide for image loading
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
    
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}