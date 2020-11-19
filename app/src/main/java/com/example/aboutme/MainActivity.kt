package com.example.aboutme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    lateinit var navSlideButton: Button
    lateinit var navWebEventButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navSlideButton = findViewById(R.id.nav_slide_button)
        navWebEventButton = findViewById(R.id.nav_web_event_button)

        navSlideButton.setOnClickListener {
            startActivity(Intent(this, SlideActivity::class.java))
            Timber.i("start SlideActivity")
        }

        navWebEventButton.setOnClickListener {
            startActivity(Intent(this, WebEventActivity::class.java))
            Timber.i("start WebEventActivity")
        }
    }
}