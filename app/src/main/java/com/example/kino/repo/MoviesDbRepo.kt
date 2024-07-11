package com.example.kino.repo

import com.example.kino.data.Movie
import com.example.kino.db.MovieDao

//TODO объединить 2 репозитория вместе
class MoviesDbRepo(private val movieDao: MovieDao/*TODO DI*/) {
	fun getMoviesFromDatabase(): List<Movie> {
		return movieDao.getAllMovies()
	}
}
