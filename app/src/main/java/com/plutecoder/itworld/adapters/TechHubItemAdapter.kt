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
import com.google.android.material.chip.Chip
import com.plutecoder.itworld.views.DetailedTechHubActivity
import com.plutecoder.itworld.R
import com.plutecoder.itworld.models.CategoryItem
import com.plutecoder.itworld.views.showUsesDialog

class TechHubItemAdapter(private val context: Context, private var itemList: ArrayList<CategoryItem>) :
    RecyclerView.Adapter<TechHubItemAdapter.OfferHolder>() {

    inner class OfferHolder(it: View) : RecyclerView.ViewHolder(it)

    override fun onBindViewHolder(holder: TechHubItemAdapter.OfferHolder, position: Int) {
        val model = itemList[position]

        holder.itemView.findViewById<TextView>(R.id.title).setBackgroundColor(Color.parseColor("#f1f1f1"));
        holder.itemView.findViewById<TextView>(R.id.title).text=model.name
        holder.itemView.findViewById<TextView>(R.id.description).text=model.info

        Glide.with(context)
            .load(itemList[position].basicRoadmap)
            .into(holder.itemView.findViewById<ImageView>(R.id.image))

        if (!model.uses.isNullOrEmpty()) {
            for (use in model.uses!!) {  // Iterate over the ArrayList<Uses>
                val chip: Chip = LayoutInflater.from(context)
                    .inflate(R.layout.chip_view_item, null, false) as Chip
                chip.text = use.title  // Use the title property for the chip text

                chip.setOnClickListener {
                    context.showUsesDialog(use)
                }

                holder.itemView.findViewById<com.google.android.material.chip.ChipGroup>(R.id.featureChips)
                    .addView(chip)
            }
        }

        holder.itemView.findViewById<soup.neumorphism.NeumorphCardView>(R.id.others_card).setOnClickListener {
            val intent = Intent(context, DetailedTechHubActivity::class.java)
            intent.putExtra("category_item", itemList[position])
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TechHubItemAdapter.OfferHolder {
        return OfferHolder(
            LayoutInflater.from(context).inflate(R.layout.individual_other_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateData(newItems: List<CategoryItem>) {
        itemList = newItems as ArrayList<CategoryItem>
        notifyDataSetChanged()
    }

}