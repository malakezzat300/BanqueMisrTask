package com.malakezzat.banquemisr.challenge05.data.remote

import android.util.Log
import com.malakezzat.banquemisr.challenge05.model.MovieDetails
import com.malakezzat.banquemisr.challenge05.model.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MoviesRemoteDataSourceImpl(private var movieApiService: MovieApiService):
    MoviesRemoteDataSource {

    private val TAG = "MoviesRemoteDataSourceImpl"
    companion object {
        private var instance: MoviesRemoteDataSourceImpl? = null
        fun getInstance(movieApiService: MovieApiService): MoviesRemoteDataSourceImpl {
            return instance ?: synchronized(this) {
                val temp = MoviesRemoteDataSourceImpl(movieApiService)
                instance = temp
                temp
            }
        }
    }

    override suspend fun getNowPlaying(): Flow<MovieResponse> = flow {
        val response = movieApiService.getNowPlaying()
        emit(response)
    }.catch { e ->
        Log.e(TAG, "Error fetching movies", e)
        throw e
    }

    override suspend fun getPopular(): Flow<MovieResponse>  = flow {
        val response = movieApiService.getPopular()
        emit(response)
    }.catch { e ->
        Log.e(TAG, "Error fetching movies", e)
        throw e
    }

    override suspend fun getUpcoming(): Flow<MovieResponse>  = flow {
        val response = movieApiService.getUpcoming()
        emit(response)
    }.catch { e ->
        Log.e(TAG, "Error fetching movies", e)
        throw e
    }

    override suspend fun getMovieDetails(movieId: Long): Flow<MovieDetails> = flow {
        val response = movieApiService.getMovieDetails(movieId)
        emit(response)
    }.catch { e ->
        Log.e(TAG, "Error fetching movie", e)
        throw e
    }
}