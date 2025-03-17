package com.plutecoder.itworld.views

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.plutecoder.itworld.R
import com.plutecoder.itworld.databinding.ActivityRoadmapBinding
import com.plutecoder.itworld.models.CategoryItem
import com.plutecoder.itworld.fragments.RelatedItemsFragment

class RoadmapActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var binding : ActivityRoadmapBinding
    private lateinit var webview : WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoadmapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        webview = findViewById(R.id.webview)

        val categoryItem = intent.getSerializableExtra("category_item") as CategoryItem

        //bottom sheet opening
        findViewById<ImageView>(R.id.open_framework).setOnClickListener {
            val relatedItemsFragment = RelatedItemsFragment(categoryItem.uid!!)
            relatedItemsFragment.show(supportFragmentManager, relatedItemsFragment.tag)
        }

        //show data in top bar
        binding.header.title.text = categoryItem.name

        //for going back
        binding.header.backImageView.setOnClickListener { onBackPressed() }

        setUpWebView()

        var imgurl= "<img src='${categoryItem.basicRoadmap}' width='100%' height='100%'/>"
        webview.loadData(imgurl, "text/html", "UTF-8")

        if (isDarkModeEnabled(this)) {
            binding.flatCard.setShadowColorLight(ContextCompat.getColor(this, R.color.neumorph_shadow_light))
            binding.flatCard.setShadowColorDark(ContextCompat.getColor(this, R.color.neumorph_shadow_dark))
        }
    }

    private fun setUpWebView() {
        webview!!.setBackgroundColor(Color.parseColor("#f1f1f1"));
        webview!!.settings.builtInZoomControls = true
        webview.setWebChromeClient(WebChromeClient())
        webview.getSettings().setAllowFileAccess(true)
        webview.getSettings().setPluginState(WebSettings.PluginState.ON)
        webview.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND)
        webview.setWebViewClient(WebViewClient())
        webview.getSettings().setJavaScriptEnabled(true)
        webview.getSettings().setLoadWithOverviewMode(true)
        webview.getSettings().setUseWideViewPort(true)
        webview.getSettings().setDisplayZoomControls(false);

        val display = windowManager.defaultDisplay

        // Enable support for zooming and scaling
        webview.settings.setSupportZoom(true)
        webview.settings.builtInZoomControls = true
        webview.settings.displayZoomControls = false

        // Set the viewport to fit the screen automatically
        webview.settings.loadWithOverviewMode = true
        webview.settings.useWideViewPort = true
    }


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null) // Clean up the handler
    }

}