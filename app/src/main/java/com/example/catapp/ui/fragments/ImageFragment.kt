package com.example.catapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.catapp.R
import com.example.catapp.databinding.FragmentImageBinding
import com.example.catapp.ui.viewmodels.GalleryViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageFragment : Fragment(R.layout.fragment_image) {

    val args: ImageFragmentArgs by navArgs()

    private lateinit var binding: FragmentImageBinding
    private val viewModel: GalleryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var isLiked = false

        val catImage = args.catImage

        viewModel.findLikedCatById(catImage.id).observe(viewLifecycleOwner) {
            isLiked = it != null
            setFabIcon(isLiked)
        }

        Glide.with(this).load(catImage.url).into(binding.zoomageView)

        binding.fabImage.setOnClickListener {
            if (isLiked) {
                viewModel.dislikeCatImage(catImage)
                Snackbar.make(view, "Removed from favorite :(", Snackbar.LENGTH_SHORT).show()
            } else {
                viewModel.likeCatImage(catImage)
                Snackbar.make(view, "Added to favorite!", Snackbar.LENGTH_SHORT).show()
            }
            isLiked = !isLiked
            setFabIcon(isLiked)
        }
    }

    private fun setFabIcon(isLiked: Boolean) {
        if (isLiked) {
            Glide.with(this).load(R.drawable.ic_baseline_thumb_down_alt).into(binding.fabImage)
        } else {
            Glide.with(this).load(R.drawable.ic_baseline_favorite).into(binding.fabImage)
        }
    }
}