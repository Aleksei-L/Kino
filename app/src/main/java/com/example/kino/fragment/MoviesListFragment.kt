package com.example.kino.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kino.R
import com.example.kino.adapter.MovieListAdapter
import com.example.kino.data.Movie

class MoviesListFragment : Fragment() {
	private var rv: RecyclerView? = null

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_movies_list, container, false)
	}

	override fun onStart() {
		super.onStart()
		rv = view?.findViewById(R.id.movie_list)

		rv?.layoutManager = LinearLayoutManager(view?.context)
		rv?.adapter = MovieListAdapter()
	}

	fun setMovieData(moviesList: List<Movie>) {
		(rv?.adapter as MovieListAdapter).setMoviesList(moviesList)
	}
}
