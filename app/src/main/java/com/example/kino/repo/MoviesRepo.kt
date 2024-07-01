package com.example.kino.repo

import com.example.kino.data.Movie
import com.example.kino.paging.MovieSetPagingSource
import com.example.kino.util.Resource

class MoviesRepo(private val movieAPI: MovieAPI /*TODO DI*/) {
	suspend fun getMovieById(id: Int): Resource<Movie> {
		return movieAPI.getMovieById(id)
	}

	fun getTopMovies() = MovieSetPagingSource(movieAPI)
}
