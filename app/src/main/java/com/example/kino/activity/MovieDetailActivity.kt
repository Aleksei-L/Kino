package com.example.kino.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kino.R
import com.example.kino.databinding.ActivityMovieDetailBinding
import com.example.kino.repo.MovieAPI
import com.example.kino.repo.MoviesRepo
import com.example.kino.util.APIInstance
import com.example.kino.util.ProgressBar
import com.example.kino.util.Resource
import com.example.kino.viewmodel.MovieDetailViewModel
import com.example.kino.viewmodel.MovieDetailViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class MovieDetailActivity : AppCompatActivity(), ProgressBar {
	private lateinit var binding: ActivityMovieDetailBinding
	private lateinit var vm: MovieDetailViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMovieDetailBinding.inflate(layoutInflater)
		setContentView(binding.root)

		setSupportActionBar(binding.toolbar.root)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		binding.toolbar.root.setNavigationOnClickListener {
			onBackPressedDispatcher.onBackPressed()
		}

		val movieId = intent.getIntExtra(MainActivity.MOVIE_ID_EXTRA, -1)

		if (movieId == -1) {
			Snackbar.make(binding.root, getString(R.string.error), Snackbar.LENGTH_SHORT).show()
			return
		}

		vm = ViewModelProvider(
			this,
			MovieDetailViewModelFactory(MoviesRepo(MovieAPI(APIInstance.httpClient)))
		)[MovieDetailViewModel::class.java]

		vm.data.observe(this) { resource ->
			when (resource) {
				is Resource.Success -> {
					hideProgressBar(binding.progressBar)
					Picasso.get()
						.load(resource.data?.posterUrl)
						.placeholder(R.drawable.image_placeholder)
						.into(binding.poster)
					binding.title.text = resource.data?.nameRu ?: resource.data?.nameEn
							?: resource.data?.nameOriginal
					supportActionBar?.title =
						resource.data?.nameRu ?: resource.data?.nameEn
								?: resource.data?.nameOriginal
					binding.description.text = resource.data?.description
					binding.genres.text = resources.getText(R.string.genres)
					var genresStr = ""

					for (i in resource.data?.genres ?: 0..1)
						genresStr += "$i, "
					binding.genresList.text = genresStr.substring(0..genresStr.length - 3)
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

		vm.getMovieById(movieId)
	}
}
