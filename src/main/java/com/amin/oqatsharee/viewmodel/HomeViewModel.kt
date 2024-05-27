package com.amin.oqatsharee.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amin.oqatsharee.models.AzanResponseModel
import com.amin.oqatsharee.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo : HomeRepository) : ViewModel() {

    val getAzanLiveData = MutableLiveData<AzanResponseModel>()
    val loading = MutableLiveData<Boolean>()
    val notFoundState = MutableLiveData<Boolean>()

    fun getAzan(token : String , city : String) = viewModelScope.launch {
        loading.postValue(true)
        val response = repo.getAzan(token , city)
        if (response.isSuccessful){
            if (response.body()?.result?.azanMaghreb?.isNotEmpty() == true){
                getAzanLiveData.postValue(response.body())
                notFoundState.postValue(false)
            }else{
                notFoundState.postValue(true)
            }
        }
        loading.postValue(false)
    }
}