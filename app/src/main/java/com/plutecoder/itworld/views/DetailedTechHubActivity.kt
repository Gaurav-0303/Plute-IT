package com.plutecoder.itworld.views

import android.graphics.Color
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.plutecoder.itworld.R
import com.plutecoder.itworld.databinding.ActivityDetailedTechHubBinding
import com.plutecoder.itworld.models.CategoryItem

class DetailedTechHubActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailedTechHubBinding
    private lateinit var webview : WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedTechHubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //receive from intent
        val categoryItem = intent.getSerializableExtra("category_item") as CategoryItem

        webview = binding.webview

        binding.title.text = categoryItem.name
        binding.description.text = categoryItem.info

        setUpWebView()


        var imgurl= "<img src='${categoryItem.basicRoadmap}' width='100%' height='100%'/>"
        webview.loadData(imgurl, "text/html", "UTF-8")

        if (this.isDarkModeEnabled(this)) {
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

        // Enable support for zooming and scaling
        webview.settings.setSupportZoom(true)
        webview.settings.builtInZoomControls = true
        webview.settings.displayZoomControls = false

        // Set the viewport to fit the screen automatically
        webview.settings.loadWithOverviewMode = true
        webview.settings.useWideViewPort = true
    }
}