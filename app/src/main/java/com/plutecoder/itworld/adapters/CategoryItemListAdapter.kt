package com.plutecoder.itworld.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.plutecoder.itworld.views.RoadmapActivity
import com.plutecoder.itworld.R
import com.plutecoder.itworld.databinding.IndividualListItemBinding
import com.plutecoder.itworld.models.CategoryItem
import com.plutecoder.itworld.views.isDarkModeEnabled
import com.plutecoder.itworld.views.showUsesDialog

class CategoryItemListAdapter(private val context: Context, private var itemList: ArrayList<CategoryItem>, var categoryUID : String?) :
    RecyclerView.Adapter<CategoryItemListAdapter.OfferHolder>() {

    inner class OfferHolder(val binding: IndividualListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferHolder {
        val binding = IndividualListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return OfferHolder(binding)
    }

    override fun onBindViewHolder(holder: OfferHolder, position: Int) {
        val model = itemList[position]
        val binding = holder.binding

        binding.txtlangname.text=model.name
        binding.txtlangdesc.text=model.info

        Glide.with(context)
            .load(model.logo)
            .into(binding.langimg);

        if (!model.uses.isNullOrEmpty()) {
            for (use in model.uses!!) {  // Iterate over the ArrayList<Uses>
                val chip: Chip = LayoutInflater.from(context)
                    .inflate(R.layout.chip_view_item, null, false) as Chip
                chip.text = use.title  // Use the title property for the chip text

                chip.setOnClickListener {
                    context.showUsesDialog(use)
                }

                binding.featureChips.addView(chip)
            }
        }

        binding.rowClick.setOnClickListener {
                val intent = Intent(context, RoadmapActivity::class.java)
                intent.putExtra("category_item", itemList[position])
                intent.putExtra("category_uid", categoryUID)
                context.startActivity(intent)
            }

        if (isDarkModeEnabled(context)) {
            binding.rowClick.setShadowColorLight(ContextCompat.getColor(context, R.color.neumorph_shadow_light))
            binding.rowClick.setShadowColorDark(ContextCompat.getColor(context, R.color.neumorph_shadow_dark))
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