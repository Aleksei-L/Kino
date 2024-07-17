package com.example.kino.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kino.repo.MoviesRepo
import com.example.kino.viewmodel.FavoriteMoviesViewModel

class FavoriteMoviesViewModelFactory(private val repo: MoviesRepo) : ViewModelProvider.Factory {

	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return FavoriteMoviesViewModel(repo) as T
	}
}
