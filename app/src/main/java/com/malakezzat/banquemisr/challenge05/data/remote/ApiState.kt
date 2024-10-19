package com.malakezzat.banquemisr.challenge05.data.remote

sealed class ApiState<out T> {
    data object Loading : ApiState<Nothing>()
    data class Success<out T>(val data: T) : ApiState<T>()
    data class Error(val message: String) : ApiState<Nothing>()
}