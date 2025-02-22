package com.plutecoder.itworld.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.plutecoder.itworld.adapters.MainGridAdapter.ViewHolder
import com.plutecoder.itworld.databinding.IndividualFrameworkBinding
import com.plutecoder.itworld.databinding.MainGridItemBinding
import com.plutecoder.itworld.models.CategoryItem
import com.plutecoder.itworld.models.Uses

class BottomSheetFrameworkAdapter(val context : Context, val itemList : ArrayList<CategoryItem>) : RecyclerView.Adapter<BottomSheetFrameworkAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding : IndividualFrameworkBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = IndividualFrameworkBinding.inflate(LayoutInflater.from(context), parent, false)
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