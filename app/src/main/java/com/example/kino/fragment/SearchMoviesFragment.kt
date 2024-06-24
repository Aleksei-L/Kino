package com.example.kino.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kino.databinding.FragmentSearchMoviesBinding

class SearchMoviesFragment : Fragment() {
	private var binding: FragmentSearchMoviesBinding? = null

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentSearchMoviesBinding.inflate(
			inflater,
			container,
			false
		)
		return binding?.root
	}
}
