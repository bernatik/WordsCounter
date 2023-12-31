plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.alexbernat.wordscounter"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.alexbernat.wordscounter"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val androidXCore = "1.12.0"
    val fragmentVersion = "1.6.1"
    val appcompatVersion = "1.6.1"
    val lifecycleVersion = "2.6.2"
    val koinVersion = "3.5.0"
    val constraintLayoutVersion = "2.1.4"
    val recyclerViewVersion = "1.3.1"

    val junitVersion = "4.13.2"
    val mockitoKotlinVersion = "4.1.0"
    val coroutinesVersion = "1.7.3"
    val assertionsVersion = "1.1.5"

    implementation("androidx.core:core-ktx:$androidXCore")
    implementation("androidx.appcompat:appcompat:$appcompatVersion")
    implementation("androidx.fragment:fragment-ktx:$fragmentVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")

    implementation("androidx.recyclerview:recyclerview:$recyclerViewVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")

    implementation("io.insert-koin:koin-android:$koinVersion")

    testImplementation("junit:junit:$junitVersion")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
    testImplementation("com.google.truth:truth:$assertionsVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
}