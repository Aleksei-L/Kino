package com.example.kino.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kino.data.Movie
import com.example.kino.repo.MoviesDbRepo
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class FavoriteMoviesViewModel(private val dbRepo: MoviesDbRepo) : ViewModel() {
	val isLoading = MutableLiveData<Boolean>()
	val data = MutableLiveData<List<Movie>>()

	fun getFavoriteMovies() = viewModelScope.launch {
		isLoading.postValue(true)
		dbRepo.getMoviesFromDatabase()
		isLoading.postValue(false)
	}

	override fun onCleared() {
		super.onCleared()
		viewModelScope.cancel()
	}
}
