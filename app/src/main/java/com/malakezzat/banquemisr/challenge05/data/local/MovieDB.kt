package com.malakezzat.banquemisr.challenge05.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_list")
data class MovieDB(
    @PrimaryKey val id: Long,
    val title: String,
    val poster_path: String,
    val releaseDate: String?,
    var type : String
)