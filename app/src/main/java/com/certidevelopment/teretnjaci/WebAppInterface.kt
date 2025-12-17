package com.certidevelopment.teretnjaci

import android.content.Context
import android.webkit.JavascriptInterface

class WebAppInterface(private val context: Context) {
    @JavascriptInterface
    fun onRouteChange(canGoBack: Boolean) {
    }
}