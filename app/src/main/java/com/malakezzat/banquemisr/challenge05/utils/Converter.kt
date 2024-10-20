package com.malakezzat.banquemisr.challenge05.utils

import com.malakezzat.banquemisr.challenge05.data.local.MovieDB
import com.malakezzat.banquemisr.challenge05.data.local.MovieDetailsDB
import com.malakezzat.banquemisr.challenge05.model.MovieDetails
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

    fun convertMovieDetailsDBToMovieDetails(movieDetailsDB: MovieDetailsDB): MovieDetails {
        return MovieDetails(
            adult = false,
            backdrop_path = "",
            belongs_to_collection = null,
            budget = 0L,
            genres = Details.parseGenres(movieDetailsDB.genres),
            homepage = "",
            id = movieDetailsDB.id,
            imdb_id = "",
            original_language = "",
            original_title = movieDetailsDB.title,
            overview = movieDetailsDB.overview ?: "",
            popularity = 0.0,
            poster_path = movieDetailsDB.posterPath,
            production_companies = emptyList(),
            production_countries = emptyList(),
            release_date = movieDetailsDB.releaseDate ?: "",
            revenue = 0L,
            runtime = movieDetailsDB.runtime ?: 0L,
            spoken_languages = emptyList(),
            status = "",
            tagline = "",
            title = movieDetailsDB.title,
            video = false,
            vote_average = movieDetailsDB.voteAverage,
            vote_count = movieDetailsDB.voteCount
        )
    }

    fun convertMovieDetailsToMovieDetailsDB(movieDetails : MovieDetails): MovieDetailsDB {
        return MovieDetailsDB(
            id = movieDetails.id,
            title = movieDetails.title,
            overview = movieDetails.overview,
            posterPath = movieDetails.poster_path,
            genres = Details.formatGenres(movieDetails.genres),
            runtime = movieDetails.runtime,
            releaseDate = movieDetails.release_date,
            voteAverage = movieDetails.vote_average,
            voteCount = movieDetails.vote_count
        )
    }
}