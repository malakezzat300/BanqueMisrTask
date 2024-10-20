package com.malakezzat.banquemisr.challenge05.ui.lists.nowplaying.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.malakezzat.banquemisr.challenge05.Utils.Converter
import com.malakezzat.banquemisr.challenge05.Utils.NetworkUtils
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

class NowPlayingScreenViewModel(private val repository: MoviesRepository): ViewModel() {

    private val _nowPlaying = MutableStateFlow<ApiState<MovieResponse>>(ApiState.Loading)
    val nowPlaying: StateFlow<ApiState<MovieResponse>> get() = _nowPlaying

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        refreshNowPlaying()
    }

    fun refreshNowPlaying() {
        getNowPlaying()
    }

    fun getNowPlayingLocal() {
        viewModelScope.launch {
            try {
                var moviesList = emptyList<MovieDB>()
                repository.getNowPlayingMovies()?.collect{ movies ->
                    moviesList = movies
                }

                if (moviesList.isNotEmpty()) {
                    _nowPlaying.value = ApiState.Success(
                        MovieResponse(
                            results = Converter.convertMovieDBToResults(moviesList)
                        )
                    )
                } else {
                    getNowPlaying()
                }
            } catch (e: Exception) {
                _nowPlaying.value = ApiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getNowPlaying() {
        viewModelScope.launch {
            repository.getNowPlaying()
                .onStart {
                    _nowPlaying.value = ApiState.Loading
                    _isRefreshing.value = true
                }
                .catch { e ->
                    _nowPlaying.value = ApiState.Error(e.message ?: "Unknown error")
                }
                .collect { moviesList ->
                    _nowPlaying.value = ApiState.Success(MovieResponse(results = emptyList()))
                    _nowPlaying.value = ApiState.Success(moviesList)
                    repository.insertMovies(Converter.convertResultsToMovieDB(moviesList.results,"nowPlaying"))
                }
            _isRefreshing.value = false
        }
    }




}