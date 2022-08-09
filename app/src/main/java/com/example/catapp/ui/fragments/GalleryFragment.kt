package com.example.catapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.catapp.R
import com.example.catapp.adapter.GalleryAdapter
import com.example.catapp.databinding.FragmentGalleryBinding
import com.example.catapp.ui.viewmodels.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint

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

        viewModel.catPhotos.observe(viewLifecycleOwner) { response ->
            galleryAdapter.differ.submitList(response)
        }
    }
    private fun setupRecyclerView() {
        galleryAdapter = GalleryAdapter()
        binding.rvGallery.apply {
            adapter = galleryAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }
}