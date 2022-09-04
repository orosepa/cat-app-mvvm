package com.example.catapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catapp.R
import com.example.catapp.adapter.BreedsAdapter
import com.example.catapp.databinding.FragmentBreedsBinding
import com.example.catapp.ui.viewmodels.BreedsViewModel
import com.example.catapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedsFragment : Fragment(R.layout.fragment_breeds) {

    val TAG = "BreedsFragment"

    private lateinit var binding: FragmentBreedsBinding
    lateinit var breedsAdapter: BreedsAdapter
    private val viewModel: BreedsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBreedsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        viewModel.breeds.observe(viewLifecycleOwner) {response ->
            when (response) {
                is Resource.Success -> {
                    binding.pbBreeds.visibility = View.INVISIBLE
                    response.data?.let {
                        breedsAdapter.differ.submitList(response.data)
                    }
                }
                is Resource.Error -> {
                    binding.pbBreeds.visibility = View.VISIBLE
                    response.data?.let { message ->
                        Log.e(TAG, "An Error Occured: $message")
                    }
                }
                is Resource.Loading -> {
                    binding.pbBreeds.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        breedsAdapter = BreedsAdapter()
        binding.rvBreeds.apply {
            adapter = breedsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}