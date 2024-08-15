plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    //id("com.google.gms.google-services")

}
project.ext.set("version", "1.0.0")
android {
    namespace = "com.boxgramer.boxfirebaselib"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    libraryVariants.all{
        outputs.all{
            packageLibraryProvider {
                archiveFileName.set("BoxFirebase-${project.ext.get("version")}.relese.aar")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildToolsVersion = "34.0.0"
    ndkVersion = "27.0.11902837 rc2"
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-analytics:22.0.2")
    implementation("com.google.firebase:firebase-firestore:25.0.0")
    implementation("com.google.firebase:firebase-auth-ktx:23.0.0")
    implementation("com.beust:klaxon:5.5")
    compileOnly(files("libs/godot-lib.3.5.3.stable.release.aar"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

}