package com.plutecoder.itworld.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.plutecoder.itworld.views.MainActivity
import com.plutecoder.itworld.R
import com.plutecoder.itworld.models.CategoryItem

class CategoryItemGridAdapter(val context: Context, var itemList: ArrayList<CategoryItem>, var categoryID : String?) :
    RecyclerView.Adapter<CategoryItemGridAdapter.OfferHolder>() {

    inner class OfferHolder(it: View) : RecyclerView.ViewHolder(it)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferHolder {
        return OfferHolder(
            LayoutInflater.from(context).inflate(R.layout.techlist_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: OfferHolder, position: Int) {
        val model = itemList[position]

        holder.itemView.findViewById<TextView>(R.id.textviel).setBackgroundColor(Color.parseColor("#f1f1f1"));
        holder.itemView.findViewById<TextView>(R.id.textviel).text=model.name
        holder.itemView.findViewById<TextView>(R.id.textviewlc).text = model.info

        Glide.with(context)
            .load(model.logo)
            .into(holder.itemView.findViewById<ImageView>(R.id.techimg));


        holder.itemView.findViewById<soup.neumorphism.NeumorphCardView>(R.id.techcard).setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("category_item", itemList[position])
            intent.putExtra("category_uid", categoryID)
            context.startActivity(intent)
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