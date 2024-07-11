package com.example.kino.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kino.R
import com.example.kino.databinding.ActivityMainBinding
import com.example.kino.db.MovieDao
import com.example.kino.db.MovieDatabase
import com.example.kino.util.SetMovieId

class MainActivity : AppCompatActivity(), SetMovieId {
	private lateinit var binding: ActivityMainBinding
	private lateinit var db: MovieDatabase
	lateinit var globalDao: MovieDao
	var globalMovieId: Int? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		db = MovieDatabase.invoke(applicationContext)
		globalDao = db.movieDao()

		val navController = findNavController(R.id.host_fragment)
		binding.bottomNav.setupWithNavController(navController)
	}

	override fun setMovieId(id: Int) {
		globalMovieId = id
	}
}
