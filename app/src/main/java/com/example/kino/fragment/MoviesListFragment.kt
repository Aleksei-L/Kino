package com.example.kino.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kino.R
import com.example.kino.activity.MainActivity
import com.example.kino.adapter.PagingMoviesAdapter
import com.example.kino.databinding.FragmentMoviesListBinding
import com.example.kino.db.MovieDatabase
import com.example.kino.repo.MovieAPI
import com.example.kino.repo.MoviesRepo
import com.example.kino.util.APIInstance
import com.example.kino.util.ProgressBar
import com.example.kino.util.ShowDetailsForMovie
import com.example.kino.viewmodel.MoviesListViewModel
import com.example.kino.viewmodel.factory.MoviesListViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MoviesListFragment : Fragment(), ProgressBar {
	private lateinit var binding: FragmentMoviesListBinding
	private lateinit var vm: MoviesListViewModel
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
		binding = FragmentMoviesListBinding.inflate(
			inflater,
			container,
			false
		)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.topAppBar.apply {
			title = getString(R.string.app_name)
		}

		vm = ViewModelProvider(
			this,
			MoviesListViewModelFactory(
				MoviesRepo(
					MovieAPI(APIInstance.httpClient),
					MovieDatabase.getInstance(requireContext()).movieDao()
				)
			)
		)[MoviesListViewModel::class.java]

		//TODO подключить виджет
		binding.swipeRefresh.setOnRefreshListener {
			//vm.getTopMovies(1)
			Toast.makeText(view.context, "But nothing happens", Toast.LENGTH_SHORT).show()
			binding.swipeRefresh.isRefreshing = false
		}

		val moviesAdapter = PagingMoviesAdapter().apply {
			setOnItemClickListener { movie ->
				setMovieInterface.setMovie(movie, ShowDetailsForMovie.MAIN_DETAILS)
				findNavController().navigate(R.id.show_detail)
			}
			addLoadStateListener { loadState ->
				if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading)
					showProgressBar(binding.progressBar)
				else {
					hideProgressBar(binding.progressBar)
					val errorState = when {
						loadState.append is LoadState.Error -> loadState.append as LoadState.Error
						loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
						loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
						else -> null
					}
					errorState?.let {
						Snackbar.make(
							binding.root,
							getString(R.string.error),
							Snackbar.LENGTH_SHORT
						).show()
					}
				}
			}
		}

		binding.bindAdapter(moviesAdapter)

		viewLifecycleOwner.lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.STARTED) {
				vm.pageData.collectLatest {
					moviesAdapter.submitData(it)
				}
			}
		}
	}
}

private fun FragmentMoviesListBinding.bindAdapter(articleAdapter: PagingMoviesAdapter) {
	moviesList.adapter = articleAdapter
	moviesList.layoutManager = LinearLayoutManager(moviesList.context)
}
