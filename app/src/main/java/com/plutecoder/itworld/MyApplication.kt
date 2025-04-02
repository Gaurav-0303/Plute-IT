package com.plutecoder.itworld

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Enable Firebase Realtime Database disk persistence
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        // Enable Crashlytics crash reporting
        FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = true

        //notification
        FirebaseMessaging.getInstance().subscribeToTopic("pluteit-updates")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Notification", "Subscribed to topic: pluteit-updates")
                }
            }
    }
}