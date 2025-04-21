package com.plutecoder.itworld.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.plutecoder.itworld.R
import com.plutecoder.itworld.databinding.IndividualRelatedItemBinding
import com.plutecoder.itworld.models.CategoryItem
import com.plutecoder.itworld.views.DetailedTechHubActivity
import com.plutecoder.itworld.views.isDarkModeEnabled
import com.plutecoder.itworld.views.showUsesDialog

class RelatedItemsAdapter(
    private val context: Context,
    private val items: List<CategoryItem>
) : RecyclerView.Adapter<RelatedItemsAdapter.CardViewHolder>() {

    inner class CardViewHolder(val binding: IndividualRelatedItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = IndividualRelatedItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = items[position]
        holder.binding.title.text = item.name
        holder.binding.description.text = item.info
        Glide.with(context)
            .load(item.logo)
            .into(holder.binding.image!!);

//        if (!item.uses.isNullOrEmpty()) {
//            for (use in item.uses!!) {
//                val chip: Chip = LayoutInflater.from(context)
//                    .inflate(R.layout.chip_view_item, null, false) as Chip
//                chip.text = use.title
//
//                chip.setOnClickListener {
//                    context.showUsesDialog(use)
//                }
//
//                holder.binding.featureChips?.addView(chip)
//            }
//        }
        if (!item.uses.isNullOrEmpty()) {
            // Combine all titles into one string
            val combinedTitle = "Uses :- " + item.uses!!.joinToString(", ") { it.title!! }
            holder.binding.txtuses!!.text = combinedTitle

        }

        if (isDarkModeEnabled(context)) {
            holder.binding.neuCard.setShadowColorLight( ContextCompat.getColor(context, R.color.neumorph_shadow_light))
            holder.binding.neuCard.setShadowColorDark( ContextCompat.getColor(context, R.color.neumorph_shadow_dark))
        }
    }

    override fun getItemCount(): Int = items.size
}
