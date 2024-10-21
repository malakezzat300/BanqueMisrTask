package com.malakezzat.banquemisr.challenge05.ui.lists.nowplaying.viewmodel

import com.malakezzat.banquemisr.challenge05.data.remote.ApiState
import com.malakezzat.banquemisr.challenge05.fakerepo.FakeData
import com.malakezzat.banquemisr.challenge05.fakerepo.FakeMoviesRepository
import com.malakezzat.banquemisr.challenge05.model.MovieResponse
import com.malakezzat.banquemisr.challenge05.utils.Converter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest


class NowPlayingScreenViewModelTest : KoinTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: NowPlayingScreenViewModel
    private val repository: FakeMoviesRepository = FakeMoviesRepository()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        startKoin {
            modules(module {
                single { repository }
                single { NowPlayingScreenViewModel(get()) }
            })
        }
        viewModel = NowPlayingScreenViewModel(repository)
    }

    @Test
    fun `test initial state`() {
        assertEquals(ApiState.Loading, viewModel.nowPlaying.value)
    }

    @Test
    fun `getNowPlayingLocal returns success`() = runTest {
        val fakeMoviesList = listOf(FakeData.fakeMovieDB1, FakeData.fakeMovieDB2)
        val expectedResponse = MovieResponse(results = Converter.convertMovieDBToResults(fakeMoviesList))

        viewModel.getNowPlayingLocal()

        testScheduler.advanceUntilIdle()

        assertEquals(ApiState.Success(expectedResponse), viewModel.nowPlaying.value)
    }

    @Test
    fun `getNowPlayingLocal retrieves movies and updates state to success`() = runTest {
        val fakeMoviesList = listOf(FakeData.fakeMovieDB1, FakeData.fakeMovieDB2)
        val expectedResponse = MovieResponse(results = Converter.convertMovieDBToResults(fakeMoviesList))


        viewModel.getNowPlayingLocal()

        advanceUntilIdle()

        assertEquals(ApiState.Success(expectedResponse), viewModel.nowPlaying.value)
    }

    @Test
    fun `getNowPlaying returns success`() = runTest {

        val fakeResults = listOf(FakeData.fakeResult1,FakeData.fakeResult2)
        val expectedResponse = MovieResponse(results = fakeResults)


        viewModel.getNowPlaying()

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(ApiState.Success(expectedResponse), viewModel.nowPlaying.value)
    }

    @Test
    fun `getNowPlaying handles error`() = runTest {
        repository.shouldReturnError = true
        viewModel.getNowPlaying()

        testScheduler.advanceUntilIdle()

        assertEquals(ApiState.Error("Network error"), viewModel.nowPlaying.value)
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }
}


