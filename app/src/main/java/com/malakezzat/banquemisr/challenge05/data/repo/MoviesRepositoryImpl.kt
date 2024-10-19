package com.malakezzat.banquemisr.challenge05.data.repo

import com.malakezzat.banquemisr.challenge05.data.remote.MoviesRemoteDataSource
import com.malakezzat.banquemisr.challenge05.model.MovieDetails
import com.malakezzat.banquemisr.challenge05.model.MovieResponse
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl private constructor(
    private var moviesRemoteDataSource: MoviesRemoteDataSource,
    //private var moviesLocalDataSource: MoviesLocalDataSource
):MoviesRepository {

    companion object{
        private var instance: MoviesRepositoryImpl? = null
        fun getInstance( moviesRemoteDataSource: MoviesRemoteDataSource,
                      //   moviesLocalDataSource: MoviesLocalDataSource
        )
                :MoviesRepositoryImpl{
            return instance ?: synchronized(this){
                val temp =MoviesRepositoryImpl(moviesRemoteDataSource,
              //      moviesLocalDataSource
                )
                instance = temp
                temp

            }
        }
    }

    override suspend fun getNowPlaying(): Flow<MovieResponse> {
        return moviesRemoteDataSource.getNowPlaying()
    }

    override suspend fun getPopular(): Flow<MovieResponse> {
        return moviesRemoteDataSource.getPopular()
    }

    override suspend fun getUpcoming(): Flow<MovieResponse> {
        return moviesRemoteDataSource.getUpcoming()
    }

    override suspend fun getMovieDetails(movieId: Long): Flow<MovieDetails> {
        return moviesRemoteDataSource.getMovieDetails(movieId)
    }


}