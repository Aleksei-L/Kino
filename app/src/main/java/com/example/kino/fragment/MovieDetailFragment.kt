package com.example.kino.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kino.R
import com.example.kino.activity.MainActivity
import com.example.kino.data.Movie
import com.example.kino.databinding.FragmentMovieDetailBinding
import com.example.kino.db.MovieDatabase
import com.example.kino.repo.MovieAPI
import com.example.kino.repo.MoviesRepo
import com.example.kino.util.APIInstance
import com.example.kino.util.ProgressBar
import com.example.kino.util.ShowDetailsForMovie
import com.example.kino.viewmodel.MovieDetailViewModel
import com.example.kino.viewmodel.factory.MovieDetailViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class MovieDetailFragment : Fragment(), ProgressBar {
	private lateinit var binding: FragmentMovieDetailBinding
	private lateinit var vm: MovieDetailViewModel
	private var thisMovie: Movie? = null
	private var fromFragment: Int? = null
	private var isAlreadyFavorite = false

	override fun onAttach(context: Context) {
		super.onAttach(context)
		fromFragment = (context as MainActivity).fromFragment
		thisMovie = when (fromFragment) {
			ShowDetailsForMovie.MAIN_DETAILS -> context.detailMovieArray[ShowDetailsForMovie.MAIN_DETAILS]
			ShowDetailsForMovie.FAVORITE_DETAILS -> context.detailMovieArray[ShowDetailsForMovie.FAVORITE_DETAILS]
			else -> null
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentMovieDetailBinding.inflate(
			inflater,
			container,
			false
		)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		vm = ViewModelProvider(
			this,
			MovieDetailViewModelFactory(
				MoviesRepo(
					MovieAPI(APIInstance.httpClient),
					MovieDatabase.getInstance(requireContext()).movieDao()
				)
			)
		)[MovieDetailViewModel::class.java]

		vm.loadingState.observe(viewLifecycleOwner) { loadingState ->
			when (loadingState) {
				true -> showProgressBar(binding.progressBar)
				false -> hideProgressBar(binding.progressBar)
			}
		}

		vm.data.observe(viewLifecycleOwner) { movie ->
			Picasso.get()
				.load(movie?.posterUrl)
				.placeholder(R.drawable.image_placeholder)
				.into(binding.poster)
			binding.topAppBar.title = movie?.nameRu ?: movie?.nameEn ?: movie?.nameOriginal
			binding.title.text = movie?.nameRu ?: movie?.nameEn ?: movie?.nameOriginal
			binding.description.text = movie?.description
			binding.genres.text = resources.getText(R.string.genres)

			var genresStr = ""
			for (i in movie?.genres ?: 0..1)
				genresStr += "$i, "
			binding.genresList.text = genresStr.substring(0..genresStr.length - 3)
		}

		vm.isMovieInFavorites.observe(viewLifecycleOwner) {
			isAlreadyFavorite = it
			updateToolbar()
		}

		thisMovie?.kinopoiskId?.let { id ->
			vm.getMovieById(id, true) //TODO а всегда ли нужны свежие данные
			vm.isMovieInDatabase(id)
		} ?: Snackbar.make(
			binding.root,
			getString(R.string.error),
			Snackbar.LENGTH_SHORT
		).show()
	}

	override fun onStart() {
		super.onStart()
		binding.topAppBar.setNavigationOnClickListener {
			findNavController().navigateUp()
		}

		binding.topAppBar.setOnMenuItemClickListener { menuItem ->
			when (menuItem.itemId) {
				R.id.add_to_favorites -> {
					thisMovie?.let { movie ->
						vm.addMovieToFavorite(movie)
					} ?: Snackbar.make(
						binding.root,
						getString(R.string.error),
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
						getString(R.string.error),
						Snackbar.LENGTH_SHORT
					).show()
					configureToolbar()
					true
				}

				else -> false
			}
		}
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
