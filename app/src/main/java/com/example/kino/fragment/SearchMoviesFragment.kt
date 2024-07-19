package com.example.kino.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kino.databinding.FragmentSearchMoviesBinding

class SearchMoviesFragment : Fragment() {
	private lateinit var binding: FragmentSearchMoviesBinding

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentSearchMoviesBinding.inflate(
			inflater,
			container,
			false
		)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.search.setOnSearchClickListener {
			Toast.makeText(context, "Search box clicked!", Toast.LENGTH_SHORT).show()
		}
	}
}
