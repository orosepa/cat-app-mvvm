package com.example.catapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.catapp.R
import com.example.catapp.adapter.GalleryAdapter
import com.example.catapp.data.toCatImage
import com.example.catapp.databinding.FragmentLikedBinding
import com.example.catapp.ui.viewmodels.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikedFragment : Fragment(R.layout.fragment_liked) {

    private lateinit var binding: FragmentLikedBinding
    lateinit var galleryAdapter: GalleryAdapter
    private val viewModel: GalleryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLikedBinding.inflate(inflater, container, false)
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
                R.id.action_likedFragment_to_imageFragment,
                bundle
            )
        }

        viewModel.getLikedCatImages().observe(viewLifecycleOwner) { catImages ->
            if (catImages.isNotEmpty()) {
                binding.twLiked.visibility = View.GONE
                galleryAdapter.differ.submitList(catImages.map { it.toCatImage() })
            } else {
                binding.twLiked.visibility = View.VISIBLE
            }
        }
    }

    private fun setupRecyclerView() {
        galleryAdapter = GalleryAdapter()
        binding.rvLiked.apply {
            adapter = galleryAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }
}