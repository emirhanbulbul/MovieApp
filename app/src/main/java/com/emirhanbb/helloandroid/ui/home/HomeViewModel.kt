package com.emirhanbb.helloandroid.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhanbb.helloandroid.data.model.MovieResponse
import com.emirhanbb.helloandroid.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(private val repository: MovieRepository) : ViewModel() {

    private val _topRated = MutableLiveData<MovieResponse>()
    val topRated: LiveData<MovieResponse>
        get() = _topRated

    private val _searchResult = MutableLiveData<MovieResponse>()
    val searchResult: LiveData<MovieResponse>
        get() = _searchResult

    private val _popular = MutableLiveData<MovieResponse>()
    val popular: LiveData<MovieResponse>
        get() = _popular

    private val _upcoming = MutableLiveData<MovieResponse>()
    val upcoming: LiveData<MovieResponse>
        get() = _upcoming

    init {
        getTopRated(1)
        getPopular(1)
        getUpcoming(1)
    }

    private fun getTopRated(page: Int) = viewModelScope.launch {
        repository.getTopRated(page).let { response ->
            if (response.isSuccessful) {
                _topRated.postValue(response.body())
            } else {
                Log.d("Tag", "getTopRated Error Response: ${response.message()}")
            }
        }
    }

    private fun getPopular(page: Int) = viewModelScope.launch {
        repository.getPopular(page).let { response ->
            if (response.isSuccessful) {
                _popular.postValue(response.body())
            } else {
                Log.d("Tag", "getPopular Error Response: ${response.message()}")
            }
        }
    }

    private fun getUpcoming(page: Int) = viewModelScope.launch {
        repository.getUpcoming(page).let { response ->
            if (response.isSuccessful) {
                _upcoming.postValue(response.body())
            } else {
                Log.d("Tag", "getUpcoming Error Response: ${response.message()}")
            }
        }
    }


    fun getSearchResult(page: Int, query: String) = viewModelScope.launch {
        repository.getSearchResult(page, query).let { response ->
            if (response.isSuccessful) {
                _searchResult.postValue(response.body())
            } else {
                Log.d("Tag", "getTopRated Error Response: ${response.message()}")
            }
        }
    }


}