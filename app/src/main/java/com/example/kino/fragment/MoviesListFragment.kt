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
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_movies_list, container, false)
	}

	override fun onStart() {
		super.onStart()
		val recyclerView = view?.findViewById<RecyclerView>(R.id.movie_list)

		recyclerView?.layoutManager = LinearLayoutManager(view?.context)
		recyclerView?.adapter = MovieListAdapter(Movie.getFakeMovies())
	}
}
