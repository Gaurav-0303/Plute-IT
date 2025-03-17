package com.plutecoder.itworld.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.plutecoder.itworld.R
import com.plutecoder.itworld.adapters.RelatedItemsAdapter
import com.plutecoder.itworld.databinding.FragmentRelatedItemsBinding
import com.plutecoder.itworld.models.CategoryItem
import com.plutecoder.itworld.viewModels.RelatedItemsViewModel

class RelatedItemsFragment(private var categoryItemUid: String) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentRelatedItemsBinding
    private lateinit var myAdapter: RelatedItemsAdapter
    private lateinit var itemList: ArrayList<CategoryItem>

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

//        (view.parent as View).setBackgroundColor(resources.getColor(android.R.color.transparent))

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
        myAdapter = RelatedItemsAdapter(requireContext(), itemList)
        binding.bottomSheetRv.adapter = myAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

}
