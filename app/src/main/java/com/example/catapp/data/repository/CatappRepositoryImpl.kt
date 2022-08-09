package com.example.catapp.data.repository

import com.example.catapp.data.remote.CatApi
import com.example.catapp.data.remote.toCatImage
import com.example.catapp.domain.model.CatImage
import com.example.catapp.domain.repository.CatappRepository
import javax.inject.Inject

class CatappRepositoryImpl @Inject constructor (
    private val api: CatApi
) : CatappRepository {

    override suspend fun getTenCats(): List<CatImage> =
        api.getTenCats().map { it.toCatImage() }

}