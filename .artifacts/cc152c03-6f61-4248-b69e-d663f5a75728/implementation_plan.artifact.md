# Fix build errors in BoxFirebase.kt

The project currently fails to build due to unresolved references in `BoxFirebase.kt`. The Firebase KTX extensions for Analytics and Auth are not being correctly resolved, and the `logEvent` call with a trailing lambda is failing.

## Proposed Changes

### [boxfirebaselib](file:///D:/work/godot_firebase_with_gpg_auth/boxfirebaselib)

#### [MODIFY] [BoxFirebase.kt](file:///D:/work/godot_firebase_with_gpg_auth/boxfirebaselib/src/main/java/com/boxgramer/boxfirebaselib/BoxFirebase.kt)

- **Fix Firebase initialization**: Replace `Firebase.analytics` and `Firebase.auth` with standard `FirebaseAnalytics.getInstance(activity)` and `FirebaseAuth.getInstance()` calls. This avoids unresolved reference issues with KTX extensions that are failing to import.
- **Fix Analytics logging**: Update `analitycLogCustom` to use a `Bundle` for parameters instead of the failing `logEvent` trailing lambda.
- **Remove conflicting stub**: Delete the `private fun FirebaseAnalytics?.logEvent(name: String, params: Any) {}` stub at the bottom of the file, which conflicts with the real Firebase API.
- **Cleanup**:
    - Add `import android.os.Bundle`.
    - Remove redundant semicolons.
    - Fix minor warnings (e.g., redundant Samuel constructor, unused parameters).
    - Ensure all imports are valid and used.

## Verification Plan

### Automated Tests
- Run `gradlew :boxfirebaselib:assembleDebug` to verify the library builds successfully.

### Manual Verification
- None required as these are build-time fixes.
