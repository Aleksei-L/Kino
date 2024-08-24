package com.example.kino.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kino.data.Movie
import com.example.kino.repo.MoviesRepo
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class SearchMovieViewModel(private val repo: MoviesRepo) : ViewModel() {
	val loadingState = MutableLiveData<Boolean>()
	val data = MutableLiveData<List<Movie>>()

	fun getMoviesByName(name: String) = viewModelScope.launch {
		loadingState.postValue(true)
		val list = repo.getMoviesByName(name)
		data.postValue(list)
		loadingState.postValue(false)
	}

	override fun onCleared() {
		super.onCleared()
		viewModelScope.cancel()
	}
}
