package com.plutecoder.itworld.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.plutecoder.itworld.R
import com.plutecoder.itworld.databinding.IndividualCategoryBinding
import com.plutecoder.itworld.models.Category
import com.plutecoder.itworld.views.LanguageActivity
import com.plutecoder.itworld.views.TechHubActivity
import com.plutecoder.itworld.views.TechnologiesActivity
import com.plutecoder.itworld.views.isDarkModeEnabled


class CategoryAdapter(private val context : Context, private val items: List<Category>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(var binding : IndividualCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = IndividualCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val binding = holder.binding

        binding.title.text = items[position].title
        binding.subtitle.text = items[position].subtitle
        Glide.with(context)
            .load(items[position].image)
            .into(binding.imageView)

        // Handle item click
        binding.languageCard.setOnClickListener {
            val targetActivity = when (items[position].uiType) {
                "LIST" -> LanguageActivity::class.java
                "GRID" -> TechnologiesActivity::class.java
                "POST" -> TechHubActivity::class.java
                else -> null
            }

            targetActivity?.let { activity ->
                context.startActivity(Intent(context, activity).apply {
                    putExtra("category", items[position])
                })
            }
        }

        if (isDarkModeEnabled(context)) {
            binding.languageCard.setShadowColorLight(ContextCompat.getColor(context, R.color.neumorph_shadow_light))
            binding.languageCard.setShadowColorDark(ContextCompat.getColor(context, R.color.neumorph_shadow_dark))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
