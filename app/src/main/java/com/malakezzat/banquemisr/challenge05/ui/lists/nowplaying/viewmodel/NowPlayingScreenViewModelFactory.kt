package com.malakezzat.banquemisr.challenge05.ui.lists.nowplaying.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.malakezzat.banquemisr.challenge05.data.repo.MoviesRepository
import java.lang.IllegalArgumentException

class NowPlayingScreenViewModelFactory(private val repository: MoviesRepository):
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NowPlayingScreenViewModel::class.java)) {
            NowPlayingScreenViewModel(repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Class not found")
        }
    }
}