package com.malakezzat.banquemisr.challenge05.data.repo

import com.malakezzat.banquemisr.challenge05.data.local.MovieDB
import com.malakezzat.banquemisr.challenge05.data.local.MovieDetailsDB
import com.malakezzat.banquemisr.challenge05.model.MovieDetails
import com.malakezzat.banquemisr.challenge05.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun getNowPlaying(): Flow<MovieResponse>
    suspend fun getPopular(): Flow<MovieResponse>
    suspend fun getUpcoming(): Flow<MovieResponse>
    suspend fun getMovieDetails(movieId : Long): Flow<MovieDetails>

    suspend fun insertMovie(movieDB: MovieDB)
    suspend fun insertMovies(movieDBList: List<MovieDB>)
    suspend fun getNowPlayingMovies(): Flow<List<MovieDB>>?
    suspend fun getPopularMovies(): Flow<List<MovieDB>>?
    suspend fun getUpcomingMovies(): Flow<List<MovieDB>>?

    suspend fun insertMovieDetails(movieDetailsDB: MovieDetailsDB)
    suspend fun getMovieDetailsById(id: Long): Flow<MovieDetailsDB>?
}