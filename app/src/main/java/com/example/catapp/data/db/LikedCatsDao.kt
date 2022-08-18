package com.example.catapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LikedCatsDao {

    @Query("SELECT * FROM catimageentity")
    fun getLikedCatImages(): LiveData<List<CatImageEntity>>

    @Query("SELECT * FROM catimageentity WHERE id=:id")
    fun findImageById(id: String): LiveData<CatImageEntity>

    @Insert
    suspend fun insertImage(catImageEntity: CatImageEntity)

    @Delete
    suspend fun deleteImage(catImageEntity: CatImageEntity)
}