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

class RelatedItemsAdapter(val context : Context, val itemList : ArrayList<CategoryItem>) : RecyclerView.Adapter<RelatedItemsAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding : IndividualRelatedItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = IndividualRelatedItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val binding = holder.binding

        binding.title.text = itemList[position].name
        binding.description.text = itemList[position].info

        if (isDarkModeEnabled(context)) {
            binding.neuCard.setShadowColorLight( ContextCompat.getColor(context, R.color.neumorph_shadow_light))
            binding.neuCard.setShadowColorDark( ContextCompat.getColor(context, R.color.neumorph_shadow_dark))
        }
    }

}