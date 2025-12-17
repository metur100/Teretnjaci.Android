package com.certidevelopment.teretnjaci

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class MyWebViewClient : WebViewClient() {
    private val history = mutableListOf<String>()

    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        request?.url?.let { url ->
            history.add(url.toString())
        }
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        // Inject JavaScript to communicate with React app
        injectJavaScriptInterface(view)
    }

    private fun injectJavaScriptInterface(view: WebView?) {
        view?.evaluateJavascript("""
            if (window.Android && window.Android.onRouteChange) {
                window.Android.onRouteChange(window.history.length > 1);
            }
        """.trimIndent(), null)
    }
}