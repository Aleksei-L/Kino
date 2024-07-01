package com.example.kino.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kino.data.Movie
import com.example.kino.repo.MoviesRepo
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow

class MoviesListViewModel(private val repo: MoviesRepo) : ViewModel() {
	val pageData: Flow<PagingData<Movie>> = Pager(
		config = PagingConfig(pageSize = 20 /*TODO*/, enablePlaceholders = false),
		pagingSourceFactory = { repo.getTopMovies() }
	).flow.cachedIn(viewModelScope)

	override fun onCleared() {
		super.onCleared()
		viewModelScope.cancel()
	}
}
