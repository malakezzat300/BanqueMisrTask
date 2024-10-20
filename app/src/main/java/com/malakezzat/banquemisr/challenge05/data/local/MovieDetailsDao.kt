package com.malakezzat.banquemisr.challenge05.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(movieDetailsDB: MovieDetailsDB)

    @Query("SELECT * FROM MOVIE_DETAILS_LIST WHERE id = :id")
    suspend fun getMovieDetailsById(id: Long): MovieDetailsDB

}