package com.example.kino.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kino.R
import com.example.kino.data.Movie
import com.example.kino.viewholder.MovieListViewHolder

class MovieListAdapter :
	RecyclerView.Adapter<MovieListViewHolder>() {
	private var moviesList: List<Movie>? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
		val itemView =
			LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
		return MovieListViewHolder(itemView)
	}

	override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
		val movie = moviesList?.get(position)

		holder.title.text = movie?.title ?: ""
		holder.description.text = movie?.description ?: ""
		holder.poster.setImageResource(R.drawable.ic_launcher_background)
	}

	override fun getItemCount(): Int = moviesList?.size ?: 0

	fun setMoviesList(movies: List<Movie>) {
		moviesList = movies
		notifyDataSetChanged()
	}
}
