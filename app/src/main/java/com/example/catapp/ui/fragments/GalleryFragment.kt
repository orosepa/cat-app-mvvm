package com.example.catapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.catapp.R
import com.example.catapp.adapter.GalleryAdapter
import com.example.catapp.databinding.FragmentGalleryBinding
import com.example.catapp.ui.viewmodels.GalleryViewModel
import com.example.catapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response
import java.util.*

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private lateinit var binding: FragmentGalleryBinding
    lateinit var galleryAdapter: GalleryAdapter
    private val viewModel: GalleryViewModel by viewModels()

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

        viewModel.catImages.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        galleryAdapter.differ.submitList(response.data)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.data?.let { message ->
                        Log.e("GalleryFragment", "An Error Occured: $message")
                    }
                }
                is Resource.Loading -> showProgressBar()
            }
        }
    }

    private fun showProgressBar() {
        binding.pbGallery.visibility = View.VISIBLE
    }
    private fun hideProgressBar() {
        binding.pbGallery.visibility = View.INVISIBLE
    }

    private fun setupRecyclerView() {
        galleryAdapter = GalleryAdapter()
        binding.rvGallery.apply {
            adapter = galleryAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }
}