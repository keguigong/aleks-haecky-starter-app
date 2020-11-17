package com.example.aboutme

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.aboutme.databinding.FragmentSlideBinding

class SlideFragment(
    private val position: Int
): Fragment() {

    private val TAG = "SlideFragment"

    init {
        Log.d(TAG, "init: $position")
    }

    data class SlideSource(
        val type: String,
        val url: String
    )

    private val slideSources: MutableList<SlideSource> = mutableListOf(
        SlideSource("text", "http://powerrankings.nioint.com/slide/1"),
        SlideSource("text", "http://powerrankings.nioint.com/slide/2"),
        SlideSource("text", "http://powerrankings.nioint.com/slide/3"),
        SlideSource("text", "http://powerrankings.nioint.com/slide/4"),
        SlideSource("text", "http://powerrankings.nioint.com/slide/5")
    )

    lateinit var currentSource: SlideSource
    lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentSlideBinding>(
            inflater,
            R.layout.fragment_slide, container, false
        )
        binding.bindThis = this

        // Set source url according to position
        setSource()

        webView = binding.webView
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = WebViewClient()

        webView.loadUrl(currentSource.url)

        return binding.root
    }

    private fun setSource() {
        currentSource = if (position < slideSources.size) {
            slideSources[position]
        } else {
            slideSources[0]
        }
    }
}
