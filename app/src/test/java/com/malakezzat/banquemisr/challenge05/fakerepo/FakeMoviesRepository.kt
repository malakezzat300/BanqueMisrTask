package com.malakezzat.banquemisr.challenge05.fakerepo

import com.malakezzat.banquemisr.challenge05.data.local.MovieDB
import com.malakezzat.banquemisr.challenge05.data.local.MovieDetailsDB
import com.malakezzat.banquemisr.challenge05.data.repo.MoviesRepository
import com.malakezzat.banquemisr.challenge05.model.MovieDetails
import com.malakezzat.banquemisr.challenge05.model.MovieResponse
import com.malakezzat.banquemisr.challenge05.model.Result
import com.malakezzat.banquemisr.challenge05.utils.Converter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeMoviesRepository() : MoviesRepository {

    private val fakeNowPlayingMovies = listOf(
        FakeData.fakeResult1,
        FakeData.fakeResult2
    )

    private val fakePopularMovies = listOf(
        FakeData.fakeResult1,
        FakeData.fakeResult2
    )

    private val fakeUpcomingMovies = listOf(
        FakeData.fakeResult1,
        FakeData.fakeResult2
    )

    private val fakeNowPlayingMoviesDB = mutableListOf(
        FakeData.fakeMovieDB1,
        FakeData.fakeMovieDB2
    )

    private val fakePopularMoviesDB = listOf(
        FakeData.fakeMovieDB1,
        FakeData.fakeMovieDB2
    )

    private val fakeUpcomingMoviesDB = listOf(
        FakeData.fakeMovieDB1,
        FakeData.fakeMovieDB2
    )

    private val fakeMovieDetails = FakeData.fakeMovieDetails1

    private val movieDetailsList = mutableListOf<MovieDetailsDB>()

    private val fakeMovieDetailsList = listOf(FakeData.fakeMovieDetails1,FakeData.fakeMovieDetails2)

    var shouldReturnError = false

    override suspend fun getNowPlaying(): Flow<MovieResponse> {
        return flow {
            if (shouldReturnError) {
                throw Exception("Network error")
            } else {
                emit(MovieResponse(results = fakeNowPlayingMovies))
            }
        }
    }

    override suspend fun getPopular(): Flow<MovieResponse>  {
        return flow {
            if (shouldReturnError) {
                throw Exception("Network error")
            } else {
                emit(MovieResponse(results = fakePopularMovies))
            }
        }
    }

    override suspend fun getUpcoming(): Flow<MovieResponse> {
        return flow {
            if (shouldReturnError) {
                throw Exception("Network error")
            } else {
                emit(MovieResponse(results = fakeUpcomingMovies))
            }
        }
    }

    override suspend fun getMovieDetails(movieId: Long): Flow<MovieDetails> {
        return flow {
            val movieDetails = fakeMovieDetailsList.find { it.id == movieId }

            if (movieDetails != null) {
                emit(movieDetails)
            } else {
                throw Exception("Movie details not found for id: $movieId")
            }
        }
    }

    override suspend fun insertMovie(movieDB: MovieDB) {
        // No-op for fake repository
    }

    override suspend fun insertMovies(movieDBList: List<MovieDB>) {
        // No-op for fake repository
    }

    override suspend fun getNowPlayingMovies(): Flow<List<MovieDB>>? {
        return flowOf(fakeNowPlayingMoviesDB)
    }

    override suspend fun getPopularMovies(): Flow<List<MovieDB>>? {
        return flowOf(fakePopularMoviesDB)
    }

    override suspend fun getUpcomingMovies(): Flow<List<MovieDB>>? {
        return flowOf(fakeUpcomingMoviesDB)
    }



    override suspend fun insertMovieDetails(movieDetailsDB: MovieDetailsDB) {
        movieDetailsList.add(movieDetailsDB)
    }

    override suspend fun getMovieDetailsById(id: Long): Flow<MovieDetailsDB>? {
        return flow {
            val movieDetails = movieDetailsList.find { it.id == id }
            if (movieDetails != null) {
                emit(movieDetails)
            } else {
                throw Exception("Movie with id $id not found.")
            }
        }
    }
}