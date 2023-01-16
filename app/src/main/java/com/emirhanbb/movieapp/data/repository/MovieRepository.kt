package com.emirhanbb.movieapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.emirhanbb.movieapp.data.service.remote.ApiService
import javax.inject.Inject

class MovieRepository
@Inject
constructor(private val apiService: ApiService) {
    suspend fun getTopRated(page: Int) = apiService.getTopRated(page)
    suspend fun getPopular(page: Int) = apiService.getPopular(page)
    suspend fun getSearchResult(page: Int, query: String) = apiService.getSearchResult(page, query)
    suspend fun getUpcoming(page: Int) = apiService.getUpcoming(page)
    suspend fun getDetail(id: String) = apiService.getMovieDetail(id)
}