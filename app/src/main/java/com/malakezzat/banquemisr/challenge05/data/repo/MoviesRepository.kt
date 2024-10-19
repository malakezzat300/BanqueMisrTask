package com.malakezzat.banquemisr.challenge05.data.repo

import com.malakezzat.banquemisr.challenge05.model.MovieDetails
import com.malakezzat.banquemisr.challenge05.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun getNowPlaying(): Flow<MovieResponse>
    suspend fun getPopular(): Flow<MovieResponse>
    suspend fun getUpcoming(): Flow<MovieResponse>
    suspend fun getMovieDetails(movieId : Long): Flow<MovieDetails>
}