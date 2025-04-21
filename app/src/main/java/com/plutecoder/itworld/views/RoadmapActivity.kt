package com.plutecoder.itworld.views

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.plutecoder.itworld.R
import com.plutecoder.itworld.databinding.ActivityRoadmapBinding
import com.plutecoder.itworld.models.CategoryItem
import com.plutecoder.itworld.fragments.RelatedItemsFragment

class RoadmapActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var binding: ActivityRoadmapBinding
    private lateinit var webview: WebView
    private var isRoadmapLoaded = false
    private lateinit var categoryItemNotification : CategoryItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoadmapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        webview = findViewById(R.id.webview)


        val itemId = intent.getStringExtra("itemId")

        if(itemId != null){

            //retrive item from firebase
            val database = FirebaseDatabase.getInstance()
            val itemRef = database.getReference("items").child(itemId ?: "")

            itemRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        categoryItemNotification = snapshot.getValue(CategoryItem::class.java)!!

                        // Show data in top bar
                        binding.header.title.text = categoryItemNotification.name

                        setUpWebView(categoryItemNotification)
                    } else {
                        Log.d("Firebase", "No item found with ID: $itemId")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Database error: ${error.message}")
                }
            })

            // Go back
            binding.header.backImageView.setOnClickListener { onBackPressed() }


            // Bottom sheet opening
            findViewById<ImageView>(R.id.open_framework).setOnClickListener {
                val relatedItemsDialog = RelatedItemDialog(itemId)
                relatedItemsDialog.show(supportFragmentManager, relatedItemsDialog.tag)
            }
        }
        else{

            val categoryItem = intent.getParcelableExtra<CategoryItem>("category_item")

            Log.d("Gaurav", categoryItem.toString())

            // Bottom sheet opening
            findViewById<ImageView>(R.id.open_framework).setOnClickListener {
                val relatedItemsFragment = RelatedItemsFragment(categoryItem?.uid!!)
                relatedItemsFragment.show(supportFragmentManager, relatedItemsFragment.tag)
            }

            // Show data in top bar
            binding.header.title.text = categoryItem?.name

            // Go back
            binding.header.backImageView.setOnClickListener { onBackPressed() }

            setUpWebView(categoryItem!!)

            // Bottom sheet opening
            findViewById<ImageView>(R.id.open_framework).setOnClickListener {
                val relatedItemsDialog = RelatedItemDialog(categoryItem.uid!!)
                relatedItemsDialog.show(supportFragmentManager, relatedItemsDialog.tag)
            }
        }

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
            domStorageEnabled = true
            databaseEnabled = true
            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        }

        webview.addJavascriptInterface(WebAppInterface(isDarkModeEnabled(this)), "AndroidInterface")

        webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                if (!isRoadmapLoaded) {
                    isRoadmapLoaded = true

                    handler.postDelayed({
                        showRoadmap(categoryItem)
                    }, 500)
                }
            }
        }

        webview.webChromeClient = WebChromeClient()

        // Load the loading screen first (from string or asset)
        loadLoadingScreen()
    }

    private fun loadLoadingScreen() {
        webview.loadUrl("file:///android_asset/loading.html")
    }



    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null) // Clean up the handler
    }
}

// Add this interface
class WebAppInterface(private val isDarkMode: Boolean) {
    @JavascriptInterface
    fun isDarkMode(): Boolean = isDarkMode
}
