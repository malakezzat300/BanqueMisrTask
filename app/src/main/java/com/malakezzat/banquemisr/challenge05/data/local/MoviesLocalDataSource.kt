package com.malakezzat.banquemisr.challenge05.data.local

interface MoviesLocalDataSource {

    suspend fun insertMovie(movieDB: MovieDB)
    suspend fun insertMovies(movieDBList: List<MovieDB>)
    suspend fun getNowPlayingMovies(): List<MovieDB>?
    suspend fun getPopularMovies(): List<MovieDB>?
    suspend fun getUpcomingMovies(): List<MovieDB>?

    suspend fun insertMovieDetails(movieDetailsDB: MovieDetailsDB)
    suspend fun getMovieDetailsById(id: Int): MovieDetailsDB?
}

