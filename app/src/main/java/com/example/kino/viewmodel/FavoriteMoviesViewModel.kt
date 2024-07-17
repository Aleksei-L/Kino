package com.example.kino.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kino.data.Movie
import com.example.kino.repo.MoviesRepo
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class FavoriteMoviesViewModel(private val repo: MoviesRepo) : ViewModel() {
	val loadingState = MutableLiveData<Boolean>()
	val data = MutableLiveData<List<Movie>>()

	fun getFavoriteMovies() = viewModelScope.launch {
		loadingState.postValue(true)
		data.postValue(repo.getMoviesFromDatabase())
		loadingState.postValue(false)
	}

	override fun onCleared() {
		super.onCleared()
		viewModelScope.cancel()
	}
}
