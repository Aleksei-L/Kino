package com.example.kino.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kino.R
import com.example.kino.data.Movie
import com.example.kino.databinding.MovieListItemBinding
import com.squareup.picasso.Picasso

class MovieListAdapter :
	PagingDataAdapter<Movie, MovieListAdapter.MovieListViewHolder>(ARTICLE_DIFF_CALLBACK) {
	private var onItemClickListener: ((Movie) -> Unit)? = null

	companion object {
		private val ARTICLE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
			override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
				oldItem.kinopoiskId == newItem.kinopoiskId

			override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
				oldItem == newItem
		}
	}

	override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
		val tile = getItem(position)
		if (tile != null)
			holder.bind(tile)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		MovieListViewHolder(
			MovieListItemBinding.inflate(
				LayoutInflater.from(parent.context),
				parent,
				false,
			)
		)

	fun setOnItemClickListener(listener: ((Movie) -> Unit)) {
		onItemClickListener = listener
	}

	inner class MovieListViewHolder(private val binding: MovieListItemBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(movie: Movie) {
			binding.apply {
				title.text = movie.nameRu ?: movie.nameEn ?: movie.nameOriginal ?: ""
				if (movie.year != null)
					about.text =
						movie.genres?.get(0)?.toCapitalize() + " (" + movie.year + ")" //TODO
				else
					about.text =
						movie.genres?.get(0)?.toCapitalize()
				root.setOnClickListener {
					onItemClickListener?.let { it(movie) }
				}
				Picasso.get()
					.load(movie.posterUrlPreview)
					.placeholder(R.drawable.image_placeholder)
					.into(poster)
			}
		}
	}
}
