package com.malakezzat.banquemisr.challenge05.fakerepo

import com.malakezzat.banquemisr.challenge05.data.local.MovieDB
import com.malakezzat.banquemisr.challenge05.data.local.MovieDetailsDB
import com.malakezzat.banquemisr.challenge05.model.Genre
import com.malakezzat.banquemisr.challenge05.model.MovieDetails
import com.malakezzat.banquemisr.challenge05.model.ProductionCompany
import com.malakezzat.banquemisr.challenge05.model.ProductionCountry
import com.malakezzat.banquemisr.challenge05.model.Result
import com.malakezzat.banquemisr.challenge05.model.SpokenLanguage

object FakeData {
    val fakeMovieDetails1 = MovieDetails(
        adult = false,
        backdrop_path = "/fakeBackdropPath.jpg",
        belongs_to_collection = null,
        budget = 100000000L,
        genres = listOf(
            Genre(id = 28, name = "Action"),
            Genre(id = 12, name = "Adventure")
        ),
        homepage = "https://www.fakemoviehomepage.com",
        id = 12345L,
        imdb_id = "tt1234567",
        original_language = "en",
        original_title = "Fake Movie Title",
        overview = "This is a fake overview for a sample movie.",
        popularity = 75.0,
        poster_path = "/fakePosterPath.jpg",
        production_companies = listOf(
            ProductionCompany(
                id = 1L,
                logo_path = "/fakeLogoPath.png",
                name = "Fake Production Company",
                origin_country = "US"
            )
        ),
        production_countries = listOf(
            ProductionCountry(
                iso_3166_1 = "US",
                name = "United States"
            )
        ),
        release_date = "2024-12-25",
        revenue = 500000000L,
        runtime = 120L,
        spoken_languages = listOf(
            SpokenLanguage(
                english_name = "English",
                iso_639_1 = "en",
                name = "English"
            ),
            SpokenLanguage(
                english_name = "Spanish",
                iso_639_1 = "es",
                name = "Español"
            )
        ),
        status = "Released",
        tagline = "This is a fake tagline for the movie.",
        title = "Fake Movie Title",
        video = false,
        vote_average = 8.5,
        vote_count = 10000L
    )

    val fakeMovieDetails2 = MovieDetails(
        adult = false,
        backdrop_path = "/anotherFakeBackdropPath.jpg",
        belongs_to_collection = null,
        budget = 50000000L,
        genres = listOf(
            Genre(id = 35, name = "Comedy"),
            Genre(id = 18, name = "Drama")
        ),
        homepage = "https://www.anotherfakemoviehomepage.com",
        id = 67890L,
        imdb_id = "tt7654321",
        original_language = "fr",
        original_title = "Titre du Film Factice",
        overview = "Ceci est un aperçu fictif d'un film échantillon.",
        popularity = 60.0,
        poster_path = "/anotherFakePosterPath.jpg",
        production_companies = listOf(
            ProductionCompany(
                id = 2L,
                logo_path = "/anotherFakeLogoPath.png",
                name = "Another Fake Production Company",
                origin_country = "FR"
            )
        ),
        production_countries = listOf(
            ProductionCountry(
                iso_3166_1 = "FR",
                name = "France"
            )
        ),
        release_date = "2025-05-15",
        revenue = 200000000L,
        runtime = 95L,
        spoken_languages = listOf(
            SpokenLanguage(
                english_name = "French",
                iso_639_1 = "fr",
                name = "Français"
            ),
            SpokenLanguage(
                english_name = "English",
                iso_639_1 = "en",
                name = "English"
            )
        ),
        status = "Post-production",
        tagline = "This is another fake tagline for the movie.",
        title = "Another Fake Movie Title",
        video = false,
        vote_average = 7.2,
        vote_count = 2500L
    )
    val fakeMovieDetailsDB = MovieDetailsDB(
        id = 12345L,
        title = "Fake Movie Title",
        overview = "This is a fake overview for the movie stored in the database.",
        posterPath = "/fakePosterPath.jpg",
        genres = "Action, Adventure",
        runtime = 120L,
        releaseDate = "2024-12-25",
        voteAverage = 8.5,
        voteCount = 10000L
    )

    val fakeMovieDB1 = MovieDB(
        id = 1L,
        title = "Fake Movie Title",
        poster_path = "/fakePosterPath.jpg",
        releaseDate = "2024-12-25",
        type = "nowPlaying"
    )

    val fakeMovieDB2 = MovieDB(
        id = 2L,
        title = "Another Fake Movie Title",
        poster_path = "/anotherFakePosterPath.jpg",
        releaseDate = "2024-11-15",
        type = "upcoming"
    )

    val fakeMovieDBList = listOf(fakeMovieDB1, fakeMovieDB2)

    val fakeResult1 = Result(
        adult = false,
        backdrop_path = "/fakeBackdropPath1.jpg",
        genre_ids = listOf(28, 12), // Action, Adventure
        id = 101L,
        original_language = "en",
        original_title = "Fake Movie One",
        overview = "This is a fake overview for the first movie.",
        popularity = 85.0,
        poster_path = "/fakePosterPath1.jpg",
        release_date = "2024-06-15",
        title = "Fake Movie One",
        video = false,
        vote_average = 7.5,
        vote_count = 1500L
    )

    val fakeResult2 = Result(
        adult = false,
        backdrop_path = "/fakeBackdropPath2.jpg",
        genre_ids = listOf(35, 14), // Comedy, Fantasy
        id = 102L,
        original_language = "fr",
        original_title = "Film Fictif Deux",
        overview = "Ceci est un aperçu fictif du deuxième film.",
        popularity = 90.0,
        poster_path = "/fakePosterPath2.jpg",
        release_date = "2024-07-20",
        title = "Fake Movie Two",
        video = false,
        vote_average = 8.0,
        vote_count = 2000L
    )
}