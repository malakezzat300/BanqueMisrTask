package com.malakezzat.banquemisr.challenge05.ui.lists.popular.viewmodel

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
import org.junit.jupiter.api.Assertions.*
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

class PopularScreenViewModelTest : KoinTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: PopularScreenViewModel
    private val repository: FakeMoviesRepository = FakeMoviesRepository()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        startKoin {
            modules(module {
                single { repository }
                single { PopularScreenViewModel(get()) }
            })
        }
        viewModel = PopularScreenViewModel(repository)
    }

    @Test
    fun `test initial state`() {
        assertEquals(ApiState.Loading, viewModel.popular.value)
    }

    @Test
    fun `getPopularLocal returns success`() = runTest {
        val fakeMoviesList = listOf(FakeData.fakeMovieDB1, FakeData.fakeMovieDB2)
        val expectedResponse = MovieResponse(results = Converter.convertMovieDBToResults(fakeMoviesList))

        viewModel.getPopularLocal()

        testScheduler.advanceUntilIdle()

        assertEquals(ApiState.Success(expectedResponse), viewModel.popular.value)
    }

    @Test
    fun `getPopularLocal retrieves movies and updates state to success`() = runTest {
        val fakeMoviesList = listOf(FakeData.fakeMovieDB1, FakeData.fakeMovieDB2)
        val expectedResponse = MovieResponse(results = Converter.convertMovieDBToResults(fakeMoviesList))


        viewModel.getPopularLocal()

        advanceUntilIdle()

        assertEquals(ApiState.Success(expectedResponse), viewModel.popular.value)
    }

    @Test
    fun `getPopular returns success`() = runTest {

        val fakeResults = listOf(FakeData.fakeResult1, FakeData.fakeResult2)
        val expectedResponse = MovieResponse(results = fakeResults)


        viewModel.getPopular()

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(ApiState.Success(expectedResponse), viewModel.popular.value)
    }

    @Test
    fun `getPopular handles error`() = runTest {
        repository.shouldReturnError = true
        viewModel.getPopular()

        testScheduler.advanceUntilIdle()

        assertEquals(ApiState.Error("Network error"), viewModel.popular.value)
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }
}


