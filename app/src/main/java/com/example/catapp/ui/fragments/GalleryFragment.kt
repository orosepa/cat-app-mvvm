package com.example.catapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catapp.R
import com.example.catapp.adapter.GalleryAdapter
import com.example.catapp.databinding.FragmentGalleryBinding
import com.example.catapp.ui.viewmodels.GalleryViewModel
import com.example.catapp.util.Constants
import com.example.catapp.util.Resource
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    val TAG = "GalleryFragment"

    private lateinit var binding: FragmentGalleryBinding
    lateinit var galleryAdapter: GalleryAdapter
    private val viewModel: GalleryViewModel by viewModels()

    var isLoading = false
    var isScrolling = false
    var checkedCategoryId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        galleryAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("CatImage", it)
            }
            findNavController().navigate(
                R.id.action_galleryFragment_to_imageFragment,
                bundle
            )
        }

        loadImages()
        createChipGroup()
    }

    private fun loadImages() {
        viewModel.catImages.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        galleryAdapter.differ.submitList(response.data)
                        binding.cgCategories.visibility = View.VISIBLE
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.data?.let { message ->
                        Log.e(TAG, "An Error Occured: $message")
                    }
                }
                is Resource.Loading -> showProgressBar()
            }
        }
    }

    private fun createChipGroup() {
        viewModel.categories.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Success -> {
                    response.data?.forEach { category ->
                        val chip = Chip(context, null, com.google.android.material.R.style.Widget_MaterialComponents_Chip_Choice)
                        chip.text = category.name
                        chip.textSize = 18f
                        chip.isCheckable = true
                        chip.isCheckedIconVisible = false
                        chip.setOnClickListener {
                            if (!chip.isChecked) {
                                chip.isChecked = true
                            }
                        }
                        chip.setOnCloseIconClickListener {
                            chip.isChecked = false
                            viewModel.categoryId.postValue(null)
                            viewModel.getCatImages(null)

                        }
                        chip.setOnCheckedChangeListener {selectedChip, isChecked ->
                            chip.isCloseIconVisible = isChecked
                            viewModel.catImages.postValue(Resource.Loading())
                            viewModel.oldImages?.clear()
                            if (selectedChip.isChecked) {
                                checkedCategoryId = viewModel.categories.value?.data?.find { it.name == selectedChip.text}?.id
                                viewModel.categoryId.postValue(checkedCategoryId)
                                viewModel.getCatImages(checkedCategoryId)
                            }
                            Log.i(TAG, "Gallery adapter item count ${galleryAdapter.itemCount}")
                        }
                        binding.cgCategories.addView(chip)
                    }
                }
                is Resource.Error -> {
                    response.data?.let { message ->
                        Log.e(TAG, "An error occurred: $message")
                    }
                }
                is Resource.Loading -> Log.i(TAG, "Loading categories...")
            }
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE

            val shouldPaginate = !isLoading && isNotAtBeginning && isAtLastItem && isTotalMoreThanVisible

            if (shouldPaginate) {
                viewModel.getCatImages(checkedCategoryId)
                isScrolling = false
            } else {
                binding.rvGallery.setPadding(0, 0, 0, 0)
            }
        }
    }

    private fun showProgressBar() {
        binding.pbGallery.visibility = View.VISIBLE
        isLoading = true
    }
    private fun hideProgressBar() {
        binding.pbGallery.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun setupRecyclerView() {
        galleryAdapter = GalleryAdapter()
        binding.rvGallery.apply {
            adapter = galleryAdapter
            layoutManager = GridLayoutManager(activity, 2)
            addOnScrollListener(scrollListener)
        }
    }
}