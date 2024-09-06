package com.example.kino.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kino.R
import com.example.kino.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		val navController = findNavController(R.id.host_fragment)
		binding.bottomNav.setupWithNavController(navController)
	}

	companion object {
		const val EXTRA_MOVIE_ID = "extra_movie_id"
	}
}
