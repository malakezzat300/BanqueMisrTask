package com.malakezzat.banquemisr.challenge05.ui.lists.popular.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malakezzat.banquemisr.challenge05.utils.Converter
import com.malakezzat.banquemisr.challenge05.data.local.MovieDB
import com.malakezzat.banquemisr.challenge05.data.remote.ApiState
import com.malakezzat.banquemisr.challenge05.data.repo.MoviesRepository
import com.malakezzat.banquemisr.challenge05.model.MovieResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PopularScreenViewModel(private val repository: MoviesRepository): ViewModel() {

    private val _popular = MutableStateFlow<ApiState<MovieResponse>>(ApiState.Loading)
    val popular: StateFlow<ApiState<MovieResponse>> get() = _popular

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        refreshPopular()
    }

    fun refreshPopular() {
        getPopular()
    }

    fun getPopularLocal() {
        viewModelScope.launch {
            try {
                var moviesList = emptyList<MovieDB>()
                repository.getPopularMovies()?.collect{ movies ->
                    moviesList = movies
                }

                if (moviesList.isNotEmpty()) {
                    _popular.value = ApiState.Success(
                        MovieResponse(
                            results = Converter.convertMovieDBToResults(moviesList)
                        )
                    )
                } else {
                    getPopular()
                }
            } catch (e: Exception) {
                _popular.value = ApiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getPopular() {
        viewModelScope.launch {
            repository.getPopular()
                .onStart {
                    _popular.value = ApiState.Loading
                    _isRefreshing.value = true
                }
                .catch { e ->
                    _popular.value = ApiState.Error(e.message ?: "Unknown error")
                }
                .collect { moviesList ->
                    _popular.value = ApiState.Success(MovieResponse(results = emptyList()))
                    _popular.value = ApiState.Success(moviesList)
                    repository.insertMovies(Converter.convertResultsToMovieDB(moviesList.results,"popular"))
                }
            _isRefreshing.value = false
        }
    }
}