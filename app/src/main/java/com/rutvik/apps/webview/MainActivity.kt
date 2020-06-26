package com.rutvik.apps.webview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rutvik.apps.webview.service.KeyBoardMonitorService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var url = "https://www.google.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startService(Intent(this, KeyBoardMonitorService::class.java))
        if (intent.getCharSequenceArrayExtra(Intent.EXTRA_PROCESS_TEXT) != null) {
            url = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()
        } else if (intent.getStringExtra("copiedLink") != null){
            url = intent.getStringExtra("copiedLink")
        }
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

        webView.loadUrl(url)
    }
}
