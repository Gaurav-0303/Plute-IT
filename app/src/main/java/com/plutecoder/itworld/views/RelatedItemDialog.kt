package com.plutecoder.itworld.views

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.plutecoder.itworld.adapters.RelatedItemsCategoryAdapter
import com.plutecoder.itworld.databinding.FragmentRelatedItemsBinding
import com.plutecoder.itworld.databinding.RelatedItemDialogViewBinding
import com.plutecoder.itworld.models.CategoryItem
import com.plutecoder.itworld.viewModels.RelatedItemsViewModel

class RelatedItemDialog (private var categoryItemUid: String): DialogFragment(){
    private var _binding: RelatedItemDialogViewBinding? = null
    private val binding get() = _binding!!

    private lateinit var myAdapter: RelatedItemsCategoryAdapter
    private lateinit var itemList: ArrayList<Pair<String, List<CategoryItem>>>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RelatedItemDialogViewBinding.inflate(inflater, container, false)
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
        binding.btnclose.setOnClickListener{
            dismiss()

        }
    }

    private fun setUpRecyclerView() {
        binding.rvRelateditem.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        myAdapter = RelatedItemsCategoryAdapter(requireContext(), itemList)
        binding.rvRelateditem.adapter = myAdapter
    }
    override fun onStart() {
        super.onStart()
        //dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        // dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // Make background transparent
        dialog?.window?.let { window ->
            // Set dialog width to match parent and height to wrap content
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            // Make background transparent
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        }
    }

}