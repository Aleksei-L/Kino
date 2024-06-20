package com.example.kino.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kino.data.Movie
import com.example.kino.data.MovieSet
import com.example.kino.repo.MoviesRepo
import com.squareup.moshi.Moshi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MoviesListViewModel(private val repo: MoviesRepo) : ViewModel() {
	private val prData = MutableLiveData<List<Movie?>?>()
	private val moshi = Moshi.Builder().build()
	val data = prData

	fun getTopMovies() = viewModelScope.launch {
		val json = repo.getTopMovies(1)
		val jsonAdapter = moshi.adapter(MovieSet::class.java)
		val movies = jsonAdapter.fromJson(json)
		prData.postValue(movies?.items)
	}

	override fun onCleared() {
		super.onCleared()
		viewModelScope.cancel()
	}
}
