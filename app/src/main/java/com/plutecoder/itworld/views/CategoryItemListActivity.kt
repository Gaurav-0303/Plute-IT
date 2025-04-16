package com.plutecoder.itworld.views

import android.app.ProgressDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.plutecoder.itworld.R
import com.plutecoder.itworld.adapters.CategoryItemListAdapter
import com.plutecoder.itworld.databinding.CategoryItemListBinding
import com.plutecoder.itworld.models.Category
import com.plutecoder.itworld.models.CategoryItem
import com.plutecoder.itworld.viewModels.CategoryItemsViewModel

class CategoryItemListActivity : AppCompatActivity() {

    private lateinit var binding : CategoryItemListBinding
    private lateinit var categoryItemListAdapter: CategoryItemListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CategoryItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: CategoryItemsViewModel = ViewModelProvider(this)[CategoryItemsViewModel::class.java]

        val category = intent.getSerializableExtra("category") as Category

        val categoryId = intent.getStringExtra("categoryId")

        if(categoryId != null){
            // Initialize the adapter
            categoryItemListAdapter = CategoryItemListAdapter(this, ArrayList(), categoryId)
            binding.langrecycler.layoutManager = LinearLayoutManager(this)
            binding.langrecycler.adapter = categoryItemListAdapter


            //fill title bar
//        binding.header.title.text = category.title
//            binding.description.text = category.subtitle

//            supportActionBar?.apply {
//                title = category.title
//                setDisplayHomeAsUpEnabled(true) // shows back button
//            }

            //for going back
//        binding.header.backImageView.setOnClickListener { onBackPressed() }

            // Observe the data from ViewModel
            viewModel.categoryItems.observe(this) { items ->
                updateRecyclerView(items)
            }

            // Observe loading state
            viewModel.isLoading.observe(this) { isLoading ->
                binding.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
            }

            // Fetch the category items
            viewModel.fetchCategoryItems(this, categoryId)
        }
        else{
            // Initialize the adapter
            categoryItemListAdapter = CategoryItemListAdapter(this, ArrayList(), category.uid)
            binding.langrecycler.layoutManager = LinearLayoutManager(this)
            binding.langrecycler.adapter = categoryItemListAdapter


            //fill title bar
//        binding.header.title.text = category.title
            binding.description.text = category.subtitle

            supportActionBar?.apply {
                title = category.title
                setDisplayHomeAsUpEnabled(true) // shows back button
            }

            //for going back
//        binding.header.backImageView.setOnClickListener { onBackPressed() }

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


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.category_item_action_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_search -> {
                Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun updateRecyclerView(items: List<CategoryItem>) {
        // Update the adapter's data and refresh the RecyclerView
        categoryItemListAdapter.updateData(items)
    }
}