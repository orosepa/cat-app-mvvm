package com.example.catapp.domain.model

import java.io.Serializable

data class CatImage(
    val id: String,
    val url: String
) : Serializable