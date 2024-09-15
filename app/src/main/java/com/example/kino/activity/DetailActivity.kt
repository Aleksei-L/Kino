package com.example.kino.activity

import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.kino.R
import com.example.kino.data.Movie
import com.example.kino.databinding.ActivityDetailBinding
import com.example.kino.util.ProgressBar
import com.example.kino.viewmodel.MovieDetailViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity(), ProgressBar {
	private val vm: MovieDetailViewModel by viewModels()
	private lateinit var binding: ActivityDetailBinding
	private var movieId = -1
	private var thisMovie: Movie? = null
	private var isAlreadyFavorite = false

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityDetailBinding.inflate(layoutInflater)
		setContentView(binding.root)

		movieId = intent.getIntExtra(MainActivity.EXTRA_MOVIE_ID, -1)
		if (movieId == -1) {
			Toast.makeText(this, getText(R.string.error_occurred), Toast.LENGTH_SHORT).show()
			finish()
		}

		//TODO сделать кнопку назад рабочей

		vm.loadingState.observe(this) { loadingState ->
			when (loadingState) {
				true -> showProgressBar(binding.progressBar)
				false -> hideProgressBar(binding.progressBar)
			}
		}

		vm.data.observe(this) { movie ->
			thisMovie = movie

			Picasso.get()
				.load(movie?.posterUrl)
				.placeholder(R.drawable.image_placeholder)
				.into(binding.poster)
			binding.topAppBar.title = movie?.nameRu ?: movie?.nameEn ?: movie?.nameOriginal
			binding.title.text = movie?.nameRu ?: movie?.nameEn ?: movie?.nameOriginal
			binding.description.text = movie?.description

			if (movie?.genres != null) {
				var genresStr = ""
				for (i in 0..<movie.genres.size - 1)
					genresStr += "${movie.genres[i]}, "
				genresStr += "${movie.genres[movie.genres.size - 1]}"

				binding.genres.text = Html.fromHtml(
					getString(R.string.genres) + genresStr,
					FROM_HTML_MODE_LEGACY
				)
			}

			if (movie?.countries != null) {
				var countriesStr = ""
				for (i in 0..<movie.countries.size - 1)
					countriesStr += "${movie.countries[i]}, "
				countriesStr += "${movie.countries[movie.countries.size - 1]}"
				binding.countries.text =
					Html.fromHtml(
						getString(R.string.countries) + countriesStr,
						FROM_HTML_MODE_LEGACY
					)
			}

			binding.year.text = Html.fromHtml(
				getString(R.string.year, movie?.year),
				FROM_HTML_MODE_LEGACY
			)

			binding.topAppBar.setOnMenuItemClickListener { menuItem ->
				when (menuItem.itemId) {
					R.id.add_to_favorites -> {
						thisMovie?.let { movie ->
							vm.addMovieToFavorite(movie)
						} ?: Snackbar.make(
							binding.root,
							getString(R.string.error_occurred),
							Snackbar.LENGTH_SHORT
						).show()
						configureToolbar()
						true
					}

					R.id.remove_from_favorites -> {
						thisMovie?.let { movie ->
							vm.removeMovieFromFavorite(movie)
						} ?: Snackbar.make(
							binding.root,
							getString(R.string.error_occurred),
							Snackbar.LENGTH_SHORT
						).show()
						configureToolbar()
						true
					}

					else -> false
				}
			}
		}

		vm.isMovieInFavorites.observe(this) {
			isAlreadyFavorite = it
			updateToolbar()
		}

		vm.getMovieById(movieId, true) //TODO а всегда ли нужны свежие данные
		vm.isMovieInDatabase(movieId)
	}

	private fun updateToolbar() {
		if (isAlreadyFavorite) {
			binding.topAppBar.menu.findItem(R.id.add_to_favorites).isVisible = false
			binding.topAppBar.menu.findItem(R.id.remove_from_favorites).isVisible = true
		} else {
			binding.topAppBar.menu.findItem(R.id.add_to_favorites).isVisible = true
			binding.topAppBar.menu.findItem(R.id.remove_from_favorites).isVisible = false
		}
	}

	private fun configureToolbar() {
		binding.topAppBar.menu.findItem(R.id.add_to_favorites).isVisible =
			!binding.topAppBar.menu.findItem(R.id.add_to_favorites).isVisible
		binding.topAppBar.menu.findItem(R.id.remove_from_favorites).isVisible =
			!binding.topAppBar.menu.findItem(R.id.remove_from_favorites).isVisible

		isAlreadyFavorite = !isAlreadyFavorite
	}
}
