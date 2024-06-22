package com.example.kino.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kino.R
import com.example.kino.activity.MainActivity
import com.example.kino.activity.MovieDetailActivity
import com.example.kino.adapter.MovieListAdapter
import com.example.kino.data.MovieSet
import com.example.kino.data.Resource

class MoviesListFragment : Fragment() {
	private var rv: RecyclerView? = null
	private lateinit var moviesAdapter: MovieListAdapter

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_movies_list, container, false)
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

		rv = view?.findViewById(R.id.movie_list)
		rv?.apply {
			layoutManager = LinearLayoutManager(view?.context)
			adapter = moviesAdapter
		}
	}

	fun setMovieData(resource: Resource<MovieSet>) {
		when (resource) {
			is Resource.Success -> {
				//hideProgressBar()
				resource.data?.let { movieSet ->
					moviesAdapter.differ.submitList(movieSet.items!!)
				}
			}

			is Resource.Error -> {
				//hideProgressBar()
				resource.message?.let { message ->
					//Log.e(TAG, "An error occured: $message")
				}
			}

			is Resource.Loading -> {
				//showProgressBar()
			}
		}
	}
}
