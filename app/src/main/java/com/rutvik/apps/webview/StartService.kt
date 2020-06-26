package com.rutvik.apps.webview

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.rutvik.apps.webview.service.KeyBoardMonitorService

class StartService : BroadcastReceiver() {

    companion object {
        const val TAG = "StartService"
    }

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Intent(context, KeyBoardMonitorService::class.java).also {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Log.d(TAG, "Starting the service in >= API 26")
                    context.startForegroundService(it)
                    return
                }
                Log.d(TAG,"Starting the service in < API 26")
                context.startService(it)
            }
        }
    }
}