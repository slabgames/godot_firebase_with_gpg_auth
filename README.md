# godot_firebase_with_gpg_auth

# How To Setup 
  1. Add google-services.json  in PROJECT_DIR/android/build
  2. add dependencies in buld gradle -> PROJECT_DIR/android/build

    implementation(platform("com.google.firebase:firebase-bom:32.7.3"))
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.beust:klaxon:5.5")
    

  3. change androidGradlePlugin to 7.3.0 in ->  PROJECT_DIR/android/build/config.gradle
     ```
     ext.versions = [
      androidGradlePlugin: '7.3.0',
      compileSdk         : 33,
      minSdk             : 19, // Also update 'platform/android/export/export_plugin.cpp#DEFAULT_MIN_SDK_VERSION'
      targetSdk          : 33, // Also update 'platform/android/export/export_plugin.cpp#DEFAULT_TARGET_SDK_VERSION'
      buildTools         : '33.0.2',
      kotlinVersion      : '1.7.0',
      fragmentVersion    : '1.3.6',
      nexusPublishVersion: '1.1.0',
      javaVersion        : 11,
      ndkVersion         : '23.2.8568313' // Also update 'platform/android/detect.py#get_ndk_version' when this is updated.
     ]
     ```
  4. add gmsGoogle in -> PROJECT_DIR/android/build/config.gradle
     ```
     ext.libraries = [
       androidGradlePlugin: "com.android.tools.build:gradle:$versions.androidGradlePlugin",
      kotlinGradlePlugin : "org.jetbrains.kotlin:kotlin-gradle-plugin:$versions.kotlinVersion",
      kotlinStdLib       : "org.jetbrains.kotlin:kotlin-stdlib:$versions.kotlinVersion",
      androidxFragment   : "androidx.fragment:fragment:$versions.fragmentVersion",
      gmsGoogle          : "com.google.gms:google-services:4.3.8",
     ]
 5. add  id 'com.google.gms.google-services' version '4.3.8' apply false in -> PROJECT_DIR/android/build/setting.gradle
    ```
    pluginManagement {
    apply from: 'config.gradle'

    plugins {
        id 'com.android.application' version versions.androidGradlePlugin
        id 'org.jetbrains.kotlin.android' version versions.kotlinVersion
        id 'com.google.gms.google-services' version '4.3.8' apply false
    }
    repositories {
        gradlePluginPortal()
        google()
    }
    }
