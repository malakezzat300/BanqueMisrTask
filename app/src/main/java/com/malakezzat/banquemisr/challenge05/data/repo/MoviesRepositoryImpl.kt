package com.malakezzat.banquemisr.challenge05.data.repo

import com.malakezzat.banquemisr.challenge05.data.local.MovieDB
import com.malakezzat.banquemisr.challenge05.data.local.MovieDetailsDB
import com.malakezzat.banquemisr.challenge05.data.local.MoviesLocalDataSource
import com.malakezzat.banquemisr.challenge05.data.remote.MoviesRemoteDataSource
import com.malakezzat.banquemisr.challenge05.model.MovieDetails
import com.malakezzat.banquemisr.challenge05.model.MovieResponse
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl private constructor(
    private var moviesRemoteDataSource: MoviesRemoteDataSource,
    private var moviesLocalDataSource: MoviesLocalDataSource
):MoviesRepository {

    companion object{
        private var instance: MoviesRepositoryImpl? = null
        fun getInstance( moviesRemoteDataSource: MoviesRemoteDataSource,
                         moviesLocalDataSource: MoviesLocalDataSource
        )
                :MoviesRepositoryImpl{
            return instance ?: synchronized(this){
                val temp =MoviesRepositoryImpl(moviesRemoteDataSource,
                    moviesLocalDataSource
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

    override suspend fun insertMovie(movieDB: MovieDB) {
        return moviesLocalDataSource.insertMovie(movieDB)
    }

    override suspend fun insertMovies(movieDBList: List<MovieDB>) {
        return moviesLocalDataSource.insertMovies(movieDBList)
    }

    override suspend fun getNowPlayingMovies(): Flow<List<MovieDB>>? {
        return moviesLocalDataSource.getNowPlayingMovies()
    }

    override suspend fun getPopularMovies(): Flow<List<MovieDB>>? {
        return moviesLocalDataSource.getPopularMovies()
    }

    override suspend fun getUpcomingMovies(): Flow<List<MovieDB>>? {
        return moviesLocalDataSource.getUpcomingMovies()
    }

    override suspend fun insertMovieDetails(movieDetailsDB: MovieDetailsDB) {
        return moviesLocalDataSource.insertMovieDetails(movieDetailsDB)
    }

    override suspend fun getMovieDetailsById(id: Long): Flow<MovieDetailsDB>? {
        return moviesLocalDataSource.getMovieDetailsById(id)
    }


}