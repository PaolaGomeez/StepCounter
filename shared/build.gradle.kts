plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "edu.sdgku.stepcounter.shared"
    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        minSdk = 30
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    api(platform("com.google.firebase:firebase-bom:34.16.0"))
    api("com.google.firebase:firebase-firestore")
}