package com.plutecoder.itworld.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.plutecoder.itworld.R
import com.plutecoder.itworld.adapters.RelatedItemsCategoryAdapter
import com.plutecoder.itworld.databinding.FragmentRelatedItemsBinding
import com.plutecoder.itworld.models.CategoryItem
import com.plutecoder.itworld.viewModels.RelatedItemsViewModel
import com.plutecoder.itworld.views.MainActivity

class RelatedItemsFragment(private var categoryItemUid: String) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentRelatedItemsBinding
    private lateinit var myAdapter: RelatedItemsCategoryAdapter
    private lateinit var itemList: ArrayList<Pair<String, List<CategoryItem>>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRelatedItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this)[RelatedItemsViewModel::class.java]

        val allCategories = MainActivity.categoryViewModel?.categoriesList?.value ?: emptyList()

        itemList = ArrayList()
        setUpRecyclerView()

        viewModel.categoryItems.observe(viewLifecycleOwner, Observer { relatedItems ->

            itemList.clear()

            // Group related items by category title
            val groupedMap = relatedItems.groupBy { item ->
                allCategories.find { it.uid == item.categoryUid }?.let { "Related ${it.title}" } ?: "Others"
            }

            // Convert grouped map to itemList format
            for ((categoryTitle, itemsList) in groupedMap) {
                itemList.add(Pair(categoryTitle, itemsList))
            }

            // Notify adapter
            myAdapter.notifyDataSetChanged()
        })

        viewModel.fetchRelatedItems(categoryItemUid)
    }

    private fun setUpRecyclerView() {
        binding.bottomSheetRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        myAdapter = RelatedItemsCategoryAdapter(requireContext(), itemList)
        binding.bottomSheetRv.adapter = myAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

}
