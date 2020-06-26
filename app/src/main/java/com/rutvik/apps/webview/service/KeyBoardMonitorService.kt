package com.rutvik.apps.webview.service

import android.app.*
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.rutvik.apps.webview.MainActivity
import com.rutvik.apps.webview.R
import com.rutvik.apps.webview.Utils

@Suppress("DEPRECATION")
class KeyBoardMonitorService : Service() {

    companion object {
        const val TAG = "KeyBoardMonitorService"
    }

    private lateinit var clipboardManager: ClipboardManager

    private var onPrimaryClipChangedListener = ClipboardManager.OnPrimaryClipChangedListener {
        Log.d(TAG, "Primary TExt change detected")
        val link = clipboardManager.primaryClip?.getItemAt(0)?.text.toString()
        if (Utils.isURL(link)) {
            startActivity(
                Intent(applicationContext, MainActivity::class.java).apply {
                    putExtra("copiedLink", link)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            )
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        val notification = createNotification()
        startForeground(1, notification)
        clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.addPrimaryClipChangedListener(onPrimaryClipChangedListener)
        Log.d(TAG, "$TAG started running")
    }

    override fun onDestroy() {
        super.onDestroy()
        clipboardManager.removePrimaryClipChangedListener(onPrimaryClipChangedListener)
        Log.d(TAG, "$TAG stopped")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotification(): Notification {
        val notificationChannelId = "WEBVIEW SERVICE CHANNEL"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                notificationChannelId,
                "URLCopy Service notifications channel",
                NotificationManager.IMPORTANCE_HIGH
            ).let {
                it.description = "URLCopy Service channel"
                it.enableLights(true)
                it.lightColor = Color.RED
                it.enableVibration(true)
                it.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                it
            }
            notificationManager.createNotificationChannel(channel)
        }

        val pendingIntent: PendingIntent = Intent(this, MainActivity::class.java).let { notificationIntent ->
            PendingIntent.getActivity(this, 0, notificationIntent, 0)
        }

        val builder: Notification.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) Notification.Builder(
            this,
            notificationChannelId
        ) else Notification.Builder(this)

        return builder
            .setContentTitle("URLCopy Service")
            .setContentText("This is your favorite service working")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setTicker("Ticker text")
            .setPriority(Notification.PRIORITY_HIGH)
            .build()
    }

}