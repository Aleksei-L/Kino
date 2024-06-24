package com.example.kino.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kino.R
import com.example.kino.activity.MainActivity
import com.example.kino.adapter.MovieListAdapter
import com.example.kino.data.MovieSet
import com.example.kino.databinding.FragmentMoviesListBinding
import com.example.kino.repo.MovieAPI
import com.example.kino.repo.MoviesRepo
import com.example.kino.util.APIInstance
import com.example.kino.util.ProgressBar
import com.example.kino.util.Resource
import com.example.kino.util.SetMovieId
import com.example.kino.viewmodel.MoviesListViewModel
import com.example.kino.viewmodel.MoviesListViewModelFactory
import com.google.android.material.snackbar.Snackbar

class MoviesListFragment : Fragment(), ProgressBar {
	private lateinit var binding: FragmentMoviesListBinding
	private lateinit var vm: MoviesListViewModel
	private lateinit var moviesAdapter: MovieListAdapter
	private lateinit var inter: SetMovieId

	override fun onAttach(context: Context) {
		super.onAttach(context)
		inter = (context as MainActivity)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentMoviesListBinding.inflate(
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
			MoviesListViewModelFactory(MoviesRepo(MovieAPI(APIInstance.httpClient)))
		)[MoviesListViewModel::class.java]

		vm.data.observe(viewLifecycleOwner) { resource ->
			when (resource) {
				is Resource.Success -> {
					hideProgressBar(binding.progressBar)
					setMovieData(resource)
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

		moviesAdapter = MovieListAdapter()

		moviesAdapter.setOnItemClickListener { movie ->
			inter.setMovieId(movie.kinopoiskId)
			val navController = findNavController()
			navController.navigate(R.id.show_detail)
		}

		val rv = binding.movieList
		rv.apply {
			layoutManager = LinearLayoutManager(view.context)
			adapter = moviesAdapter
		}

		vm.getTopMovies(1)
	}

	private fun setMovieData(resource: Resource<MovieSet>) {
		resource.data?.let { movieSet ->
			moviesAdapter.differ.submitList(movieSet.items!!)
		}
	}
}
