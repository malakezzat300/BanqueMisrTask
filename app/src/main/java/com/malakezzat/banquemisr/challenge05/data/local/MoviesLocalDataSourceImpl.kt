package com.malakezzat.banquemisr.challenge05.data.local

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MoviesLocalDataSourceImpl(
    appDatabase: AppDatabase
) : MoviesLocalDataSource
    //, IMoviesLocalDataSource testing
{
    private val tag = "MoviesLocalDataSourceImpl"
    private var movieDao : MovieDao = appDatabase.movieDao
    private var movieDetailsDao : MovieDetailsDao = appDatabase.movieDetailsDao
    override suspend fun insertMovie(movieDB: MovieDB) {
        movieDao.insertMovie(movieDB)
    }

    override suspend fun insertMovies(movieDBList: List<MovieDB>) {
        movieDao.insertMovies(movieDBList)
    }

    override suspend fun getNowPlayingMovies(): Flow<List<MovieDB>> = flow {
        val response = movieDao.getNowPlayingMovies()
        emit(response)
    }.catch { e ->
        Log.e(tag, "Error fetching movies", e)
        emit(emptyList())
    }

    override suspend fun getPopularMovies(): Flow<List<MovieDB>> = flow {
        val response = movieDao.getPopularMovies()
        emit(response)
    }.catch { e ->
        Log.e(tag, "Error fetching movies", e)
        emit(emptyList())
    }

    override suspend fun getUpcomingMovies(): Flow<List<MovieDB>> = flow {
        val response = movieDao.getUpcomingMovies()
        emit(response)
    }.catch { e ->
        Log.e(tag, "Error fetching movies", e)
        emit(emptyList())
    }

    override suspend fun insertMovieDetails(movieDetailsDB: MovieDetailsDB) {
        return movieDetailsDao.insertMovieDetails(movieDetailsDB)
    }

    override suspend fun getMovieDetailsById(id: Long): Flow<MovieDetailsDB> = flow {
        val response = movieDetailsDao.getMovieDetailsById(id)
        emit(response)
    }.catch { e ->
        Log.e(tag, "Error fetching movies", e)
        emit(MovieDetailsDB())
    }

}