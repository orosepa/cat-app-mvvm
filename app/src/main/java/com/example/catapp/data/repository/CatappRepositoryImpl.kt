package com.example.catapp.data.repository

import com.example.catapp.data.db.LikedCatsDatabase
import com.example.catapp.data.remote.CatApi
import com.example.catapp.domain.repository.CatappRepository
import javax.inject.Inject

class CatappRepositoryImpl @Inject constructor (
    private val api: CatApi,
    private val db: LikedCatsDatabase
) : CatappRepository {

    // remote
    override suspend fun getCatImages(categoryId: Int?) = api.getCatImages(categoryId = categoryId)
    override suspend fun getCategories() = api.getCategories()

    // db
}