package com.plutecoder.itworld.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.plutecoder.itworld.R
import com.plutecoder.itworld.databinding.IndividualGridItemBinding
import com.plutecoder.itworld.models.CategoryItem
import com.plutecoder.itworld.views.RoadmapActivity
import com.plutecoder.itworld.views.isDarkModeEnabled

class CategoryItemGridAdapter(val context: Context, var itemList: ArrayList<CategoryItem>, var categoryID : String?) :
    RecyclerView.Adapter<CategoryItemGridAdapter.OfferHolder>() {

    inner class OfferHolder(val binding: IndividualGridItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferHolder {
        val binding = IndividualGridItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return OfferHolder(binding)
    }

    override fun onBindViewHolder(holder: OfferHolder, position: Int) {
        val model = itemList[position]
        val binding = holder.binding

        binding.textviel.text=model.name
        binding.textviewlc.text = model.info

        Glide.with(context)
            .load(model.logo)
            .into(binding.techimg);

        binding.techcard.setOnClickListener {
            val intent = Intent(context, RoadmapActivity::class.java)
            intent.putExtra("category_item", itemList[position])
            intent.putExtra("category_uid", categoryID)
            context.startActivity(intent)
        }

        if (isDarkModeEnabled(context)) {
            binding.techcard.setShadowColorLight( ContextCompat.getColor(context, R.color.neumorph_shadow_light))
            binding.techcard.setShadowColorDark( ContextCompat.getColor(context, R.color.neumorph_shadow_dark))
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateData(newItems: List<CategoryItem>) {
        itemList = newItems as ArrayList<CategoryItem>
        notifyDataSetChanged()
    }

}