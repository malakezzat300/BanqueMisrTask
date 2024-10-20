package com.malakezzat.banquemisr.challenge05.Utils

import com.malakezzat.banquemisr.challenge05.data.local.MovieDB
import com.malakezzat.banquemisr.challenge05.model.Result

object Converter {
    fun convertResultsToMovieDB(results: List<Result>, type: String): List<MovieDB> {
        return results.map { result ->
            MovieDB(
                id = result.id,
                title = result.title,
                releaseDate = result.release_date,
                type = type,
                poster_path = result.poster_path
            )
        }
    }

    fun convertMovieDBToResults(movieDBList: List<MovieDB>): List<Result> {
        return movieDBList.map { movieDB ->
            Result(
                adult = false,
                backdrop_path = "",
                genre_ids = emptyList(),
                id = movieDB.id,
                original_language = "",
                original_title = movieDB.title,
                overview = "",
                popularity = 0.0,
                poster_path = movieDB.poster_path,
                release_date = movieDB.releaseDate ?: "",
                title = movieDB.title,
                video = false,
                vote_average = 0.0,
                vote_count = 0
            )
        }
    }
}