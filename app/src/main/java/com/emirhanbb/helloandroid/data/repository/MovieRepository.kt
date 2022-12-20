package com.emirhanbb.helloandroid.data.repository

import com.emirhanbb.helloandroid.data.service.remote.ApiService
import javax.inject.Inject

class MovieRepository
@Inject
constructor(private val apiService: ApiService) {
    suspend fun getTopRated(page: Int) = apiService.getTopRated(page)
    suspend fun getPopular(page: Int) = apiService.getPopular(page)
    suspend fun getSearchResult(page: Int, query: String) = apiService.getSearchResult(page, query)
    suspend fun getUpcoming(page: Int) = apiService.getUpcoming(page)
}