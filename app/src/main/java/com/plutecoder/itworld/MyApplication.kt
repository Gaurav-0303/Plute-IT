package com.plutecoder.itworld

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.database.FirebaseDatabase

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Enable Firebase Realtime Database disk persistence
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        // Enable Crashlytics crash reporting
        FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = true
    }
}