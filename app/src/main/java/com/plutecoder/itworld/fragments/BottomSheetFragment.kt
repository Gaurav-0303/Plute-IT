package com.plutecoder.itworld.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.plutecoder.itworld.adapters.BottomSheetFrameworkAdapter
import com.plutecoder.itworld.databinding.FragmentBottomSheetBinding
import com.plutecoder.itworld.models.CategoryItem
import com.plutecoder.itworld.models.Uses
import com.plutecoder.itworld.viewModels.RelatedItemsViewModel

class BottomSheetFragment(var categoryUid: String, var categoryItemUid: String) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetBinding
    private lateinit var myAdapter: BottomSheetFrameworkAdapter
    private lateinit var itemList: ArrayList<CategoryItem>
    private lateinit var viewModel: RelatedItemsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[RelatedItemsViewModel::class.java]

        (view.parent as View).setBackgroundColor(resources.getColor(android.R.color.transparent))

        itemList = ArrayList()
        setUpRecyclerView()

        viewModel.categoryItems.observe(viewLifecycleOwner, Observer { relatedItems ->
            itemList.clear()
            for (categoryItem in relatedItems) {
                itemList.add(categoryItem)
            }
            myAdapter.notifyDataSetChanged()
        })

        viewModel.fetchRelatedItems(categoryItemUid)
    }

    private fun setUpRecyclerView() {
        binding.bottomSheetRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        myAdapter = BottomSheetFrameworkAdapter(requireContext(), itemList)
        binding.bottomSheetRv.adapter = myAdapter
    }
}
