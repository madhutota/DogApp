package com.inditrade.dogapp.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.inditrade.dogapp.data.api.State
import com.inditrade.dogapp.data.models.DogFeedModel
import com.inditrade.dogapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogFeedViewModel @Inject constructor(var repository: DogFeedRepository) : BaseViewModel() {

    private var _dogFeedLiveData = MutableLiveData<State<DogFeedModel>>()
    var dogFeedLiveData: LiveData<State<DogFeedModel>> = _dogFeedLiveData

    private var _dogFeedBreedLiveData = MutableLiveData<State<DogFeedModel>>()
    var dogFeedBreedLiveData: LiveData<State<DogFeedModel>> = _dogFeedBreedLiveData

    init {
        fetchDogFeed()
    }

     fun fetchDogFeed() {
        viewModelScope.launch {
            repository.getDogFeed(false, "").collect {
                _dogFeedLiveData.value = it
            }
        }
    }

    fun fetchDogFeedByBreed(breed: String) {
        viewModelScope.launch {
            repository.getDogFeed(true, breed).collect {
                _dogFeedBreedLiveData.value = it
            }
        }
    }

}