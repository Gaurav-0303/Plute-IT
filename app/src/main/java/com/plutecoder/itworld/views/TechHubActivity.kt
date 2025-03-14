package com.plutecoder.itworld.views

import android.app.ProgressDialog
import android.os.Bundle
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
    private lateinit var progressBar: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTechHubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize progress bar
        progressBar = ProgressDialog(this).apply {
            setMessage("Loading...")
            setCancelable(false)
            setProgressStyle(ProgressDialog.STYLE_SPINNER)
        }

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
            if (isLoading) progressBar.show() else progressBar.hide()
        }

        // Fetch the category items
        viewModel.fetchCategoryItems(this, category.uid!!)
    }

    private fun updateRecyclerView(items: List<CategoryItem>) {
        // Update the adapter's data and refresh the RecyclerView
        techHubItemAdapter.updateData(items)
    }
}