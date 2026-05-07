# Implementation Plan: Fix Compilation Errors and App Architecture

## Objective
Resolve Room compilation errors, fix table name mismatches, and initialize the database to ensure the app compiles and runs without crashing on the main thread.

## Key Files & Context
- `gradle/libs.versions.toml`
- `app/build.gradle.kts`
- `app/src/main/java/com/example/powerlifter_companion/data/AppDatabase.kt`
- `app/src/main/java/com/example/powerlifter_companion/data/*Dao.kt`
- `app/src/main/java/com/example/powerlifter_companion/PowerlifterApplication.kt` (New)
- `app/src/main/AndroidManifest.xml`
- `app/src/main/java/com/example/powerlifter_companion/MainActivity.kt`

## Implementation Steps

### 1. Fix Dependencies (Room 2.6.1 + KSP)
- Update `gradle/libs.versions.toml` to include the `ksp` plugin and set Room version to `2.6.1`.
- Update `app/build.gradle.kts` to apply the `ksp` plugin and use `ksp` instead of `annotationProcessor` for the Room compiler. Ensure `androidx.room:room-ktx` is included.

### 2. Fix Room Database Setup
- Update `AppDatabase.kt` to use `androidx.room.Database` instead of `androidx.room3`.
- Remove the `exportSchema = false` if not necessary, but ensure it compiles.

### 3. Fix DAO Compilation Errors
- **`TrainingWeekDao.kt`**: Fix `deleteWeeksForBlock` to return `Unit` instead of `Flow<TrainingBlocks>`. Ensure `getTrainingWeek` is `suspend`.
- **`ExerciseDao.kt`**: Add `Exercise?` return type to `getExerciseById` and make it `suspend`. Fix the query in `getExerciseByDefinitionId` to target `exercise_definition` instead of `exercise_definitions`.
- **`ExerciseDefinitionDao.kt`**: Ensure the table name in all `@Query` statements exactly matches `exercise_definition`.

### 4. Provide Database Initialization
- Create `PowerlifterApplication.kt` extending `Application`.
- Instantiate `AppDatabase` lazily using `Room.databaseBuilder`.
- Expose the repositories (`ExerciseRepository`, `TrainingRepository`) from the Application class so ViewModels can use them.
- Update `AndroidManifest.xml` to declare `.PowerlifterApplication` as the app's `android:name`.

### 5. Update ViewModels and MainActivity
- Ensure `TrainingViewModel` and `ExerciseViewModel` are created using a `ViewModelProvider.Factory` that takes the repositories from the Application class.
- Pass the ViewModels to the compose UI in `MainActivity`.

## Verification & Testing
- Run `./gradlew assembleDebug` to ensure all Room compilation errors are resolved.
- Inspect the logcat on app startup to verify the database builds correctly and the main thread is not blocked (no "Davey!" or ANR logs).