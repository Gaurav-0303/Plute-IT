package com.plutecoder.itworld.views

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.webkit.WebChromeClient
import android.webkit.WebResourceResponse
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
    private lateinit var binding: ActivityRoadmapBinding
    private lateinit var webview: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoadmapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        webview = findViewById(R.id.webview)

        val categoryItem = intent.getSerializableExtra("category_item") as CategoryItem

        // Bottom sheet opening
        findViewById<ImageView>(R.id.open_framework).setOnClickListener {
            val relatedItemsFragment = RelatedItemsFragment(categoryItem.uid!!)
            relatedItemsFragment.show(supportFragmentManager, relatedItemsFragment.tag)
        }

        // Show data in top bar
        binding.header.title.text = categoryItem.name

        // Go back
        binding.header.backImageView.setOnClickListener { onBackPressed() }

        setUpWebView(categoryItem)

        if (isDarkModeEnabled(this)) {
            binding.flatCard.setShadowColorLight(ContextCompat.getColor(this, R.color.neumorph_shadow_light))
            binding.flatCard.setShadowColorDark(ContextCompat.getColor(this, R.color.neumorph_shadow_dark))
        }
    }

    private fun showRoadmap(categoryItem: CategoryItem) {
        val imgHtml = """
        <html>
        <head>
          <style>
            img {
                padding: 60px;
            }
          </style>  
        </head>
        <body>
            <div class="image-container">
                <img src='${categoryItem.basicRoadmap}' width='100%' height='100%'/>
            </div>
        </body>
        </html>
    """.trimIndent()

        webview.loadDataWithBaseURL(null, imgHtml, "text/html", "UTF-8", null)
    }

    private fun setUpWebView(categoryItem: CategoryItem) {
        webview.setBackgroundColor(ContextCompat.getColor(this, R.color.appbackcolor1))
        webview.settings.apply {
            builtInZoomControls = true
            displayZoomControls = false
            javaScriptEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            allowFileAccess = true
            domStorageEnabled = true  // Enable DOM storage
            databaseEnabled = true    // Enable database storage
            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK // Use cache first, then network
        }

        webview.webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
                // Use cache for images
                if (url?.endsWith(".png") == true || url?.endsWith(".jpg") == true || url?.endsWith(".jpeg") == true) {
                    return super.shouldInterceptRequest(view, url)
                }
                return super.shouldInterceptRequest(view, url)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                webview.settings.cacheMode = WebSettings.LOAD_DEFAULT
            }
        }

        webview.webChromeClient = WebChromeClient()

        showRoadmap(categoryItem)
    }


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null) // Clean up the handler
    }
}
