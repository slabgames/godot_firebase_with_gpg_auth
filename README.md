
# godot_firebase_with_gpg_auth

# How To Setup 
  1. Add google-services.json  in PROJECT_DIR/android/build
  2. add dependencies in build.gradle -> PROJECT_DIR/android/build
      ```
        implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
        implementation("com.google.firebase:firebase-analytics")
        implementation("com.google.firebase:firebase-firestore")
        implementation("com.google.firebase:firebase-auth")
        implementation("com.beust:klaxon:5.5")
        ```

  3. add  id 'com.google.gms.google-services' in PROJECT_DIR/android/build/build.gradle
    
    ```
    plugins {
        ...

        id 'com.google.gms.google-services'
    }
    ```

  4. change androidGradlePlugin to 7.4.2 in ->  PROJECT_DIR/android/build/config.gradle
     ```
     ext.versions = [
      androidGradlePlugin: '7.4.2',
      compileSdk         : 34,
      minSdk             : 19, // Also update 'platform/android/export/export_plugin.cpp#DEFAULT_MIN_SDK_VERSION'
      targetSdk          : 34, // Also update 'platform/android/export/export_plugin.cpp#DEFAULT_TARGET_SDK_VERSION'
      buildTools         : '33.0.2',
      kotlinVersion      : '1.7.0',
      fragmentVersion    : '1.3.6',
      nexusPublishVersion: '1.1.0',
      javaVersion        : 11,
      ndkVersion         : '23.2.8568313' // Also update 'platform/android/detect.py#get_ndk_version' when this is updated.
     ]
     ```
  5. add gmsGoogle in -> PROJECT_DIR/android/build/config.gradle
     ```
     ext.libraries = [
       androidGradlePlugin: "com.android.tools.build:gradle:$versions.androidGradlePlugin",
      kotlinGradlePlugin : "org.jetbrains.kotlin:kotlin-gradle-plugin:$versions.kotlinVersion",
      kotlinStdLib       : "org.jetbrains.kotlin:kotlin-stdlib:$versions.kotlinVersion",
      androidxFragment   : "androidx.fragment:fragment:$versions.fragmentVersion",
      gmsGoogle          : "com.google.gms:google-services:4.4.2",
     ]
     ```
  6. add  id 'com.google.gms.google-services' version '4.4.2' apply false in -> PROJECT_DIR/android/build/setting.gradle
      ```
      pluginManagement {
      apply from: 'config.gradle'

      plugins {
          id 'com.android.application' version versions.androidGradlePlugin
          id 'org.jetbrains.kotlin.android' version versions.kotlinVersion
          id 'com.google.gms.google-services' version '4.4.2' apply false
      }
      repositories {
          gradlePluginPortal()
          google()
      }
      }
      ```
      
  7. set the version of gradle in PROJECT_DIR/gradle/wrapper/gradle-wrapper.properties
    ```
    distributionUrl=https\://services.gradle.org/distributions/gradle-7.5-bin.zip
    ```

  Note: Please make sure internet permission is allowed

6. add share 
```
// android/src/main/AndroidManifest.xml
<application
    ...>
    <provider
        android:name="androidx.core.content.FileProvider"
        android:authorities="${applicationId}"
        android:exported="false"
        android:grantUriPermissions="true"
        tools:replace="android:authorities"
        >

            <meta-data
    android:name="android.support.FILE_PROVIDER_PATHS"
    android:resource="@xml/file_paths"
    tools:replace="android:resource" />
    </provider>
</application>

// android/src/main/res/xml/file_paths.xml
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    <files-path name="files" path="." />
</paths>

```
