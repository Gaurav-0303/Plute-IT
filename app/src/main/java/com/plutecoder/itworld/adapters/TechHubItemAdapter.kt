package com.plutecoder.itworld.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.plutecoder.itworld.views.DetailedTechHubActivity
import com.plutecoder.itworld.R
import com.plutecoder.itworld.databinding.IndividualTechHubItemBinding
import com.plutecoder.itworld.models.CategoryItem
import com.plutecoder.itworld.views.isDarkModeEnabled
import com.plutecoder.itworld.views.showUsesDialog

class TechHubItemAdapter(
    private val context: Context,
    private var itemList: ArrayList<CategoryItem>
) :
    RecyclerView.Adapter<TechHubItemAdapter.OfferHolder>() {

    inner class OfferHolder(val binding: IndividualTechHubItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: TechHubItemAdapter.OfferHolder, position: Int) {
        val model = itemList[position]
        val binding = holder.binding

        binding.title.setBackgroundColor(Color.parseColor("#f1f1f1"))
        binding.title.text = model.name
        binding.description.text = model.info

        // Configure WebView
        val webView = binding.image as WebView
        webView.apply {
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.domStorageEnabled = true
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    // Adjust WebView height based on content
//                    view?.evaluateJavascript(
//                        "(function() { return document.body.scrollHeight; })();"
//                    ) { height ->
//                        val webViewHeight = height.toFloatOrNull()?.toInt() ?: 0
//                        view?.layoutParams?.height = webViewHeight
//                    }
                }
            }
            loadUrl(itemList[position].basicRoadmap!!)
        }

        if (!model.uses.isNullOrEmpty()) {
            for (use in model.uses!!) {
                val chip: Chip = LayoutInflater.from(context)
                    .inflate(R.layout.chip_view_item, null, false) as Chip
                chip.text = use.title

                chip.setOnClickListener {
                    context.showUsesDialog(use)
                }

                binding.featureChips.addView(chip)
            }
        }

        binding.othersCard.setOnClickListener {
            val intent = Intent(context, DetailedTechHubActivity::class.java)
            intent.putExtra("category_item", itemList[position])
            context.startActivity(intent)
        }

        if (isDarkModeEnabled(context)) {
            binding.othersCard.setShadowColorLight(
                ContextCompat.getColor(
                    context,
                    R.color.neumorph_shadow_light
                )
            )
            binding.othersCard.setShadowColorDark(
                ContextCompat.getColor(
                    context,
                    R.color.neumorph_shadow_dark
                )
            )
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TechHubItemAdapter.OfferHolder {
        val binding =
            IndividualTechHubItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return OfferHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateData(newItems: List<CategoryItem>) {
        itemList = newItems as ArrayList<CategoryItem>
        notifyDataSetChanged()
    }
}