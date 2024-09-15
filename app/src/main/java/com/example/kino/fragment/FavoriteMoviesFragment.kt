package com.example.kino.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kino.R
import com.example.kino.activity.MainActivity
import com.example.kino.adapter.MoviesAdapter
import com.example.kino.databinding.FragmentFavoriteMoviesBinding
import com.example.kino.util.ProgressBar
import com.example.kino.viewmodel.FavoriteMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMoviesFragment : Fragment(), ProgressBar {
	private val vm: FavoriteMoviesViewModel by viewModels()
	private lateinit var binding: FragmentFavoriteMoviesBinding

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentFavoriteMoviesBinding.inflate(
			inflater,
			container,
			false
		)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.searchView.apply {
			setupWithSearchBar(binding.searchBar)
			editText.apply {
				setOnEditorActionListener { _, actionId, _ ->
					if (actionId == IME_ACTION_SEARCH) {
						vm.getMoviesByName(this.toString())

						binding.searchBar.setText(binding.searchView.text)
						binding.searchView.hide()
						return@setOnEditorActionListener true
					}
					return@setOnEditorActionListener false
				}
			}
		}

		val favoriteAdapter = MoviesAdapter().apply {
			setOnItemClickListener { movie ->
				findNavController().navigate(
					R.id.show_favorite_details,
					Bundle().apply { putInt(MainActivity.EXTRA_MOVIE_ID, movie.kinopoiskId) }
				)
			}
		}

		vm.data.observe(viewLifecycleOwner) { newMoviesList ->
			favoriteAdapter.differ.submitList(newMoviesList)
		}

		vm.loadingState.observe(viewLifecycleOwner) { loadingState ->
			when (loadingState) {
				true -> showProgressBar(binding.progressBar)
				false -> hideProgressBar(binding.progressBar)
			}
		}

		binding.moviesList.apply {
			layoutManager = LinearLayoutManager(view.context)
			adapter = favoriteAdapter
		}

		vm.getFavoriteMovies()
	}
}
