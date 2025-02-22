package com.plutecoder.itworld.views

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.plutecoder.itworld.adapters.CategoryItemListAdapter
import com.plutecoder.itworld.databinding.LanguageScreenBinding
import com.plutecoder.itworld.models.Category
import com.plutecoder.itworld.models.CategoryItem
import com.plutecoder.itworld.viewModels.CategoryItemsViewModel

class LanguageActivity : AppCompatActivity() {

    private lateinit var viewModel: CategoryItemsViewModel
    private lateinit var binding : LanguageScreenBinding
    private lateinit var categoryItemListAdapter: CategoryItemListAdapter
    private lateinit var progressBar: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LanguageScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize progress bar
        progressBar = ProgressDialog(this).apply {
            setMessage("Loading...")
            setCancelable(false)
            setProgressStyle(ProgressDialog.STYLE_SPINNER)
        }

        viewModel = ViewModelProvider(this)[CategoryItemsViewModel::class.java]

        val category = intent.getSerializableExtra("category") as Category

        // Initialize the adapter
        categoryItemListAdapter = CategoryItemListAdapter(this, ArrayList(), category.uid)
        binding.langrecycler.layoutManager = LinearLayoutManager(this)
        binding.langrecycler.adapter = categoryItemListAdapter


        //fill title bar
        binding.header.title.text = category.title
        binding.description.text = category.subtitle

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
        categoryItemListAdapter.updateData(items)
    }
}