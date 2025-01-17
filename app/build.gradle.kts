plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
//    id("com.google.devtools.ksp")
    //kapt
    id("kotlin-kapt")
}

android {
    namespace = "com.akashsoam.productsapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.akashsoam.productsapp"
        minSdk = 26
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
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

//    recyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Navigation Components
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.5")


//    Lifecycle Scopes
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")


    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

//glide for loading image, we could use picasso as well
    implementation("com.github.bumptech.glide:glide:4.11.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.11.0")

//    Retrofit

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//Gson converter for Retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//    Logging interceptor for Retrofit
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")


    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
// LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
// Lifecycle runtime
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")

    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

//    room
    implementation("androidx.room:room-runtime:2.6.1")
//    kapt("androidx.room:room-compiler:2.6.1")
//    ksp("androidx.room:room-compiler:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    //splash api
    implementation("androidx.core:core-splashscreen:1.0.1")


    //lottie animation
    implementation("com.airbnb.android:lottie:6.6.2")

    //work manager for background tasks
//    implementation("androidx.work:work-runtime-ktx:2.10.0")
    //koin for dependency injection
    implementation("io.insert-koin:koin-android:3.2.0")
//    implementation("io.insert-koin:koin-androidx-scope:3.2.0")
//    implementation("io.insert-koin:koin-androidx-viewmodel:3.2.0")
    implementation("io.insert-koin:koin-androidx-workmanager:3.2.0")
//    implementation("io.insert-koin:koin-androidx-fragment:3.2.0")
//    implementation("io.insert-koin:koin-androidx-compose:3.2.0")


}


