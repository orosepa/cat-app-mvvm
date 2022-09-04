package com.example.catapp.data.remote

import com.example.catapp.data.remote.dto.BreedDto
import com.example.catapp.data.remote.dto.CategoryDto
import com.example.catapp.data.remote.dto.FilteredCatImageDto
import com.example.catapp.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {

    @GET("/v1/images/search")
    suspend fun getCatImages(
        @Query("limit") limit: Int = Constants.QUERY_PAGE_SIZE,
        @Query("page") page: Int = 1,
        @Query("category_ids") categoryId: Int?,
        @Query("api_key") apiKey: String = Constants.CATS_API
    ): Response<MutableList<FilteredCatImageDto>>

    @GET("/v1/categories")
    suspend fun getCategories(): Response<MutableList<CategoryDto>>

    @GET("/v1/breeds")
    suspend fun getBreeds(): Response<MutableList<BreedDto>>
}