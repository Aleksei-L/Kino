package com.example.kino.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kino.data.Movie
import com.example.kino.repo.MoviesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(private val repo: MoviesRepo) : ViewModel() {
	val pageData: Flow<PagingData<Movie>> = Pager(
		config = PagingConfig(pageSize = 20 /*TODO*/, enablePlaceholders = false),
		pagingSourceFactory = { repo.getTopMovies() }
	).flow.cachedIn(viewModelScope)
	val data = MutableLiveData<List<Movie>>()

	fun searchMovie(query: String) = viewModelScope.launch {
		data.postValue(repo.searchMoviesFromApi(query))
	}

	override fun onCleared() {
		super.onCleared()
		viewModelScope.cancel()
	}
}
