package com.example.kino.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kino.R
import com.example.kino.activity.MainActivity
import com.example.kino.adapter.PagingMoviesAdapter
import com.example.kino.databinding.FragmentMoviesListBinding
import com.example.kino.util.ProgressBar
import com.example.kino.viewmodel.MoviesListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesListFragment : Fragment(), ProgressBar {
	private val vm: MoviesListViewModel by viewModels()
	private lateinit var binding: FragmentMoviesListBinding

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

		binding.searchView.apply {
			setupWithSearchBar(binding.searchBar)
			editText.apply {
				setOnEditorActionListener { _, actionId, _ ->
					if (actionId == IME_ACTION_SEARCH) {
						vm.searchMovie(this.toString())

						binding.searchBar.setText(binding.searchView.text)
						binding.searchView.hide()
						return@setOnEditorActionListener true
					}
					return@setOnEditorActionListener false
				}
			}
		}

		val moviesAdapter = PagingMoviesAdapter().apply {
			setOnItemClickListener { movie ->
				findNavController().navigate(
					R.id.show_detail,
					Bundle().apply { putInt(MainActivity.EXTRA_MOVIE_ID, movie.kinopoiskId) }
				)
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

		vm.data.observe(viewLifecycleOwner) { newMoviesList ->
			moviesAdapter.differ.submitList(newMoviesList)
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
