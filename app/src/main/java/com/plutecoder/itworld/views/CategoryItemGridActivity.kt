package com.plutecoder.itworld.views

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.plutecoder.itworld.R
import com.plutecoder.itworld.adapters.CategoryItemGridAdapter
import com.plutecoder.itworld.databinding.CategoryItemGridBinding
import com.plutecoder.itworld.models.Category
import com.plutecoder.itworld.models.CategoryItem
import com.plutecoder.itworld.viewModels.CategoryItemsViewModel


class CategoryItemGridActivity : AppCompatActivity() {

    private lateinit var categoryItemGridAdapter: CategoryItemGridAdapter
    private lateinit var binding: CategoryItemGridBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CategoryItemGridBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this)[CategoryItemsViewModel::class.java]

        val category = intent.getSerializableExtra("category") as Category

        // Initialize the adapter
        binding.techrecycler.layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.grid_span_count))
        categoryItemGridAdapter = CategoryItemGridAdapter(this, ArrayList(), category.uid)
        binding.techrecycler.adapter = categoryItemGridAdapter

        //fill title bar
        binding.header.title.text = category.title
        binding.description.text = category.subtitle

        //for going back
        binding.header.backImageView.setOnClickListener { onBackPressed() }

        // Observe the data from ViewModel
        viewModel.categoryItems.observe(this) { items ->
            updateRecyclerView(items)
        }

        // Observe loading state
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Fetch the category items
        viewModel.fetchCategoryItems(this, category.uid!!)
    }

    private fun updateRecyclerView(items: List<CategoryItem>) {
        // Update the adapter's data and refresh the RecyclerView
        categoryItemGridAdapter.updateData(items)
    }


}