package com.malakezzat.banquemisr.challenge05.ui.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malakezzat.banquemisr.challenge05.data.remote.ApiState
import com.malakezzat.banquemisr.challenge05.data.repo.MoviesRepository
import com.malakezzat.banquemisr.challenge05.model.MovieDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

open class DetailsScreenViewModel(private val repository: MoviesRepository): ViewModel() {

    private val _movieDetails = MutableStateFlow<ApiState<MovieDetails>>(ApiState.Loading)
    open val movieDetails: StateFlow<ApiState<MovieDetails>> get() = _movieDetails


    open fun getMovieDetails(movieId : Long) {
        viewModelScope.launch {
            repository.getMovieDetails(movieId)
                .onStart {
                    _movieDetails.value = ApiState.Loading
                }
                .catch { e ->
                    _movieDetails.value = ApiState.Error(e.message ?: "Unknown error")
                }
                .collect { movieDetails ->
                    _movieDetails.value = ApiState.Success(movieDetails)
                }
        }
    }

}