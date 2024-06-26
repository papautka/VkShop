plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.uteev.vkshop"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.uteev.vkshop"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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

    // RxJava2 Retrofit2 Dependencies
    implementation(libs.androidx.rxandroid)
    implementation(libs.androidx.rxjava)
    implementation(libs.androidx.retrofit)
    implementation(libs.androidx.retrofit.converter.gson)
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.9.0")


    // ViewModel and LiveData
    implementation(libs.androidx.livedata.ktx)
    implementation(libs.androidx.viewmodel.ktx)
    kapt(libs.androidx.lifycycle.compiler)
    implementation(libs.androidx.lifycycle.streams)

    // Room Dependencies
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    kapt(libs.androidx.room.compiler.get())
    implementation(libs.androidx.room.ktx)

    //Picasso
    implementation(libs.picasso)

    // Dagger
    val dagger_version = "2.50"
    implementation("com.google.dagger:dagger:$dagger_version")
    implementation("com.google.dagger:dagger-android:$dagger_version")
    implementation("com.google.dagger:dagger-android-support:$dagger_version")
    kapt("com.google.dagger:dagger-android-processor:$dagger_version")
    kapt("com.google.dagger:dagger-compiler:$dagger_version")
}