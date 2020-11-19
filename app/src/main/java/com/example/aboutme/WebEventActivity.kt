package com.example.aboutme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import timber.log.Timber

class WebEventActivity : AppCompatActivity() {

    private val embedUrl: String = "http://powerrankings.nioint.com"

    private lateinit var webView: WebView
    private lateinit var updateButton: Button
    private lateinit var reloadButton: Button
    private lateinit var messageEdit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_web_event)
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
        Timber.d("sendMessageToWeb(): ${messageEdit.text}")
    }

    /**
     * Receive message from webview and pass on to native.
     */
    fun showMessageInApp(message: String) {
        try {
            messageEdit.setText(message)
            Timber.d("showMessageInApp():$message")
        }
        catch (e: Exception) {
            Timber.e("showMessageInApp(): ${e.toString()}")
        }
    }

    fun navigateToSlide() {
//        startActivity(Intent(this, SlideActivity::class.java))
        Timber.i("navigate to SlideActivity")
    }

    inner class WebViewInterface {

        @JavascriptInterface
        fun showMessageInApp(message: String) {
            this@WebEventActivity.showMessageInApp(message)
        }

        @JavascriptInterface
        fun navigateToSlide() {
            this@WebEventActivity.navigateToSlide()
        }
    }
}