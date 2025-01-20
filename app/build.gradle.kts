plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
  //  kotlin("kapt")
}

android {
    namespace = "com.mahmutgunduz.westy"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mahmutgunduz.westy"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // AndroidX ve temel kütüphaneler
    implementation(libs.androidx.core.ktx.v1100)
    implementation(libs.androidx.appcompat.v161)
    implementation(libs.material)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.constraintlayout.v214)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)

    // Picasso ve resim yükleme
    implementation(libs.picasso)

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx.v253)
    implementation(libs.androidx.navigation.ui.ktx.v253)

    // ViewPager2 ve diğer UI bileşenleri
    implementation("androidx.viewpager2:viewpager2:1.1.0")


    // Room Database Bağımlılıkları
    implementation ("androidx.room:room-runtime:2.6.1")

    implementation("androidx.room:room-rxjava3:2.6.1") // RxJava3 desteği

    // RxJava
    implementation ("io.reactivex.rxjava3:rxjava:3.1.5")
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.2")

    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1") // KSP için room-compiler
}
