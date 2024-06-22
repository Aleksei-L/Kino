package com.example.kino.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.kino.R
import com.example.kino.fragment.MoviesListFragment
import com.example.kino.repo.MovieAPI
import com.example.kino.repo.MoviesRepo
import com.example.kino.util.APIInstance
import com.example.kino.viewmodel.MoviesListViewModel
import com.example.kino.viewmodel.MoviesListViewModelFactory

class MainActivity : AppCompatActivity() {
	private lateinit var vm: MoviesListViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val toolbar = findViewById<Toolbar>(R.id.toolbar)
		setSupportActionBar(toolbar)

		vm = ViewModelProvider(
			this,
			MoviesListViewModelFactory(MoviesRepo(MovieAPI(APIInstance.httpClient)))
		)[MoviesListViewModel::class.java]

		val frag =
			supportFragmentManager.findFragmentById(R.id.movies_list_fragment) as MoviesListFragment

		vm.data.observe(this) {
			frag.setMovieData(it)
		}
	}

	override fun onStart() {
		super.onStart()

		vm.getTopMovies(1)
	}

	companion object {
		const val MOVIE_ID_EXTRA = "movie_id"
	}
}
