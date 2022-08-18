package com.example.catapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LikedCatsDao {

    @Query("SELECT * FROM catimageentity")
    fun getStations(): LiveData<List<CatImageEntity>>

    @Insert
    suspend fun insertImage(): Boolean

    @Delete
    suspend fun deleteImage(): Boolean
}