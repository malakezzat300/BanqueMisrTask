package com.malakezzat.banquemisr.challenge05.data.remote

import com.malakezzat.banquemisr.challenge05.model.MovieDetails
import com.malakezzat.banquemisr.challenge05.model.MovieResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Path

interface MoviesRemoteDataSource {

    suspend fun getNowPlaying(): Flow<MovieResponse>
    suspend fun getPopular(): Flow<MovieResponse>
    suspend fun getUpcoming(): Flow<MovieResponse>
    suspend fun getMovieDetails(movieId : Long): Flow<MovieDetails>
}