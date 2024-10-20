package com.malakezzat.banquemisr.challenge05.data.remote

import com.malakezzat.banquemisr.challenge05.model.MovieDetails
import com.malakezzat.banquemisr.challenge05.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiService {

    @GET("now_playing")
    suspend fun getNowPlaying(): MovieResponse

    @GET("popular")
    suspend fun getPopular(): MovieResponse

    @GET("upcoming")
    suspend fun getUpcoming(): MovieResponse

    @GET("{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId : Long): MovieDetails
}