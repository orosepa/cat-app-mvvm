package com.example.catapp.data.remote.dto

import com.example.catapp.domain.model.CatImage

data class CatImageDto(
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)