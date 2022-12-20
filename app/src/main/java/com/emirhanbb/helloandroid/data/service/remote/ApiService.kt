package com.emirhanbb.helloandroid.data.service.remote

import com.emirhanbb.helloandroid.data.model.MovieResponse
import com.emirhanbb.helloandroid.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/top_rated?language=en-US")
    suspend fun getTopRated(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<MovieResponse>

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<MovieResponse>

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<MovieResponse>

    @GET("search/movie?language=en-US")
    suspend fun getSearchResult(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<MovieResponse>
}