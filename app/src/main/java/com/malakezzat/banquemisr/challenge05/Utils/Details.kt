package com.malakezzat.banquemisr.challenge05.Utils

import com.malakezzat.banquemisr.challenge05.model.Genre

object Details {
    fun formatRuntime(runtimeMinutes: Long): String {
        val hours = runtimeMinutes / 60
        val minutes = runtimeMinutes % 60
        return String.format("%dh %dm", hours, minutes)
    }

    fun formatGenres(genres: List<Genre>): String {
        return genres.joinToString(" - ") { it.name }
    }
}