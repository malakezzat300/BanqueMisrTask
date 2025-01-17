package com.malakezzat.banquemisr.challenge05.ui.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malakezzat.banquemisr.challenge05.utils.Converter
import com.malakezzat.banquemisr.challenge05.data.local.MovieDetailsDB
import com.malakezzat.banquemisr.challenge05.data.remote.ApiState
import com.malakezzat.banquemisr.challenge05.data.repo.MoviesRepository
import com.malakezzat.banquemisr.challenge05.model.MovieDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailsScreenViewModel(private val repository: MoviesRepository): ViewModel() {

    private val _movieDetails = MutableStateFlow<ApiState<MovieDetails>>(ApiState.Loading)
    val movieDetails: StateFlow<ApiState<MovieDetails>> get() = _movieDetails

    private val _movieDetailsLocal = MutableStateFlow<ApiState<MovieDetailsDB>>(ApiState.Loading)
    val movieDetailsLocal: StateFlow<ApiState<MovieDetailsDB>> get() = _movieDetailsLocal

    fun getMovieDetailsLocal(movieId : Long) {
        viewModelScope.launch {
            try {
                var moviesDetails: MovieDetailsDB
                repository.getMovieDetailsById(movieId)?.collect{ movies ->
                    moviesDetails = movies
                    _movieDetailsLocal.value = ApiState.Success(moviesDetails)
                }
            } catch (e: Exception) {
                _movieDetailsLocal.value = ApiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getMovieDetails(movieId : Long) {
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
                    repository.insertMovieDetails(Converter.convertMovieDetailsToMovieDetailsDB(movieDetails))
                }
        }
    }

}