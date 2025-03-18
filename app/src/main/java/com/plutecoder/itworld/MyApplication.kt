package com.plutecoder.itworld

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Enable Firebase Realtime Database disk persistence
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}