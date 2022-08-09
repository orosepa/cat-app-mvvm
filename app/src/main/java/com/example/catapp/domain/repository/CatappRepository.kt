package com.example.catapp.domain.repository

import com.example.catapp.domain.model.CatImage

interface CatappRepository {

    suspend fun getTenCats(): List<CatImage>
}