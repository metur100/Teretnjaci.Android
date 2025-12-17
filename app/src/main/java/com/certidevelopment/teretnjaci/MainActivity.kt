package com.certidevelopment.teretnjaci

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.certidevelopment.teretnjaci.ui.theme.TeretnjaciTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TeretnjaciTheme {
                WebViewScreen()
            }
        }
    }
}

@Composable
fun WebViewScreen() {
    var webView: WebView? by remember { mutableStateOf(null) }

    AndroidView(
        modifier = Modifier.fillMaxSize(), // CRITICAL: Force full screen
        factory = { context ->
            WebView(context).apply {
                webView = this

                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.useWideViewPort = true
                settings.loadWithOverviewMode = true
                settings.cacheMode = WebSettings.LOAD_DEFAULT

                // Force proper layout
                settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
                setLayerType(WebView.LAYER_TYPE_HARDWARE, null)

                // Disable zoom
                settings.setSupportZoom(false)
                settings.builtInZoomControls = false
                settings.displayZoomControls = false

                // CRITICAL: Force WebView to calculate height properly
                settings.useWideViewPort = true
                settings.loadWithOverviewMode = true

                addJavascriptInterface(WebAppInterface(context), "Android")
                webViewClient = MyWebViewClient()

                //loadUrl("http://169.254.123.185:5173/")
                loadUrl("https://teretnjaci.pages.dev/")
            }
        }
    )

    BackHandler {
        webView?.let { wv ->
            if (wv.canGoBack()) {
                wv.goBack()
            } else {
                wv.evaluateJavascript("""
                if (window.history.length > 1) {
                    window.history.back();
                    true;
                } else {
                    false;
                }
            """.trimIndent()) { result ->
                    if (result == "false") {
                        // Handle app exit or minimize
                    }
                }
            }
        }
    }
}