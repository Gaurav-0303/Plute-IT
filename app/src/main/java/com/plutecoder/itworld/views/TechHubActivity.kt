package com.plutecoder.itworld.views

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.plutecoder.itworld.adapters.TechHubItemAdapter
import com.plutecoder.itworld.databinding.ActivityTechHubBinding
import com.plutecoder.itworld.models.Category
import com.plutecoder.itworld.models.CategoryItem
import com.plutecoder.itworld.viewModels.CategoryItemsViewModel

class TechHubActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTechHubBinding
    private lateinit var techHubItemAdapter: TechHubItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTechHubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this)[CategoryItemsViewModel::class.java]

        // Initialize the adapter
        techHubItemAdapter = TechHubItemAdapter(this, ArrayList())
        binding.othersRv.layoutManager = LinearLayoutManager(this)
        binding.othersRv.adapter = techHubItemAdapter

        val category = intent.getSerializableExtra("category") as Category

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
        techHubItemAdapter.updateData(items)
    }
}