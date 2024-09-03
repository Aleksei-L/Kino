package com.example.kino.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kino.R
import com.example.kino.activity.MainActivity
import com.example.kino.adapter.MoviesAdapter
import com.example.kino.databinding.FragmentFavoriteMoviesBinding
import com.example.kino.db.MovieDatabase
import com.example.kino.repo.MovieAPI
import com.example.kino.repo.MoviesRepo
import com.example.kino.util.APIInstance
import com.example.kino.util.ProgressBar
import com.example.kino.util.ShowDetailsForMovie
import com.example.kino.viewmodel.FavoriteMoviesViewModel
import com.example.kino.viewmodel.factory.FavoriteMoviesViewModelFactory

class FavoriteMoviesFragment : Fragment(), ProgressBar {
	private lateinit var binding: FragmentFavoriteMoviesBinding
	private lateinit var vm: FavoriteMoviesViewModel
	private lateinit var setMovieInterface: ShowDetailsForMovie

	override fun onAttach(context: Context) {
		super.onAttach(context)
		setMovieInterface = context as MainActivity
	}

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

		//todo requireContext() создаёт ошибку при отсутсвии контекста, обработать в try-catch
		vm = ViewModelProvider(
			this,
			FavoriteMoviesViewModelFactory(
				MoviesRepo(
					MovieAPI(APIInstance.httpClient),
					MovieDatabase.getInstance(requireContext()).movieDao()
				)
			)
		)[FavoriteMoviesViewModel::class.java]

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
				setMovieInterface.setMovie(movie, ShowDetailsForMovie.FAVORITE_DETAILS)
				findNavController().navigate(R.id.show_favorite_details)
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
