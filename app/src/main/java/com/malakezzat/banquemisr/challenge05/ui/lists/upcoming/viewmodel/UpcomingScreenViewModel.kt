package com.malakezzat.banquemisr.challenge05.ui.lists.upcoming.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malakezzat.banquemisr.challenge05.Utils.Converter
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

class UpcomingScreenViewModel(private val repository: MoviesRepository): ViewModel() {

    private val _upcoming = MutableStateFlow<ApiState<MovieResponse>>(ApiState.Loading)
    val upcoming: StateFlow<ApiState<MovieResponse>> get() = _upcoming

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        refreshUpcoming()
    }

    fun refreshUpcoming() {
        getUpcoming()
    }

    fun getUpcomingLocal() {
        viewModelScope.launch {
            try {
                var moviesList = emptyList<MovieDB>()
                repository.getNowPlayingMovies()?.collect{ movies ->
                    moviesList = movies
                }

                if (moviesList.isNotEmpty()) {
                    _upcoming.value = ApiState.Success(
                        MovieResponse(
                            results = Converter.convertMovieDBToResults(moviesList)
                        )
                    )
                } else {
                    getUpcoming()
                }
            } catch (e: Exception) {
                _upcoming.value = ApiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getUpcoming() {
        viewModelScope.launch {
            repository.getUpcoming()
                .onStart {
                    _upcoming.value = ApiState.Loading
                    _isRefreshing.value = true
                }
                .catch { e ->
                    _upcoming.value = ApiState.Error(e.message ?: "Unknown error")
                }
                .collect { moviesList ->
                    _upcoming.value = ApiState.Success(MovieResponse(results = emptyList()))
                    _upcoming.value = ApiState.Success(moviesList)
                    repository.insertMovies(Converter.convertResultsToMovieDB(moviesList.results,"upcoming"))
                }
            _isRefreshing.value = false
        }
    }
}