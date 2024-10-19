package com.malakezzat.banquemisr.challenge05.ui.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.malakezzat.banquemisr.challenge05.data.repo.MoviesRepository
import java.lang.IllegalArgumentException

class DetailsScreenViewModelFactory(private val repository: MoviesRepository):
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DetailsScreenViewModel::class.java)) {
            DetailsScreenViewModel(repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Class not found")
        }
    }
}