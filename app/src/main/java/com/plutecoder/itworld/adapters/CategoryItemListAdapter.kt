package com.plutecoder.itworld.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.plutecoder.itworld.views.RoadmapActivity
import com.plutecoder.itworld.R
import com.plutecoder.itworld.models.CategoryItem
import com.plutecoder.itworld.views.isDarkModeEnabled
import com.plutecoder.itworld.views.showUsesDialog

class CategoryItemListAdapter(private val context: Context, private var itemList: ArrayList<CategoryItem>, var categoryUID : String?) :
    RecyclerView.Adapter<CategoryItemListAdapter.OfferHolder>() {

    inner class OfferHolder(it: View) : RecyclerView.ViewHolder(it)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferHolder {
        return OfferHolder(

            LayoutInflater.from(context).inflate(R.layout.individual_list_item, parent, false)

        )
    }

    override fun onBindViewHolder(holder: OfferHolder, position: Int) {
        val model = itemList[position]

        holder.itemView.findViewById<TextView>(R.id.txtlangname).setBackgroundColor(Color.parseColor("#f1f1f1"));
        holder.itemView.findViewById<TextView>(R.id.txtlangname).text=model.name
        holder.itemView.findViewById<TextView>(R.id.txtlangdesc).text=model.info

        Glide.with(context)
            .load(model.logo)
            .into(holder.itemView.findViewById<ImageView>(R.id.langimg));

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


        holder.itemView.findViewById<soup.neumorphism.NeumorphCardView>(R.id.row_click).setOnClickListener {
                val intent = Intent(context, RoadmapActivity::class.java)
                intent.putExtra("category_item", itemList[position])
                intent.putExtra("category_uid", categoryUID)
                context.startActivity(intent)
            }

        if (context.isDarkModeEnabled(context)) {
            holder.itemView.findViewById<soup.neumorphism.NeumorphCardView>(R.id.row_click).setShadowColorLight(ContextCompat.getColor(context, R.color.neumorph_shadow_light))
            holder.itemView.findViewById<soup.neumorphism.NeumorphCardView>(R.id.row_click).setShadowColorDark(ContextCompat.getColor(context, R.color.neumorph_shadow_dark))
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