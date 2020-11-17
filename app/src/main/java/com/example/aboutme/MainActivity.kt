package com.example.aboutme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import kotlin.Exception

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private val embedUrl: String = "http://powerrankings.nioint.com"

    private lateinit var webView: WebView
    private lateinit var updateButton: Button
    private lateinit var reloadButton: Button
    private lateinit var messageEdit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        webView = findViewById(R.id.web_view)
        updateButton = findViewById(R.id.update_button)
        reloadButton = findViewById(R.id.reload_button)
        messageEdit = findViewById(R.id.message_edit)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = WebViewClient()
        webView.addJavascriptInterface(WebViewInterface(), "App")

        webView.loadUrl(embedUrl)

        updateButton.setOnClickListener {
            sendMessageToWeb()
        }

        reloadButton.setOnClickListener {
            webView.reload()
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
            return
        }
        super.onBackPressed()
    }

    /**
     * Send data to webview through function updateFromNative.
     */
    private fun sendMessageToWeb() {
        webView.evaluateJavascript("javascript:updateFromApp('${messageEdit.text}')", null)
        Log.d(TAG, "sendMessageToWeb(): ${messageEdit.text}")
    }

    /**
     * Receive message from webview and pass on to native.
     */
    fun showMessageInApp(message: String) {
        try {
            messageEdit.setText(message)
            Log.d(TAG, "showMessageInApp():$message")
        }
        catch (e: Exception) {
            Log.e(TAG, "showMessageInApp(): ${e.toString()}")
        }
    }

    fun navigateToSlide() {
        startActivity(Intent(this, SlideActivity::class.java))
    }

    inner class WebViewInterface {

        @JavascriptInterface
        fun showMessageInApp(message: String) {
            this@MainActivity.showMessageInApp(message)
        }

        @JavascriptInterface
        fun navigateToSlide() {
            this@MainActivity.navigateToSlide()
        }
    }
}