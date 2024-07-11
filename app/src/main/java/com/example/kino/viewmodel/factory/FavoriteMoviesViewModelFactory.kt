package com.example.kino.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kino.repo.MoviesDbRepo
import com.example.kino.viewmodel.FavoriteMoviesViewModel

class FavoriteMoviesViewModelFactory(private val dbRepo: MoviesDbRepo) : ViewModelProvider.Factory {

	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return FavoriteMoviesViewModel(dbRepo) as T
	}
}
