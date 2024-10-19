package com.malakezzat.banquemisr.challenge05.ui

import kotlinx.serialization.Serializable

@Serializable
object Main

@Serializable
object NowPlayingScreen

@Serializable
object PopularScreen

@Serializable
object UpcomingScreen

@Serializable
data class DetailsScreen(
    val movieId : Long
)
