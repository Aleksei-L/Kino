package com.example.kino.activity

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kino.adapter.MoviesAdapter
import com.example.kino.databinding.ActivitySearchBinding
import com.example.kino.db.MovieDatabase
import com.example.kino.repo.MovieAPI
import com.example.kino.repo.MoviesRepo
import com.example.kino.util.APIInstance
import com.example.kino.util.ProgressBar
import com.example.kino.viewmodel.SearchMovieViewModel
import com.example.kino.viewmodel.factory.SearchMovieViewModelFactory

class SearchActivity : AppCompatActivity(), ProgressBar {
	private lateinit var binding: ActivitySearchBinding
	private lateinit var vm: SearchMovieViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivitySearchBinding.inflate(layoutInflater)
		setContentView(binding.root)

		setSupportActionBar(binding.topAppBar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		vm = ViewModelProvider(
			this,
			SearchMovieViewModelFactory(
				MoviesRepo(
					MovieAPI(APIInstance.httpClient),
					MovieDatabase.getInstance(applicationContext).movieDao()
				)
			)
		)[SearchMovieViewModel::class.java]

		val moviesAdapter = MoviesAdapter()

		vm.data.observe(this) { newMoviesList ->
			moviesAdapter.differ.submitList(newMoviesList)
		}

		vm.loadingState.observe(this) { loadingState ->
			when (loadingState) {
				true -> showProgressBar(binding.progressBar)
				false -> hideProgressBar(binding.progressBar)
			}
		}

		binding.moviesList.apply {
			layoutManager = LinearLayoutManager(context)
			adapter = moviesAdapter
		}
	}

	override fun onStart() {
		super.onStart()

		if (Intent.ACTION_SEARCH == intent.action) {
			intent.getStringExtra(SearchManager.QUERY)?.let { query ->
				vm.getMoviesByName(query)
			}
		}
	}
}
