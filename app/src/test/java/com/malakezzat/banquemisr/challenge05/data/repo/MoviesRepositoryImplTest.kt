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
import kotlinx.coroutines.test.runTest
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
    fun `getNowPlaying returns movies successfully from remote`() = runTest {
        val nowPlayingMovies = MovieResponse(results = listOf())
        val nowPlayingMoviesFlow = flowOf(nowPlayingMovies)
        coEvery { remoteDataSource.getNowPlaying() } returns nowPlayingMoviesFlow

        val result = repository.getNowPlaying()

        assertEquals(nowPlayingMoviesFlow, result)
        coVerify { remoteDataSource.getNowPlaying() }
    }

    @Test
    fun `getPopular returns movies successfully from remote`() = runTest {
        val popularMovies = MovieResponse(results = listOf())
        val popularMoviesFlow = flowOf(popularMovies)
        coEvery { remoteDataSource.getPopular() } returns popularMoviesFlow

        val result = repository.getPopular()

        assertEquals(popularMoviesFlow, result)
        coVerify { remoteDataSource.getPopular() }
    }

    @Test
    fun `getUpcoming returns movies successfully from remote`() = runTest {
        val upcomingMovies = MovieResponse(results = listOf())
        val upcomingMoviesFlow = flowOf(upcomingMovies)
        coEvery { remoteDataSource.getUpcoming() } returns upcomingMoviesFlow

        val result = repository.getUpcoming()

        assertEquals(upcomingMoviesFlow, result)
        coVerify { remoteDataSource.getUpcoming() }
    }

    @Test
    fun `getMovieDetails returns details successfully from remote`() = runTest {
        val movieId = 1L
        val movieDetails = MovieDetails()
        val movieDetailsFlow = flowOf(movieDetails)
        coEvery { remoteDataSource.getMovieDetails(movieId) } returns movieDetailsFlow

        val result = repository.getMovieDetails(movieId)

        assertEquals(movieDetailsFlow, result)
        coVerify { remoteDataSource.getMovieDetails(movieId) }
    }

    @Test
    fun `insertMovie inserts movie to local database`() = runTest {
        val movie = FakeData.fakeMovieDB1
        coEvery { localDataSource.insertMovie(movie) } returns Unit

        repository.insertMovie(movie)

        coVerify { localDataSource.insertMovie(movie) }
    }

    @Test
    fun `insertMovies inserts multiple movies to local database`() = runTest {
        val movies = FakeData.fakeMovieDBList
        coEvery { localDataSource.insertMovies(movies) } returns Unit

        repository.insertMovies(movies)

        coVerify { localDataSource.insertMovies(movies) }
    }

    @Test
    fun `getNowPlayingMovies returns movies from local database`() = runTest {
        val nowPlayingMovies = listOf<MovieDB>()
        val nowPlayingMoviesFlow = flowOf(nowPlayingMovies)
        coEvery { localDataSource.getNowPlayingMovies() } returns nowPlayingMoviesFlow

        val result = repository.getNowPlayingMovies()

        assertEquals(nowPlayingMoviesFlow, result)
        coVerify { localDataSource.getNowPlayingMovies() }
    }

    @Test
    fun `getPopularMovies returns movies from local database`() = runTest {
        val popularMovies = listOf<MovieDB>()
        val popularMoviesFlow = flowOf(popularMovies)
        coEvery { localDataSource.getPopularMovies() } returns popularMoviesFlow

        val result = repository.getPopularMovies()

        assertEquals(popularMoviesFlow, result)
        coVerify { localDataSource.getPopularMovies() }
    }

    @Test
    fun `getUpcomingMovies returns movies from local database`() = runTest {
        val upcomingMovies = listOf<MovieDB>()
        val upcomingMoviesFlow = flowOf(upcomingMovies)
        coEvery { localDataSource.getUpcomingMovies() } returns upcomingMoviesFlow

        val result = repository.getUpcomingMovies()

        assertEquals(upcomingMoviesFlow, result)
        coVerify { localDataSource.getUpcomingMovies() }
    }

    @Test
    fun `insertMovieDetails inserts movie details to local database`() = runTest {
        val movieDetails = MovieDetailsDB()
        coEvery { localDataSource.insertMovieDetails(movieDetails) } returns Unit

        repository.insertMovieDetails(movieDetails)

        coVerify { localDataSource.insertMovieDetails(movieDetails) }
    }

    @Test
    fun `getMovieDetailsById returns movie details from local database`() = runTest {
        val movieId = 1L
        val movieDetails = MovieDetailsDB()
        val movieDetailsFlow = flowOf(movieDetails)
        coEvery { localDataSource.getMovieDetailsById(movieId) } returns movieDetailsFlow

        val result = repository.getMovieDetailsById(movieId)

        assertEquals(movieDetailsFlow, result)
        coVerify { localDataSource.getMovieDetailsById(movieId) }
    }

    @Test
    fun `getNowPlaying returns empty list when no movies are available`() = runTest {
        val emptyMovies = MovieResponse(results = listOf())
        val emptyMoviesFlow = flowOf(emptyMovies)
        coEvery { remoteDataSource.getNowPlaying() } returns emptyMoviesFlow

        val result = repository.getNowPlaying()

        assertEquals(emptyMoviesFlow, result)
        coVerify { remoteDataSource.getNowPlaying() }
    }

    @After
    fun teardown() {
        stopKoin()
    }
}
