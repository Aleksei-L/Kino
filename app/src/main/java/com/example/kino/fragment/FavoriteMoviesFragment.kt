package com.example.kino.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kino.activity.MainActivity
import com.example.kino.databinding.FragmentFavoriteMoviesBinding
import com.example.kino.repo.MoviesDbRepo
import com.example.kino.viewmodel.FavoriteMoviesViewModel
import com.example.kino.viewmodel.factory.FavoriteMoviesViewModelFactory

class FavoriteMoviesFragment : Fragment() {
	private lateinit var binding: FragmentFavoriteMoviesBinding
	private lateinit var vm: FavoriteMoviesViewModel

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentFavoriteMoviesBinding.inflate(
			inflater,
			container,
			false
		)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		vm = ViewModelProvider(
			this,
			FavoriteMoviesViewModelFactory(
				MoviesDbRepo(
					(activity as MainActivity).globalDao
				)
			)
		)[FavoriteMoviesViewModel::class.java]
	}
}
