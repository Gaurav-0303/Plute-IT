package com.plutecoder.itworld.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.plutecoder.itworld.R
import com.plutecoder.itworld.adapters.MainGridAdapter
import com.plutecoder.itworld.databinding.HomeFragmentBinding
import com.plutecoder.itworld.models.Category
import com.plutecoder.itworld.viewModels.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var adapter: MainGridAdapter
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private lateinit var progressBar: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Apply the TechHubDark theme
        val contextThemeWrapper = ContextThemeWrapper(requireActivity(), R.style.ItemBg)
        val themedInflater = inflater.cloneInContext(contextThemeWrapper)

        binding = HomeFragmentBinding.inflate(themedInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize progress bar
        progressBar = ProgressDialog(requireContext()).apply {
            setMessage("Loading...")
            setCancelable(false)
            setProgressStyle(ProgressDialog.STYLE_SPINNER)
        }

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        // Initialize RecyclerView
        binding.gridRv.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = MainGridAdapter(requireContext(), emptyList())
        binding.gridRv.adapter = adapter

        // Observe ViewModel
        viewModel.categoriesList.observe(viewLifecycleOwner) { categories ->
            updateRecyclerView(categories)
        }

        // Observe loading state
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) progressBar.show() else progressBar.hide()
        }
    }

    private fun updateRecyclerView(categories: List<Category>) {
        adapter = MainGridAdapter(requireContext(), categories)
        binding.gridRv.adapter = adapter
    }
}
