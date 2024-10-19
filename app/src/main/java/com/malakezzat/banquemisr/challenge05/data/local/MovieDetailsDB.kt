package com.malakezzat.banquemisr.challenge05.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_details_list")
data class MovieDetailsDB(
    @PrimaryKey val id: Long,
    val title: String,
    val overview: String?,
    val posterPath: String,
    val genres: String,
    val runtime: Int?,
    val releaseDate: String?,
    val voteAverage: Double,
    val voteCount: Int
)