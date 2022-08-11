package com.example.catapp.data.repository

import com.example.catapp.data.remote.CatApi
import com.example.catapp.domain.repository.CatappRepository
import javax.inject.Inject

class CatappRepositoryImpl @Inject constructor (
    private val api: CatApi
) : CatappRepository {

    override suspend fun getCatImages() = api.getCatImages()

}