package com.malakezzat.banquemisr.challenge05.data.repo

import com.malakezzat.banquemisr.challenge05.data.local.MovieDB
import com.malakezzat.banquemisr.challenge05.data.local.MovieDetailsDB
import com.malakezzat.banquemisr.challenge05.data.local.MoviesLocalDataSource
import com.malakezzat.banquemisr.challenge05.data.remote.MoviesRemoteDataSource
import com.malakezzat.banquemisr.challenge05.fakerepo.FakeData
import com.malakezzat.banquemisr.challenge05.model.MovieDetails
import com.malakezzat.banquemisr.challenge05.model.MovieResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import kotlin.test.assertEquals

class MoviesRepositoryImplTest {

    private lateinit var repository: MoviesRepositoryImpl
    private lateinit var repository2: MoviesRepositoryImpl
    private val remoteDataSource: MoviesRemoteDataSource = mockk()
    private val localDataSource: MoviesLocalDataSource = mockk()

    @Before
    fun setup() {
        startKoin {
            modules(module {
                single { remoteDataSource }
                single { localDataSource }
            })
        }

        repository = MoviesRepositoryImpl(remoteDataSource, localDataSource)
        repository2 = MoviesRepositoryImpl.getInstance(remoteDataSource,localDataSource)
    }



    @Test
    fun getNowPlaying() = runBlocking {
        val nowPlayingMovies = MovieResponse(results = listOf())
        val nowPlayingMoviesFlow = flowOf(nowPlayingMovies)
        coEvery { remoteDataSource.getNowPlaying() } returns nowPlayingMoviesFlow

        val result = repository.getNowPlaying()

        assertEquals(nowPlayingMoviesFlow, result)
        coVerify { remoteDataSource.getNowPlaying() }
    }

    @Test
    fun getPopular() = runBlocking {
        val popularMovies = MovieResponse(results = listOf())
        val popularMoviesFlow = flowOf(popularMovies)
        coEvery { remoteDataSource.getPopular() } returns popularMoviesFlow

        val result = repository.getPopular()

        assertEquals(popularMoviesFlow, result)
        coVerify { remoteDataSource.getPopular() }
    }

    @Test
    fun getUpcoming() = runBlocking {
        val upcomingMovies = MovieResponse(results = listOf())
        val upcomingMoviesFlow = flowOf(upcomingMovies)
        coEvery { remoteDataSource.getUpcoming() } returns upcomingMoviesFlow

        val result = repository.getUpcoming()

        assertEquals(upcomingMoviesFlow, result)
        coVerify { remoteDataSource.getUpcoming() }
    }

    @Test
    fun getMovieDetails() = runBlocking {
        val movieId = 1L
        val movieDetails = MovieDetails()
        val movieDetailsFlow = flowOf(movieDetails)
        coEvery { remoteDataSource.getMovieDetails(movieId) } returns movieDetailsFlow

        val result = repository.getMovieDetails(movieId)

        assertEquals(movieDetailsFlow, result)
        coVerify { remoteDataSource.getMovieDetails(movieId) }
    }

    @Test
    fun insertMovie() = runBlocking {
        val movie = FakeData.fakeMovieDB1
        coEvery { localDataSource.insertMovie(movie) } returns Unit

        repository.insertMovie(movie)

        coVerify { localDataSource.insertMovie(movie) }
    }

    @Test
    fun insertMovies() = runBlocking {
        val movies = FakeData.fakeMovieDBList
        coEvery { localDataSource.insertMovies(movies) } returns Unit

        repository.insertMovies(movies)

        coVerify { localDataSource.insertMovies(movies) }
    }

    @Test
    fun getNowPlayingMovies() = runBlocking {
        val nowPlayingMovies = listOf<MovieDB>()
        val nowPlayingMoviesFlow = flowOf(nowPlayingMovies)
        coEvery { localDataSource.getNowPlayingMovies() } returns nowPlayingMoviesFlow

        val result = repository.getNowPlayingMovies()

        assertEquals(nowPlayingMoviesFlow, result)
        coVerify { localDataSource.getNowPlayingMovies() }
    }

    @Test
    fun getPopularMovies() = runBlocking {
        val popularMovies = listOf<MovieDB>()
        val popularMoviesFlow = flowOf(popularMovies)
        coEvery { localDataSource.getPopularMovies() } returns popularMoviesFlow

        val result = repository.getPopularMovies()

        assertEquals(popularMoviesFlow, result)
        coVerify { localDataSource.getPopularMovies() }
    }

    @Test
    fun getUpcomingMovies() = runBlocking {
        val upcomingMovies = listOf<MovieDB>()
        val upcomingMoviesFlow = flowOf(upcomingMovies)
        coEvery { localDataSource.getUpcomingMovies() } returns upcomingMoviesFlow

        val result = repository.getUpcomingMovies()

        assertEquals(upcomingMoviesFlow, result)
        coVerify { localDataSource.getUpcomingMovies() }
    }

//
    @Test
    fun insertMovieDetails() = runBlocking {
        val movieDetails = MovieDetailsDB()
        coEvery { localDataSource.insertMovieDetails(movieDetails) } returns Unit

        repository.insertMovieDetails(movieDetails)

        coVerify { localDataSource.insertMovieDetails(movieDetails) }
    }

    @Test
    fun getMovieDetailsById() = runBlocking {
        val movieId = 1L
        val movieDetails = MovieDetailsDB()
        val movieDetailsFlow = flowOf(movieDetails)
        coEvery { localDataSource.getMovieDetailsById(movieId) } returns movieDetailsFlow

        val result = repository2.getMovieDetailsById(movieId)

        assertEquals(movieDetailsFlow, result)
        coVerify { localDataSource.getMovieDetailsById(movieId) }
    }

    @After
    fun teardown() {
        stopKoin()
    }
}
