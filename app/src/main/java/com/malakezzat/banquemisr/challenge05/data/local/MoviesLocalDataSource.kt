package com.malakezzat.banquemisr.challenge05.data.local

import kotlinx.coroutines.flow.Flow

interface MoviesLocalDataSource {

    suspend fun insertMovie(movieDB: MovieDB)
    suspend fun insertMovies(movieDBList: List<MovieDB>)
    suspend fun getNowPlayingMovies(): Flow<List<MovieDB>>?
    suspend fun getPopularMovies(): Flow<List<MovieDB>>?
    suspend fun getUpcomingMovies(): Flow<List<MovieDB>>?

    suspend fun insertMovieDetails(movieDetailsDB: MovieDetailsDB)
    suspend fun getMovieDetailsById(id: Int): Flow<MovieDetailsDB>?
}

