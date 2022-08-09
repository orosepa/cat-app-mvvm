package com.example.catapp.data.remote

import com.example.catapp.data.remote.dto.BreedDto
import com.example.catapp.data.remote.dto.CatImageDto
import com.example.catapp.data.remote.dto.CategoryDto
import com.example.catapp.data.remote.dto.FilteredCatImageDto
import com.example.catapp.domain.model.Breed
import com.example.catapp.domain.model.CatImage
import com.example.catapp.domain.model.Category

fun CatImageDto.toCatImage() = CatImage(id, url)

fun BreedDto.toBreed() = Breed(
    id = id,
    name = name,
    alt_names = alt_names,
    origin = origin,
    image = image,
    description = description,
    energy_level = energy_level,
    life_span = life_span,
    weight = weight.metric,
    wikipedia_url = wikipedia_url
)

fun CategoryDto.toCategory() = Category(id, name)

fun FilteredCatImageDto.toCatImage() = CatImage(id, url)