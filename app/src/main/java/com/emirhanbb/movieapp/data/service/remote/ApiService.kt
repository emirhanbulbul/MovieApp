package com.emirhanbb.movieapp.data.service.remote

import androidx.lifecycle.MutableLiveData
import com.emirhanbb.movieapp.data.model.response.detailresponse.DetailResponse
import com.emirhanbb.movieapp.data.model.response.movieresponse.MovieResponse
import com.emirhanbb.movieapp.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("movie/{id}?language=en-US")
    suspend fun getMovieDetail(
        @Path("id") id:String,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ):Response<DetailResponse?>
}