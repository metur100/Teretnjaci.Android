package com.certidevelopment.teretnjaci

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
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
        factory = { context ->
            WebView(context).apply {
                webView = this

                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.useWideViewPort = true
                settings.loadWithOverviewMode = true
                settings.cacheMode = WebSettings.LOAD_DEFAULT

                webViewClient = WebViewClient()

                // 🔗 YOUR REACT APP URL
                loadUrl("https://teretnjaci.pages.dev/")
            }
        }
    )

    // 🔙 Android back button → Web history
    BackHandler(enabled = webView?.canGoBack() == true) {
        webView?.goBack()
    }
}
