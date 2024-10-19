package com.malakezzat.banquemisr.challenge05.ui.lists.upcoming.viewmodel

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

class UpcomingScreenViewModel(private val repository: MoviesRepository): ViewModel() {

    private val _upcoming = MutableStateFlow<ApiState<MovieResponse>>(ApiState.Loading)
    val upcoming: StateFlow<ApiState<MovieResponse>> get() = _upcoming

    fun getUpcoming() {
        viewModelScope.launch {
            repository.getUpcoming()
                .onStart {
                    _upcoming.value = ApiState.Loading
                }
                .catch { e ->
                    _upcoming.value = ApiState.Error(e.message ?: "Unknown error")
                }
                .collect { moviesList ->
                    _upcoming.value = ApiState.Success(moviesList)
                }
        }
    }

}