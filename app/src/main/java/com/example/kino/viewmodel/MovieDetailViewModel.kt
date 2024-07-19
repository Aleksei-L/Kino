package com.example.kino.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kino.data.Movie
import com.example.kino.repo.MoviesRepo
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val repo: MoviesRepo) : ViewModel() {
	val loadingState = MutableLiveData<Boolean>()
	val isMovieInFavorites = MutableLiveData<Boolean>()
	val data = MutableLiveData<Movie?>()

	fun getMovieById(id: Int, needFreshData: Boolean) = viewModelScope.launch {
		loadingState.postValue(true)
		val movie = repo.getMovieById(id, needFreshData)
		data.postValue(movie)
		loadingState.postValue(false)
	}

	fun isMovieInDatabase(id: Int) = viewModelScope.launch {
		val movieInDatabase = repo.getMovieByIdFromDatabase(id)
		isMovieInFavorites.postValue(movieInDatabase != null)
	}

	fun addMovieToFavorite(movie: Movie) = viewModelScope.launch {
		repo.insertMovieToDatabase(movie)
	}

	fun removeMovieFromFavorite(movie: Movie) = viewModelScope.launch {
		repo.removeMovieFromDatabase(movie)
	}

	override fun onCleared() {
		super.onCleared()
		viewModelScope.cancel()
	}
}
