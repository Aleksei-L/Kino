package com.example.kino.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kino.MyApp
import com.example.kino.R
import com.example.kino.fragment.MoviesListFragment
import com.example.kino.repo.MoviesRepo
import com.example.kino.viewmodel.MoviesListViewModel
import com.example.kino.viewmodel.MoviesListViewModelFactory

class MainActivity : AppCompatActivity() {
	private lateinit var vm: MoviesListViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val app = application as MyApp
		val repo = MoviesRepo(app.data)

		vm = ViewModelProvider(
			this,
			MoviesListViewModelFactory(repo)
		)[MoviesListViewModel::class.java]

		val frag =
			supportFragmentManager.findFragmentById(R.id.movies_list_fragment) as MoviesListFragment

		vm.data.observe(this) {
			frag.setMovieData(it)
		}
	}

	override fun onStart() {
		super.onStart()

		vm.getMovieById(300)
	}
}
