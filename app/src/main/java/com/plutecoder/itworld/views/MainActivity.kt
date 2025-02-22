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
import com.plutecoder.itworld.R
import com.plutecoder.itworld.databinding.WebpngBinding
import com.plutecoder.itworld.models.CategoryItem
import com.plutecoder.itworld.fragments.BottomSheetFragment

class MainActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var binding : WebpngBinding
    private lateinit var webview : WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WebpngBinding.inflate(layoutInflater)
        setContentView(binding.root)

        webview = findViewById(R.id.webview)

        // Disable screenshots
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_SECURE,
//            WindowManager.LayoutParams.FLAG_SECURE
//        )

        val categoryItem = intent.getSerializableExtra("category_item") as CategoryItem
        val categoryUid = intent.getStringExtra("category_uid") as String

        //bottom sheet opening
        findViewById<ImageView>(R.id.open_framwework).setOnClickListener {
            val bottomSheetFragment = BottomSheetFragment(categoryUid, categoryItem.uid!!)
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }

        //show data in top bar
        binding.header.title.text = categoryItem.name

        setUpWebView()

        var imgurl= "<img src='${categoryItem.basicRoadmap}' width='100%' height='100%'/>"
        webview.loadData(imgurl, "text/html", "UTF-8")
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
        val width = display.width

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