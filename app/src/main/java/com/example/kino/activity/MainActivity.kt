package com.example.kino.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kino.R
import com.example.kino.databinding.ActivityMainBinding
import com.example.kino.fragment.MoviesListFragment
import com.example.kino.repo.MovieAPI
import com.example.kino.repo.MoviesRepo
import com.example.kino.util.APIInstance
import com.example.kino.util.ProgressBar
import com.example.kino.util.Resource
import com.example.kino.viewmodel.MoviesListViewModel
import com.example.kino.viewmodel.MoviesListViewModelFactory
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), ProgressBar {
	private lateinit var binding: ActivityMainBinding
	private lateinit var vm: MoviesListViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		setSupportActionBar(binding.toolbar.root)

		vm = ViewModelProvider(
			this,
			MoviesListViewModelFactory(MoviesRepo(MovieAPI(APIInstance.httpClient)))
		)[MoviesListViewModel::class.java]

		val navController = findNavController(R.id.host_fragment)
		binding.bottomNav.setupWithNavController(navController)
	}

	override fun onStart() {
		super.onStart()

		val navHostFragment = supportFragmentManager.findFragmentById(R.id.host_fragment)
		val frag = navHostFragment?.childFragmentManager?.fragments?.get(0) as MoviesListFragment

		vm.data.observe(this) { resource ->
			when (resource) {
				is Resource.Success -> {
					hideProgressBar(binding.progressBar)
					frag.setMovieData(resource)
				}

				is Resource.Error -> {
					hideProgressBar(binding.progressBar)
					Snackbar.make(
						binding.root,
						resource.message ?: getString(R.string.error),
						Snackbar.LENGTH_SHORT
					).show()
				}

				is Resource.Loading -> showProgressBar(binding.progressBar)
			}
		}

		vm.getTopMovies(1)
	}

	companion object {
		const val MOVIE_ID_EXTRA = "movie_id"
	}
}
