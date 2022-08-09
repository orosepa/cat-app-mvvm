package com.example.catapp.data.remote.dto

data class FilteredCatImageDto(
    val breeds: List<Any>,
    val categories: List<Category>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)