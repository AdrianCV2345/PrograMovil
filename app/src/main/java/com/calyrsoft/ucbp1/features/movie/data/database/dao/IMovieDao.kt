package com.calyrsoft.ucbp1.features.movie.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.calyrsoft.ucbp1.features.movie.data.database.entity.MovieEntity

@Dao
interface IMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(list: List<MovieEntity>)

    @Query("SELECT * FROM movies ORDER BY popularity DESC NULLS LAST, title ASC")
    suspend fun getAll(): List<MovieEntity>
}
