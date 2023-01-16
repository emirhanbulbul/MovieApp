package com.emirhanbb.movieapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhanbb.movieapp.data.model.response.movieresponse.MovieResponseResult
import com.emirhanbb.movieapp.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

const val FIRST_PAGE = 1

@HiltViewModel
class HomeViewModel
@Inject
constructor(private val repository: MovieRepository) : ViewModel() {
    //TOP RATED
    private var topRatedList = mutableListOf<MovieResponseResult>()
    val topRatedPage = MutableLiveData<Int>()
    private val _topRated = MutableLiveData<List<MovieResponseResult>>()
    val topRated: LiveData<List<MovieResponseResult>>
        get() = _topRated

    //POPULAR
    private val popularList = mutableListOf<MovieResponseResult>()
    val popularPage = MutableLiveData<Int>()
    private val _popular = MutableLiveData<List<MovieResponseResult>>()
    val popular: LiveData<List<MovieResponseResult>>
        get() = _popular

    //UPCOMING
    private val upcomingList = mutableListOf<MovieResponseResult>()
    val upcomingPage = MutableLiveData<Int>()
    private val _upcoming = MutableLiveData<List<MovieResponseResult>>()
    val upcoming: LiveData<List<MovieResponseResult>>
        get() = _upcoming

    //SEARCH
    private val _searchResult = MutableLiveData<List<MovieResponseResult>>()
    val searchResult: LiveData<List<MovieResponseResult>>
        get() = _searchResult

    val loading = MutableLiveData<Boolean>()

    init {
        topRatedPage.value = FIRST_PAGE
        popularPage.value = FIRST_PAGE
        upcomingPage.value = FIRST_PAGE
    }

    fun getTopRated() = viewModelScope.launch(Dispatchers.IO) {
        loading.postValue(true)
        repository.getTopRated(topRatedPage.value!!.toInt()).let { response ->
            if (response.isSuccessful) {
                topRatedList.addAll(response.body()!!.results)
                _topRated.postValue(topRatedList)
                loading.postValue(false)
            } else {
                Log.d("Tag", "getTopRated Error Response: ${response.message()}")
                loading.postValue(false)
            }
        }
    }

    fun getPopular() = viewModelScope.launch(Dispatchers.IO) {
        loading.postValue(true)
        popularPage
        repository.getPopular(popularPage.value!!.toInt()).let { response ->
            if (response.isSuccessful) {
                popularList.addAll(response.body()!!.results)
                _popular.postValue(popularList)
                loading.postValue(false)
            } else {
                Log.d("Tag", "getPopular Error Response: ${response.message()}")
                loading.postValue(false)

            }
        }
    }

    fun getUpcoming() = viewModelScope.launch(Dispatchers.IO) {
        loading.postValue(true)
        repository.getUpcoming(upcomingPage.value!!.toInt()).let { response ->
            if (response.isSuccessful) {
                upcomingList.addAll(response.body()!!.results)
                _upcoming.postValue(upcomingList)
                loading.postValue(false)
            } else {
                Log.d("Tag", "getUpcoming Error Response: ${response.message()}")
                loading.postValue(false)
            }
        }
    }


    fun getSearchResult(page: Int, query: String) = viewModelScope.launch {
        loading.postValue(true)
        repository.getSearchResult(page, query).let { response ->
            if (response.isSuccessful) {
                _searchResult.postValue(response.body()!!.results)
                loading.postValue(false)
            } else {
                Log.d("Tag", "getTopRated Error Response: ${response.code()}")
                loading.postValue(false)
            }
        }
    }

    fun pagination(page:Int) {
        when(page){
            1 -> topRatedPage.value = topRatedPage.value?.plus(1)
            2 -> popularPage.value = popularPage.value?.plus(1)
            3 -> upcomingPage.value = upcomingPage.value?.plus(1)
        }
    }
}
