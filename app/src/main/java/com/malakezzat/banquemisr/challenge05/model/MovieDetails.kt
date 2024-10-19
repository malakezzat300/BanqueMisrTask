package com.malakezzat.banquemisr.challenge05.model

data class MovieDetails(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val belongs_to_collection: Any? = null,
    val budget: Long = 0L,
    val genres: List<Genre> = emptyList(),
    val homepage: String = "",
    val id: Long = 0L,
    val imdb_id: String = "",
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    val production_companies: List<ProductionCompany> = emptyList(),
    val production_countries: List<ProductionCountry> = emptyList(),
    val release_date: String = "",
    val revenue: Long = 0L,
    val runtime: Long = 0L,
    val spoken_languages: List<SpokenLanguage> = emptyList(),
    val status: String = "",
    val tagline: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val vote_count: Long = 0L,
)

data class Genre(
    val id: Long,
    val name: String,
)

data class ProductionCompany(
    val id: Long,
    val logo_path: String?,
    val name: String,
    val origin_country: String,
)

data class ProductionCountry(
    val iso_3166_1: String,
    val name: String,
)

data class SpokenLanguage(
    val english_name: String,
    val iso_639_1: String,
    val name: String,
)
