package com.rutvik.apps.webview

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rutvik.apps.webview.service.KeyBoardMonitorService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var url = "https://www.google.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Intent(this, KeyBoardMonitorService::class.java).also {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(it)
            } else {
                startService(it)
            }
        }

        if (intent.getCharSequenceArrayExtra(Intent.EXTRA_PROCESS_TEXT) != null) {
            url = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)!!.toString()
        } else if (intent.getStringExtra("copiedLink") != null){
            url = intent.getStringExtra("copiedLink")!!
        }
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

        webView.loadUrl(url)
    }
}
