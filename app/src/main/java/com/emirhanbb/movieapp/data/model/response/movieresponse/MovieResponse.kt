package com.emirhanbb.movieapp.data.model.response.movieresponse

data class MovieResponse(
    val page: Int,
    val results: List<MovieResponseResult>,
    val total_pages: Int,
    val total_results: Int
)