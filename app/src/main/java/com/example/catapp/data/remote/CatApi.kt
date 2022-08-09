package com.example.catapp.data.remote

import com.example.catapp.data.remote.dto.CatImageDto
import retrofit2.http.GET

interface CatApi {

    @GET("/v1/images/search?limit=10")
    suspend fun getTenCats(): List<CatImageDto>
}