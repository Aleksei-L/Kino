package com.example.kino.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kino.data.Movie
import com.example.kino.repo.MoviesRepo
import com.squareup.moshi.Moshi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val repo: MoviesRepo) : ViewModel() {
	private val prData = MutableLiveData<Movie?>()
	private val moshi = Moshi.Builder().build()
	val data = prData

	fun getMovieById(id: Int) = viewModelScope.launch {
		val json = repo.getMovieDataById(id)
		val jsonAdapter = moshi.adapter(Movie::class.java)
		val movie = jsonAdapter.fromJson(json)
		prData.postValue(movie)
	}

	override fun onCleared() {
		super.onCleared()
		viewModelScope.cancel()
	}
}
