package com.example.kino.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.kino.MyApp
import com.example.kino.R
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

		val app = application as MyApp
		val repo = MoviesRepo(app.data)

		val movieId = intent.getIntExtra(MainActivity.MOVIE_ID_EXTRA, 300) //TODO WHY

		vm = ViewModelProvider(
			this,
			MovieDetailViewModelFactory(repo)
		)[MovieDetailViewModel::class.java]

		val poster = findViewById<ImageView>(R.id.poster)
		val title = findViewById<TextView>(R.id.title)
		val desc = findViewById<TextView>(R.id.description)
		val genres = findViewById<TextView>(R.id.genres)
		val genresList = findViewById<TextView>(R.id.genres_list)

		vm.data.observe(this) {
			Picasso.get()
				.load(it?.posterUrl)
				.placeholder(R.drawable.image_placeholder)
				.into(poster)
			title.text = it?.nameRu ?: it?.nameEn ?: it?.nameOriginal
			supportActionBar?.title = it?.nameRu ?: it?.nameEn ?: it?.nameOriginal
			desc.text = it?.description
			genres.text = resources.getText(R.string.genres)
			var genresStr = ""
			for (i in it?.genres ?: 0..1)
				genresStr += "$i, "
			genresList.text = genresStr.substring(0..genresStr.length - 3)
		}

		vm.getMovieById(movieId)
	}
}
