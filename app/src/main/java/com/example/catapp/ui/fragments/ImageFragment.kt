package com.example.catapp.ui.fragments

import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.catapp.R
import com.example.catapp.databinding.FragmentImageBinding

class ImageFragment : Fragment(R.layout.fragment_image) {

    val args: ImageFragmentArgs by navArgs()
    private lateinit var binding: FragmentImageBinding

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
        val catImage = args.catImage
        binding.zoomageView.apply {
            Glide.with(this).load(catImage.url).into(binding.zoomageView)
        }
        binding.fabImage.setOnClickListener {
            Toast.makeText(context, "fab clicked", Toast.LENGTH_SHORT).show()
        }

    }
}