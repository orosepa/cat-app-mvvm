package com.example.catapp.data.repository

import androidx.lifecycle.LiveData
import com.example.catapp.data.db.CatImageEntity
import com.example.catapp.data.db.LikedCatsDatabase
import com.example.catapp.data.remote.CatApi
import com.example.catapp.data.remote.dto.BreedDto
import com.example.catapp.data.toCatImageEntity
import com.example.catapp.domain.model.CatImage
import com.example.catapp.domain.repository.CatappRepository
import retrofit2.Response
import javax.inject.Inject

class CatappRepositoryImpl @Inject constructor (
    private val api: CatApi,
    private val db: LikedCatsDatabase
) : CatappRepository {

    // remote
    override suspend fun getCatImages(categoryId: Int?) = api.getCatImages(categoryId = categoryId)

    override suspend fun getCategories() = api.getCategories()

    override suspend fun getBreeds() = api.getBreeds()
    // db
    override fun getLikedCatImages(): LiveData<List<CatImageEntity>> =
        db.dao.getLikedCatImages()

    override suspend fun insertCatImage(catImage: CatImage) = db.dao.insertImage(catImage.toCatImageEntity())

    override suspend fun deleteCatImage(catImage: CatImage) = db.dao.deleteImage(catImage.toCatImageEntity())

    override fun findLikedCatImageById(id: String) = db.dao.findImageById(id)
}