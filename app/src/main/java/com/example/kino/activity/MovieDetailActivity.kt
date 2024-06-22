package com.example.kino.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.kino.R
import com.example.kino.data.Resource
import com.example.kino.repo.APIInstance
import com.example.kino.repo.MovieAPI
import com.example.kino.repo.MoviesRepo
import com.example.kino.viewmodel.MovieDetailViewModel
import com.example.kino.viewmodel.MovieDetailViewModelFactory
import com.squareup.picasso.Picasso

class MovieDetailActivity : AppCompatActivity() {
	private lateinit var vm: MovieDetailViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_movie_detail)

		val toolbar = findViewById<Toolbar>(R.id.toolbar)
		setSupportActionBar(toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		toolbar.setNavigationOnClickListener {
			onBackPressedDispatcher.onBackPressed()
		}

		val movieId = intent.getIntExtra(MainActivity.MOVIE_ID_EXTRA, 300) //TODO WHY

		vm = ViewModelProvider(
			this,
			MovieDetailViewModelFactory(MoviesRepo(MovieAPI(APIInstance.httpClient)))
		)[MovieDetailViewModel::class.java]

		val poster = findViewById<ImageView>(R.id.poster)
		val title = findViewById<TextView>(R.id.title)
		val desc = findViewById<TextView>(R.id.description)
		val genres = findViewById<TextView>(R.id.genres)
		val genresList = findViewById<TextView>(R.id.genres_list)

		vm.data.observe(this) {
			when (it) {
				is Resource.Success -> {
					//hideProgressBar
					Picasso.get()
						.load(it.data?.posterUrl)
						.placeholder(R.drawable.image_placeholder)
						.into(poster)
					title.text = it.data?.nameRu ?: it.data?.nameEn ?: it.data?.nameOriginal
					supportActionBar?.title =
						it.data?.nameRu ?: it.data?.nameEn ?: it.data?.nameOriginal
					desc.text = it.data?.description
					genres.text = resources.getText(R.string.genres)
					var genresStr = ""

					for (i in it.data?.genres ?: 0..1)
						genresStr += "$i, "
					genresList.text = genresStr.substring(0..genresStr.length - 3)
				}

				else -> println() //TODO дописать
			}
		}

		vm.getMovieById(movieId)
	}
}
