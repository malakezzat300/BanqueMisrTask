package com.malakezzat.banquemisr.challenge05.ui.list.viewmodel

import android.util.Log
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

class ListScreenViewModel(private val repository: MoviesRepository): ViewModel() {
    private val _nowPlaying = MutableStateFlow<ApiState<MovieResponse>>(ApiState.Loading)
    val nowPlaying: StateFlow<ApiState<MovieResponse>> get() = _nowPlaying

    private val _popular = MutableStateFlow<ApiState<MovieResponse>>(ApiState.Loading)
    val popular: StateFlow<ApiState<MovieResponse>> get() = _popular

    private val _upcoming = MutableStateFlow<ApiState<MovieResponse>>(ApiState.Loading)
    val upcoming: StateFlow<ApiState<MovieResponse>> get() = _upcoming


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

    fun getPopular() {
        viewModelScope.launch {
            repository.getPopular()
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

    fun getUpcoming() {
        viewModelScope.launch {
            repository.getUpcoming()
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