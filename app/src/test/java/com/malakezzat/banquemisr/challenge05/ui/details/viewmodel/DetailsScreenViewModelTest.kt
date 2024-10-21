package com.malakezzat.banquemisr.challenge05.ui.details.viewmodel

import com.malakezzat.banquemisr.challenge05.data.remote.ApiState
import com.malakezzat.banquemisr.challenge05.fakerepo.FakeData
import com.malakezzat.banquemisr.challenge05.fakerepo.FakeMoviesRepository
import com.malakezzat.banquemisr.challenge05.utils.Converter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.koin.test.inject
import org.koin.test.KoinTest

import org.junit.Test
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsScreenViewModelTest : KoinTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: DetailsScreenViewModel
    private val repository: FakeMoviesRepository = FakeMoviesRepository()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        startKoin {
            modules(module {
                single { repository }
                single { DetailsScreenViewModel(get()) }
            })
        }
        viewModel = DetailsScreenViewModel(repository)
    }


    @Test
    fun `getMovieDetailsLocal returns success`() = runTest {
        val movieId = FakeData.fakeMovieDetailsDB.id
        val expectedMovieDetails = FakeData.fakeMovieDetailsDB

        repository.insertMovieDetails(expectedMovieDetails)

        viewModel.getMovieDetailsLocal(movieId)

        testScheduler.advanceUntilIdle()

        assertEquals(ApiState.Success(expectedMovieDetails), viewModel.movieDetailsLocal.value)
    }

    @Test
    fun `getMovieDetailsLocal returns error`() = runTest {
        val movieId = 10L
        val errorMessage = "Movie with id $movieId not found."

        viewModel.getMovieDetailsLocal(movieId)

        testScheduler.advanceUntilIdle()

        assertEquals(ApiState.Error(errorMessage), viewModel.movieDetailsLocal.value)
    }

    @Test
    fun `getMovieDetails returns error`() = runTest {
        val movieId = 1L
        val errorMessage = "Movie details not found for id: $movieId"

        viewModel.getMovieDetails(movieId)

        testScheduler.advanceUntilIdle()

        val result = viewModel.movieDetails.value

        assertTrue(result is ApiState.Error)
        assertEquals(errorMessage, (result as ApiState.Error).message)
    }

    @Test
    fun `getMovieDetails returns success`() = runTest {
        val movieId = 12345L
        val expectedMovieDetails = FakeData.fakeMovieDetails1

        viewModel.getMovieDetails(movieId)

        testScheduler.advanceUntilIdle()

        assertEquals(ApiState.Success(expectedMovieDetails), viewModel.movieDetails.value)
    }


    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }
}
