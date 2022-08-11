package com.example.catapp.domain.repository

import com.example.catapp.data.remote.dto.CatImageDto
import retrofit2.Response

interface CatappRepository {

    suspend fun getTenCats(): Response<MutableList<CatImageDto>>
}