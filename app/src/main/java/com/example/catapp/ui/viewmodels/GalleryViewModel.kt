package com.example.catapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.domain.model.CatImage
import com.example.catapp.domain.repository.CatappRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    val catRepository: CatappRepository
) : ViewModel() {

    val catPhotos: MutableLiveData<List<CatImage>> = MutableLiveData()

    init {
        getTenCats()
    }

    private fun getTenCats() = viewModelScope.launch {
        val response = catRepository.getTenCats()
        catPhotos.postValue(response)
    }
}