package com.example.kino.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kino.repo.MoviesRepo

class MoviesListViewModelFactory(private val repo: MoviesRepo) : ViewModelProvider.Factory {

	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return MoviesListViewModel(repo) as T
	}
}
