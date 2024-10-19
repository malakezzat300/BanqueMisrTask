package com.malakezzat.banquemisr.challenge05.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieDB: MovieDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movieDBList: List<MovieDB>)

    @Query("SELECT * FROM MOVIE_LIST WHERE type = 'nowPlaying'")
    suspend fun getNowPlayingMovies(): List<MovieDB>?

    @Query("SELECT * FROM MOVIE_LIST WHERE type = 'popular'")
    suspend fun getPopularMovies(): List<MovieDB>?

    @Query("SELECT * FROM MOVIE_LIST WHERE type = 'upcoming'")
    suspend fun getUpcomingMovies(): List<MovieDB>?

}