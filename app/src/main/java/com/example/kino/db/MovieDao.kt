package com.example.kino.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kino.data.Movie

@Dao
interface MovieDao {
	@Query("SELECT * FROM Movie")
	fun getAllMovies(): List<Movie>

	@Query("SELECT * FROM Movie WHERE kinopoiskId = :id")
	fun getMovieById(id: Int): Movie?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertMovie(movie: Movie)

	@Delete
	fun deleteMovie(movie: Movie)
}
