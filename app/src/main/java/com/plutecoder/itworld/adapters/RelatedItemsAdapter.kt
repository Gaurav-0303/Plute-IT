package com.plutecoder.itworld.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.plutecoder.itworld.databinding.IndividualRelatedItemBinding
import com.plutecoder.itworld.models.CategoryItem

class RelatedItemsAdapter(val context : Context, val itemList : ArrayList<CategoryItem>) : RecyclerView.Adapter<RelatedItemsAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding : IndividualRelatedItemBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = IndividualRelatedItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.title.text = itemList[position].name
        holder.binding.description.text = itemList[position].info
    }

}