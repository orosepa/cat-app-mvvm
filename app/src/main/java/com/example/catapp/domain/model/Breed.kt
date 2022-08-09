package com.example.catapp.domain.model

import com.example.catapp.data.remote.dto.CatImageDto
import com.example.catapp.data.remote.dto.Weight

data class Breed(
    val alt_names: String,
    val description: String,
    val energy_level: Int,
    val id: String,
    val image: CatImageDto,
    val life_span: String,
    val name: String,
    val origin: String,
    val weight: Weight,
    val wikipedia_url: String
)
