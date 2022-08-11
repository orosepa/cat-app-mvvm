package com.example.catapp.data.remote.dto

data class FilteredCatImageDto(
    val breeds: List<BreedDto>?,
    val categories: List<CategoryDto>?,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)