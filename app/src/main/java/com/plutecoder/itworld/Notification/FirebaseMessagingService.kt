package com.plutecoder.itworld.Notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.app.PendingIntent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.plutecoder.itworld.R
import com.plutecoder.itworld.adapters.CategoryItemGridAdapter
import com.plutecoder.itworld.views.CategoryItemGridActivity
import com.plutecoder.itworld.views.CategoryItemListActivity
import com.plutecoder.itworld.views.RoadmapActivity
import com.plutecoder.itworld.views.TechHubActivity

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let {
            // When the notification is received, call the showNotification function
            showNotification(it.title ?: "PluteIT", it.body ?: "New update available!", remoteMessage.data)
        }
    }

    private fun showNotification(title: String, message: String, data: Map<String, String>) {
        val channelId = "pluteit_channel"
        val notificationId = System.currentTimeMillis().toInt()

        // Create notification manager
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "PluteIT Updates", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        // Get the uid (item-specific ID) and ui_type (category UI type) from the data
        val itemId = data["itemId"]  // Item-specific identifier (for item notifications)

        Log.d("Gaurav", itemId.toString())

        // Create an intent based on whether it's an item or category
        val intent: Intent = if (itemId != null) {
            // If there's a uid, it's an item notification
            Log.d("Gaurav", itemId.toString())

            Intent(this, RoadmapActivity::class.java).apply {
                putExtra("itemId", itemId) // Pass the item uid to RoadmapActivity
            }
        } else {
            // If there's no uid, it's a category notification, check ui_type for category display
            val categoryId = data["categoryId"]  // Assuming the category name is sent
            val uiType = data["Ui_type"] // UI Type for category notifications
            when (uiType) {
                "GRID" -> {
                    // Open GridCategoryActivity for GRID type
                    Intent(this, CategoryItemGridActivity::class.java).apply {
                        putExtra("categoryId", categoryId) // Pass the category name
                    }
                }
                "LIST" -> {
                    // Open ListCategoryActivity for LIST type
                    Intent(this, CategoryItemListActivity::class.java).apply {
                        putExtra("categoryId", categoryId) // Pass the category name
                    }
                }
                "POST" -> {
                    // Open PostCategoryActivity for POST type
                    Intent(this, TechHubActivity::class.java).apply {
                        putExtra("categoryId", categoryId) // Pass the category name
                    }
                }

                else -> {
                    // Open PostCategoryActivity for POST type
                    Intent(this, TechHubActivity::class.java).apply {
                        putExtra("categoryId", categoryId) // Pass the category name
                    }
                }
            }
        }

        // Set the intent to open when the notification is clicked
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE // Updated flag usage
        )

        Log.d("Gaurav", "PendingIntent created with intent: $intent")




        // Create notification builder
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent) // Set the content intent to open the activity on click
            .build()

        // 🔹 Check Notification Permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                Log.e("FirebaseMessagingService", "Notification permission not granted")
                return // Stop execution if permission is not granted
            }
        }

        // Notify the user
        NotificationManagerCompat.from(this).notify(notificationId, notification)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("Gaurav", "New token: $token")
    }
}
