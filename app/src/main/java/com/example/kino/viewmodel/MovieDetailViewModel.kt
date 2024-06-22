package com.example.kino.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kino.data.Movie
import com.example.kino.repo.MoviesRepo
import com.example.kino.util.Resource
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val repo: MoviesRepo) : ViewModel() {
	val data = MutableLiveData<Resource<Movie>>()

	fun getMovieById(id: Int) = viewModelScope.launch {
		data.postValue(Resource.Loading())
		val movieResource = repo.getMovieById(id)
		data.postValue(movieResource)
	}

	override fun onCleared() {
		super.onCleared()
		viewModelScope.cancel()
	}
}
