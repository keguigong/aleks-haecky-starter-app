package com.example.aboutme

import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.aboutme.slideaimation.DepthPageTransformer
import com.example.aboutme.slideaimation.ZoomOutPageTransformer
import timber.log.Timber

class SlideActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2

    /**
     * The web pages to be displayed
     */
    private val slideSourceList  = mutableListOf<SlideSource>(
        SlideSource("text", "https://www.gatsbyjs.com/"),
        SlideSource("text", "https://www.gatsbyjs.com/get-started/"),
        SlideSource("text", "https://www.gatsbyjs.com/blog/"),
        SlideSource("text","https://www.gatsbyjs.com/cloud"),
        SlideSource("text", "https://www.gatsbyjs.com/resources/gatsby-days/"),
        SlideSource("text", "https://www.gatsbyjs.com/dashboard/login/"),
        SlideSource("text", "https://www.gatsbyjs.com/support/"),
        SlideSource("text", "https://www.gatsbyjs.com/use-cases/e-commerce/"),
        SlideSource("text", "https://www.gatsbyjs.com/pricing/"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_slide)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.pager)
//        viewPager.setPageTransformer(ZoomOutPageTransformer())
//        viewPager.setPageTransformer(DepthPageTransformer())

        slideSourceList.shuffle()

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = SlideAdapter(this)
        viewPager.adapter = pagerAdapter
        viewPager.offscreenPageLimit = 10

        viewPager.autoScroll(3000)
    }

    private fun ViewPager2.autoScroll(interval: Long) {
        val handler = Handler()
        var scrollPosition = 0

        val runnable = object : Runnable {
            override fun run() {
                val count = adapter?.itemCount ?: 0
                setCurrentItem(scrollPosition++ % count, true)

                handler.postDelayed(this, interval)
            }
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                // Updating "scroll position" when user scrolls manually
                scrollPosition = position + 1
                Timber.d("onPageSelected(): $position")
            }

            override fun onPageScrollStateChanged(state: Int) {
                // Not necessary
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // Not necessary
            }
        })

        handler.post(runnable)
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
//            viewPager.currentItem = viewPager.currentItem - 1
            super.onBackPressed()
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class SlideAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = slideSourceList.size

        override fun createFragment(position: Int): Fragment =
            SlideFragment(position, slideSourceList.get(position))
    }
}