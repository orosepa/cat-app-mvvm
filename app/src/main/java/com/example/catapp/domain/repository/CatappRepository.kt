package com.example.catapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.catapp.data.db.CatImageEntity
import com.example.catapp.data.remote.dto.BreedDto
import com.example.catapp.data.remote.dto.CategoryDto
import com.example.catapp.data.remote.dto.FilteredCatImageDto
import com.example.catapp.domain.model.CatImage
import retrofit2.Response

interface CatappRepository {

    // remote
    suspend fun getCatImages(categoryId: Int?): Response<MutableList<FilteredCatImageDto>>
    suspend fun getCategories(): Response<MutableList<CategoryDto>>
    suspend fun getBreeds(): Response<MutableList<BreedDto>>

    // db
    fun getLikedCatImages(): LiveData<List<CatImageEntity>>
    suspend fun insertCatImage(catImage: CatImage)
    suspend fun deleteCatImage(catImage: CatImage)
    fun findLikedCatImageById(id: String): LiveData<CatImageEntity>
}