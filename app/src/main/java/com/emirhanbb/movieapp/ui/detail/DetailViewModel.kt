package com.emirhanbb.movieapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhanbb.movieapp.data.model.response.detailresponse.DetailResponse
import com.emirhanbb.movieapp.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject
constructor(private val repository: MovieRepository) : ViewModel() {
    private val _movieDetail = MutableLiveData<DetailResponse>()
    val movieDetail: LiveData<DetailResponse>
        get() = _movieDetail
    val loading = MutableLiveData<Boolean>()

    fun getMovieDetail(id: String) = viewModelScope.launch {
        loading.postValue(true)
        repository.getDetail(id).let { response ->
            if (response.isSuccessful) {
                _movieDetail.postValue(response.body())
                loading.postValue(false)
            } else {
                Log.d("Tag", "getDetail Error Response: ${response.message()}")
                loading.postValue(false)
            }
        }
    }
}