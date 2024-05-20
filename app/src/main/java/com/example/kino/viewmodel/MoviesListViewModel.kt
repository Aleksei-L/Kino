package com.example.kino.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kino.data.Movie
import com.example.kino.repo.MoviesRepo
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MoviesListViewModel(private val repo: MoviesRepo) : ViewModel() {
	private val prData = MutableLiveData<List<Movie>>()
	val data = prData

	fun getMovieById(id: Int) = viewModelScope.launch {
		val json = repo.getMovieDataById(id)
		//TODO здесь мы парсим json и создаём объект фильма
		val title = "TEST_TITLE"
		val desc = "TEST_DESC"
		prData.postValue(listOf(Movie(title, json), Movie(title, json), Movie(title, json)))
	}

	override fun onCleared() {
		super.onCleared()
		viewModelScope.cancel()
	}
}
