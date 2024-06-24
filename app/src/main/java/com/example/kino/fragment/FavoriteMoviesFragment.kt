package com.example.kino.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kino.databinding.FragmentFavoriteMoviesBinding

class FavoriteMoviesFragment : Fragment() {
	private var binding: FragmentFavoriteMoviesBinding? = null

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentFavoriteMoviesBinding.inflate(
			inflater,
			container,
			false
		)
		return binding?.root
	}

}
