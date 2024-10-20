package com.malakezzat.banquemisr.challenge05.utils

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

    fun parseGenres(genresString: String): List<Genre> {
        return genresString.split(" - ").map { genreName ->
            Genre(id = 0L, name = genreName)
        }
    }
}