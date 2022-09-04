package com.example.catapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.data.remote.dto.BreedDto
import com.example.catapp.data.toBreed
import com.example.catapp.domain.model.Breed
import com.example.catapp.domain.repository.CatappRepository
import com.example.catapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor(
    val catRepository: CatappRepository
) : ViewModel() {

    val breeds: MutableLiveData<Resource<MutableList<Breed>>> = MutableLiveData()
    private var breedsResponse: MutableList<BreedDto>? = null

    init {
        getBreeds()
    }

    fun getBreeds() = viewModelScope.launch {
        breeds.postValue(Resource.Loading())
        val response = catRepository.getBreeds()
        breeds.postValue(handleBreedsResponse(response))
    }
    private fun handleBreedsResponse(
        response: Response<MutableList<BreedDto>>
    ) : Resource<MutableList<Breed>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breedsResponse = resultResponse
                return Resource.Success(
                    resultResponse.map { it.toBreed() }.toMutableList()
                )
            }
        }
        return Resource.Error(response.message())
    }
}