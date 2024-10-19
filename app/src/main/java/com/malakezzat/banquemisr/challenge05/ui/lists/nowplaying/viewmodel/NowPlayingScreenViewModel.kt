package com.malakezzat.banquemisr.challenge05.ui.lists.nowplaying.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malakezzat.banquemisr.challenge05.data.remote.ApiState
import com.malakezzat.banquemisr.challenge05.data.repo.MoviesRepository
import com.malakezzat.banquemisr.challenge05.model.MovieResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class NowPlayingScreenViewModel(private val repository: MoviesRepository): ViewModel() {
    private val _nowPlaying = MutableStateFlow<ApiState<MovieResponse>>(ApiState.Loading)
    val nowPlaying: StateFlow<ApiState<MovieResponse>> get() = _nowPlaying


    fun getNowPlaying() {
        viewModelScope.launch {
            repository.getNowPlaying()
                .onStart {
                    _nowPlaying.value = ApiState.Loading
                }
                .catch { e ->
                    _nowPlaying.value = ApiState.Error(e.message ?: "Unknown error")
                }
                .collect { moviesList ->
                    _nowPlaying.value = ApiState.Success(moviesList)
                }
        }
    }

}