package com.plutecoder.itworld.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plutecoder.itworld.R
import com.plutecoder.itworld.databinding.IndividualRelatedItemBinding
import com.plutecoder.itworld.databinding.IndividualRelatedItemCategoryBinding
import com.plutecoder.itworld.models.CategoryItem
import com.plutecoder.itworld.views.isDarkModeEnabled

class RelatedItemsCategoryAdapter(val context : Context, val itemList : ArrayList<Pair<String, List<CategoryItem>>>) : RecyclerView.Adapter<RelatedItemsCategoryAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding : IndividualRelatedItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = IndividualRelatedItemCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val binding = holder.binding
        val (categoryTitle, categoryItems) = itemList[position]

        binding.categoryTitle.text = categoryTitle

        // Setup inner RecyclerView
        val innerAdapter = RelatedItemsAdapter(context, ArrayList(categoryItems))
        binding.rvInside.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvInside.adapter = innerAdapter


//        binding.title.text = itemList[position].name
//        binding.description.text = itemList[position].info
//
//        if (isDarkModeEnabled(context)) {
//            binding.neuCard.setShadowColorLight( ContextCompat.getColor(context, R.color.neumorph_shadow_light))
//            binding.neuCard.setShadowColorDark( ContextCompat.getColor(context, R.color.neumorph_shadow_dark))
//        }
    }

}