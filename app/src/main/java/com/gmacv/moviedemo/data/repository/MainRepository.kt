package com.gmacv.moviedemo.data.repository

import com.gmacv.moviedemo.data.api.Apis
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apis: Apis
) {
    suspend fun getNowPlayingMovies() = apis.getNowPlayingMovies()
    suspend fun getMovieDetails(id: Int) = apis.getMovieDetails(id)
    suspend fun getReviews(id: Int) = apis.getReviews(id)
    suspend fun getCredits(id: Int) = apis.getCredits(id)
    suspend fun getSimilarMovies(id: Int) = apis.getSimilarMovies(id)
}