package com.example.catapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.data.remote.dto.CategoryDto
import com.example.catapp.data.remote.dto.FilteredCatImageDto
import com.example.catapp.data.remote.toCatImage
import com.example.catapp.data.remote.toCategory
import com.example.catapp.domain.model.CatImage
import com.example.catapp.domain.model.Category
import com.example.catapp.domain.repository.CatappRepository
import com.example.catapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    val catRepository: CatappRepository
) : ViewModel() {

    val catImages: MutableLiveData<Resource<MutableList<CatImage>>> = MutableLiveData()
    val categories: MutableLiveData<Resource<MutableList<Category>>> = MutableLiveData()

    private var catImagesResponse: MutableList<FilteredCatImageDto>? = null
    private var categoriesResponse: MutableList<CategoryDto>? = null
    var catImagesPage = 1

    init {
        getCatImages()
        getCategories()
    }

    fun getCategories() = viewModelScope.launch {
        categories.postValue(Resource.Loading())
        val response = catRepository.getCategories()
        categories.postValue(handleCategoriesResponse(response))
    }

    private fun handleCategoriesResponse(
        response: Response<MutableList<CategoryDto>>
    ): Resource<MutableList<Category>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (categoriesResponse == null) {
                    categoriesResponse = resultResponse
                }
                return Resource.Success(
                    categoriesResponse?.map { it.toCategory() }?.toMutableList() ?:
                    resultResponse.map { it.toCategory() }.toMutableList()
                )
            }
        }
        return Resource.Error(response.message())
    }

    fun getCatImages() = viewModelScope.launch {
        catImages.postValue(Resource.Loading())
        val response = catRepository.getCatImages()
        catImages.postValue(handleCatImagesResponse(response))
    }

    private fun handleCatImagesResponse(
        response: Response<MutableList<FilteredCatImageDto>>
    ) : Resource<MutableList<CatImage>> {
        if (response.isSuccessful) {
            catImagesPage++
            Log.i("GalleryViewModel", "Page number is $catImagesPage")
            response.body()?.let { resultResponse ->
                if (catImagesResponse == null) {
                    catImagesResponse = resultResponse
                } else {
                    val oldImages = catImagesResponse
                    oldImages?.addAll(resultResponse)
                }
                return Resource.Success(
                    catImagesResponse?.map { it.toCatImage() }?.toMutableList() ?:
                    resultResponse.map { it.toCatImage() }.toMutableList()
                )
            }
        }
        return Resource.Error(response.message())
    }
}