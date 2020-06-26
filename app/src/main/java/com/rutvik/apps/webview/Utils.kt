package com.rutvik.apps.webview

import android.util.Patterns
import android.webkit.URLUtil

object Utils {

    fun isURL(text: String) : Boolean = URLUtil.isValidUrl(text)
}