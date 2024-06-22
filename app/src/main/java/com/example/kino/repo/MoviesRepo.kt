package com.example.kino.repo

import com.example.kino.data.Movie
import com.example.kino.data.MovieSet
import com.example.kino.data.Resource

class MoviesRepo(private val movieAPI: MovieAPI /*TODO DI*/) {
	suspend fun getMovieById(id: Int): Resource<Movie> {
		return movieAPI.getMovieById(id)
	}

	suspend fun getTopMovies(pageNumber: Int): Resource<MovieSet> {
		return movieAPI.getTopMovies(pageNumber)
	}
}
