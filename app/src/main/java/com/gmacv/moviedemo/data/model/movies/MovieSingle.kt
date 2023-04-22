package com.gmacv.moviedemo.data.model.movies

data class MovieSingle(
    val id: Int,
    val title: String,
    val poster_path: String,
    val release_date: String,
    val overview: String = "",
    val vote_average: Float = 0F,
    val runtime: Int = 0
)
