package com.example.kino.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kino.R
import com.example.kino.data.Movie
import com.squareup.picasso.Picasso

class FavoriteMoviesAdapter :
	RecyclerView.Adapter<FavoriteMoviesAdapter.FavoriteMoviesViewHolder>() {
	private val differCallback = object : DiffUtil.ItemCallback<Movie>() {
		override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
			oldItem.kinopoiskId == newItem.kinopoiskId

		override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
			oldItem == newItem
	}
	val differ = AsyncListDiffer(this, differCallback)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMoviesViewHolder =
		FavoriteMoviesViewHolder(
			LayoutInflater.from(parent.context).inflate(
				R.layout.movie_list_item,
				parent,
				false
			)
		)

	override fun onBindViewHolder(holder: FavoriteMoviesViewHolder, position: Int) {
		val movie = differ.currentList[position]

		holder.title.text = movie?.nameRu ?: movie?.nameEn ?: movie?.nameOriginal ?: ""
		holder.about.text =
			(movie?.genres?.get(0)?.toCapitalize() ?: "") + " (" + (movie?.year
				?: "") + ")" //TODO переделать на ресурсы
		Picasso.get()
			.load(movie?.posterUrlPreview)
			.placeholder(R.drawable.image_placeholder)
			.into(holder.poster)

		/*holder.itemView.setOnClickListener {
			onItemClickListener?.let { it(movie) }
		}*/
	}

	override fun getItemCount(): Int =
		differ.currentList.size

	inner class FavoriteMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val poster: ImageView = itemView.findViewById(R.id.poster)
		val title: TextView = itemView.findViewById(R.id.title)
		val about: TextView = itemView.findViewById(R.id.about)
	}
}
