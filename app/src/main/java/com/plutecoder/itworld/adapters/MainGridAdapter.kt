package com.plutecoder.itworld.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.plutecoder.itworld.views.LanguageActivity
import com.plutecoder.itworld.views.TechHubActivity
import com.plutecoder.itworld.views.TechnologiesActivity
import com.plutecoder.itworld.databinding.MainGridItemBinding
import com.plutecoder.itworld.models.Category


class MainGridAdapter(private val context : Context, private val items: List<Category>) : RecyclerView.Adapter<MainGridAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MainGridItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.title.text = items[position].title
        holder.binding.subtitle.text = items[position].subtitle
        Glide.with(context)
            .load(items[position].image)
            .into(holder.binding.imageView)

        Log.d("Gaurav", items[position].title.toString())

        // Handle item click
        holder.binding.languageCard.setOnClickListener {
            if (items[position].uiType == "LIST") {
                val intent = Intent(context, LanguageActivity::class.java)
                intent.putExtra("category", items[position])
                context.startActivity(intent)
            }
            else if(items[position].uiType == "GRID"){
                val intent = Intent(context, TechnologiesActivity::class.java)
                intent.putExtra("category", items[position])
                context.startActivity(intent)
            }
            else if(items[position].uiType == "POST"){
                val intent = Intent(context, TechHubActivity::class.java)
                intent.putExtra("category", items[position])
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(var binding : MainGridItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}
