package com.malakezzat.banquemisr.challenge05.model

data class MovieResponse(
    val dates: Dates = Dates("",""),
    val page: Long = 0L,
    val results: List<Result> = emptyList(),
    val total_pages: Long = 0L,
    val total_results: Long = 0L,
)

data class Dates(
    val maximum: String,
    val minimum: String,
)

data class Result(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Long>,
    val id: Long,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Long,
)
