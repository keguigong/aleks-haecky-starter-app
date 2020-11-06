package com.example.aboutme

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var callButton: Button
    private var clickTimes: Int  = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.web_view)
        callButton = findViewById(R.id.set_web_text)

        var webViewSettings = webView.settings
        webViewSettings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.addJavascriptInterface(Web2Android(), "Android")

        webView.loadUrl("http://powerrankings.nioint.com")

        callButton.setOnClickListener {
            webAlert()
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
            return
        }
        super.onBackPressed()
    }

    private fun webAlert() {
        webView.loadUrl("javascript:setWebText('Hello Web!')")
    }

    inner class Web2Android {

        @JavascriptInterface
        fun setAndroidToast() {
            clickTimes++
            Toast.makeText(this@MainActivity, "You clicked ${clickTimes.toString()} times.",
                Toast.LENGTH_SHORT).show()
        }
    }
}