package com.example.kino.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kino.activity.MainActivity
import com.example.kino.activity.MovieDetailActivity
import com.example.kino.adapter.MovieListAdapter
import com.example.kino.data.MovieSet
import com.example.kino.databinding.FragmentMoviesListBinding
import com.example.kino.util.Resource

class MoviesListFragment : Fragment() {
	private var binding: FragmentMoviesListBinding? = null
	private lateinit var moviesAdapter: MovieListAdapter

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentMoviesListBinding.inflate(
			inflater,
			container,
			false
		)
		return binding?.root
	}

	override fun onStart() {
		super.onStart()

		moviesAdapter = MovieListAdapter()

		moviesAdapter.setOnItemClickListener { movie ->
			val myIntent = Intent(view?.context, MovieDetailActivity::class.java)
			myIntent.putExtra(
				MainActivity.MOVIE_ID_EXTRA,
				movie.kinopoiskId
			)
			startActivity(myIntent)
		}

		val rv = binding?.movieList
		rv?.apply {
			layoutManager = LinearLayoutManager(view?.context)
			adapter = moviesAdapter
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	fun setMovieData(resource: Resource<MovieSet>) {
		resource.data?.let { movieSet ->
			moviesAdapter.differ.submitList(movieSet.items!!)
		}
	}
}
