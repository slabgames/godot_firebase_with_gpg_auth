
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    //id("com.google.gms.google-services")
//    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}



project.ext.set("version", "1.0.2")
android {
    namespace = "com.boxgramer.boxfirebaselib"
    compileSdk = 37

    defaultConfig {
        minSdk = 23
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        compose = true
    }

//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.1"
//    }



    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    libraryVariants.all{
        outputs.all{
            packageLibraryProvider {
                archiveFileName.set("BoxFirebase-${project.ext.get("version")}.release.aar")
            }
        }
    }
    composeCompiler {
        reportsDestination = layout.buildDirectory.dir("compose_compiler")
//        stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
    }

//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
    kotlin {
//        jvmToolchain {
//            languageVersion.set(JavaLanguageVersion.of("1.8"))
//        }
        // Or shorter:
        jvmToolchain(8)
        // For example:
        jvmToolchain(17)
    }
    buildToolsVersion = "34.0.0"
    ndkVersion = "27.0.11902837 rc2"
}



dependencies {
    implementation("androidx.core:core-ktx:1.19.0")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.14.0")
    implementation(platform("androidx.compose:compose-bom:2026.06.01"))
    implementation("androidx.compose.runtime:runtime")
    implementation(platform("com.google.firebase:firebase-bom:34.17.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-inappmessaging")
    implementation("com.google.firebase:firebase-inappmessaging-display")
    implementation("com.beust:klaxon:5.6")
    compileOnly(files("libs/godot-lib.3.6.2.stable.release.aar"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
}