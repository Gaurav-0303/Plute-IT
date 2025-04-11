package com.plutecoder.itworld.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.plutecoder.itworld.R
import com.plutecoder.itworld.databinding.IndividualRelatedItemBinding
import com.plutecoder.itworld.models.CategoryItem
import com.plutecoder.itworld.views.isDarkModeEnabled

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

        if (isDarkModeEnabled(context)) {
            holder.binding.neuCard.setShadowColorLight( ContextCompat.getColor(context, R.color.neumorph_shadow_light))
            holder.binding.neuCard.setShadowColorDark( ContextCompat.getColor(context, R.color.neumorph_shadow_dark))
        }
    }

    override fun getItemCount(): Int = items.size
}
