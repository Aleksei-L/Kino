package com.example.kino.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kino.data.MovieSet
import com.example.kino.repo.MoviesRepo
import com.example.kino.util.Resource
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MoviesListViewModel(private val repo: MoviesRepo) : ViewModel() {
	val data = MutableLiveData<Resource<MovieSet>>()

	fun getTopMovies(pageNumber: Int) = viewModelScope.launch {
		val movieSetResource = repo.getTopMovies(pageNumber)
		data.postValue(movieSetResource)
	}

	override fun onCleared() {
		super.onCleared()
		viewModelScope.cancel()
	}
}
