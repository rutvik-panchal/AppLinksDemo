package com.rutvik.apps.webview.service

import android.app.Service
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.rutvik.apps.webview.MainActivity
import com.rutvik.apps.webview.Utils

class KeyBoardMonitorService : Service() {

    companion object {
        const val TAG = "KeyBoardMonitorService"
    }

    private lateinit var clipboardManager: ClipboardManager

    private var onPrimaryClipChangedListener: ClipboardManager.OnPrimaryClipChangedListener = ClipboardManager.OnPrimaryClipChangedListener {
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

    override fun onCreate() {
        super.onCreate()

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

}