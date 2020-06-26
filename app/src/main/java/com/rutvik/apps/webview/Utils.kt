package com.rutvik.apps.webview

import android.webkit.URLUtil

object Utils {

    fun isURL(text: String) : Boolean = URLUtil.isValidUrl(text)
}