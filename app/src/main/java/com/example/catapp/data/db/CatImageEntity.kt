package com.example.catapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CatImageEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val url: String
)