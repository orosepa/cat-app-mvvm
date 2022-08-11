package com.example.catapp.domain.repository

import com.example.catapp.data.remote.dto.CategoryDto
import com.example.catapp.data.remote.dto.FilteredCatImageDto
import retrofit2.Response

interface CatappRepository {

    suspend fun getCatImages(): Response<MutableList<FilteredCatImageDto>>

    suspend fun getCategories(): Response<MutableList<CategoryDto>>
}