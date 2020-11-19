package com.example.aboutme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.aboutme.databinding.FragmentSlideBinding
import timber.log.Timber

class SlideFragment(
    private val position: Int,
    private val slideSource: SlideSource
): Fragment() {

    init {
        Timber.i("init: $position")
    }

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

        webView = binding.webView
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = WebViewClient()

        webView.loadUrl(slideSource.url)

        return binding.root
    }
}
