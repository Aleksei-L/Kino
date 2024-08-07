package com.example.kino.repo

import com.example.kino.data.Movie
import com.example.kino.db.MovieDao
import com.example.kino.paging.MovieSetPagingSource
import com.example.kino.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepo(
	private val movieAPI: MovieAPI,
	private val movieDao: MovieDao //TODO DI
) {
	suspend fun getMovieById(id: Int, needFreshData: Boolean): Movie? =
		withContext(Dispatchers.IO) {
			var data: Movie? = null
			if (needFreshData) {
				val res = getMovieByIdFromApi(id)
				if (res is Resource.Success)
					data = res.data!!
			} else {
				val isInDb = getMovieByIdFromDatabase(id)
				if (isInDb != null)
					data = isInDb
				else {
					val res = getMovieByIdFromApi(id)
					if (res is Resource.Success)
						data = res.data!!
				}
			}
			return@withContext data
		}

	suspend fun getMoviesFromDatabase(): List<Movie> = withContext(Dispatchers.IO) {
		return@withContext movieDao.getAllMovies()
	}

	private suspend fun getMovieByIdFromApi(id: Int): Resource<Movie> {
		return movieAPI.getMovieById(id)
	}

	suspend fun getMovieByIdFromDatabase(id: Int): Movie? = withContext(Dispatchers.IO) {
		return@withContext movieDao.getMovieById(id)
	}

	suspend fun insertMovieToDatabase(movie: Movie) = withContext(Dispatchers.IO) {
		movieDao.insertMovie(movie)
	}

	suspend fun removeMovieFromDatabase(movie: Movie) = withContext(Dispatchers.IO) {
		movieDao.deleteMovie(movie)
	}

	fun getTopMovies() = MovieSetPagingSource(movieAPI)
}
