package com.example.kino.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kino.R
import com.example.kino.data.Movie
import com.squareup.picasso.Picasso

class MovieListAdapter :
	RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {
	private var moviesList: List<Movie?>? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
		val itemView =
			LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
		return MovieListViewHolder(itemView)
	}

	override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
		val movie = moviesList?.get(position)

		holder.title.text = movie?.nameRu ?: movie?.nameEn ?: movie?.nameOriginal ?: ""
		holder.about.text =
			(movie?.genres?.get(0)?.toString() ?: "") + " (" + (movie?.year
				?: "") + ")" //TODO переделать на ресурсы
		Picasso.get()
			.load(movie?.posterUrlPreview)
			.placeholder(R.drawable.image_placeholder)
			.into(holder.poster)
	}

	override fun getItemCount(): Int = moviesList?.size ?: 0

	fun setMoviesList(movies: List<Movie?>) {
		moviesList = movies
		notifyDataSetChanged()
	}

	inner class MovieListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val poster: ImageView = itemView.findViewById(R.id.poster)
		val title: TextView = itemView.findViewById(R.id.title)
		val about: TextView = itemView.findViewById(R.id.about)
	}
}
