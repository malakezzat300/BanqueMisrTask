package com.malakezzat.banquemisr.challenge05.ui

import kotlinx.serialization.Serializable

@Serializable
object Main

@Serializable
object ListScreen

@Serializable
data class DetailsScreen(
    val movieId : Long
)
