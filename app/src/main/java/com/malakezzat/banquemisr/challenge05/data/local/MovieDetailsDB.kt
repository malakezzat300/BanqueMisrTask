package com.malakezzat.banquemisr.challenge05.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_details_list")
data class MovieDetailsDB(
    @PrimaryKey val id: Long = 0L,
    val title: String = "",
    val overview: String? = "",
    val posterPath: String = "",
    val genres: String = "",
    val runtime: Long? = 0L ,
    val releaseDate: String? = "",
    val voteAverage: Double = 0.0,
    val voteCount: Long = 0L
)