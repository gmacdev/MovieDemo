package com.gmacv.moviedemo.data.api

import com.gmacv.moviedemo.data.model.credits.Credits
import com.gmacv.moviedemo.data.model.movies.Movies
import com.gmacv.moviedemo.data.model.reviews.Reviews
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Apis {

    @GET("movie/now_playing?api_key=47023c4b7da198512958c9c558c5f431")
    suspend fun getNowPlayingMovies(): Response<Movies>

    @GET("movie/{movie_id}?api_key=47023c4b7da198512958c9c558c5f431")
    suspend fun getMovieDetails(@Path("movie_id") id: Int): Response<Movies>

    @GET("movie/{movie_id}/reviews?api_key=47023c4b7da198512958c9c558c5f431")
    suspend fun getReviews(@Path("movie_id") id: Int): Response<Reviews>

    @GET("movie/{movie_id}/credits?api_key=47023c4b7da198512958c9c558c5f431")
    suspend fun getCredits(@Path("movie_id") id: Int): Response<Credits>

    @GET("movie/{movie_id}/similar?api_key=47023c4b7da198512958c9c558c5f431")
    suspend fun getSimilarMovies(@Path("movie_id") id: Int): Response<Movies>
}