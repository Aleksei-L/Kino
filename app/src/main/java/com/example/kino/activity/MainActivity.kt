package com.example.kino.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kino.R
import com.example.kino.data.Movie
import com.example.kino.databinding.ActivityMainBinding
import com.example.kino.util.ShowDetailsForMovie

class MainActivity : AppCompatActivity(), ShowDetailsForMovie {
	private lateinit var binding: ActivityMainBinding
	val detailMovieArray = arrayOfNulls<Movie>(3)
	var fromFragment: Int? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		val navController = findNavController(R.id.host_fragment)
		binding.bottomNav.setupWithNavController(navController)
	}

	override fun setMovie(movie: Movie, position: Int) {
		detailMovieArray[position] = movie
		fromFragment = position
	}
}
