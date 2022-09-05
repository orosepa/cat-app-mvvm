package com.example.catapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.catapp.R
import com.example.catapp.databinding.FragmentOneBreedBinding

class OneBreedFragment : Fragment(R.layout.fragment_one_breed) {

    lateinit var binding: FragmentOneBreedBinding
    private val args: OneBreedFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOneBreedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val breed = args.breed
        val weight = getString(R.string.weight_value, breed.weight)
        val lifetime = getString(R.string.lifetime_value, breed.life_span)

        if (breed.image != null) {
            Glide.with(this).load(breed.image.url).into(binding.ivOneBreed)
        } else {
            binding.ivOneBreed.visibility = View.GONE
        }
        binding.twBreedName.text = breed.name
        binding.TwBreedDescription.text = breed.description
        binding.twBreedOrigin.text = breed.origin
        binding.twBreedWeight.text = weight
        binding.twBreedLifetime.text = lifetime
        if (breed.energy_level == null) {
            binding.llBreedEnergy.visibility = View.GONE
        } else {
            binding.rbBreedEnergy.rating = breed.energy_level.toFloat()
        }
        if (breed.wikipedia_url == null) {
            binding.llBreedWiki.visibility = View.GONE
        } else {
            binding.twBreedWiki.text = breed.wikipedia_url
        }
//        binding.twBreedWiki.text = breed.wikipedia_url

    }
}