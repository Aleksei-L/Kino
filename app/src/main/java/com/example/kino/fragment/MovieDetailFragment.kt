package com.example.kino.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kino.R
import com.example.kino.activity.MainActivity
import com.example.kino.databinding.FragmentMovieDetailBinding
import com.example.kino.repo.MovieAPI
import com.example.kino.repo.MoviesRepo
import com.example.kino.util.APIInstance
import com.example.kino.util.ProgressBar
import com.example.kino.util.Resource
import com.example.kino.viewmodel.MovieDetailViewModel
import com.example.kino.viewmodel.MovieDetailViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class MovieDetailFragment : Fragment(), ProgressBar {
	private lateinit var binding: FragmentMovieDetailBinding
	private lateinit var vm: MovieDetailViewModel
	private var movieId: Int? = null

	override fun onAttach(context: Context) {
		super.onAttach(context)
		movieId = (context as? MainActivity)?.globalMovieId
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentMovieDetailBinding.inflate(
			inflater,
			container,
			false
		)
		return binding.root
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		vm = ViewModelProvider(
			this,
			MovieDetailViewModelFactory(MoviesRepo(MovieAPI(APIInstance.httpClient)))
		)[MovieDetailViewModel::class.java]

		vm.data.observe(this) { resource ->
			when (resource) {
				is Resource.Success -> {
					hideProgressBar(binding.progressBar)
					Picasso.get()
						.load(resource.data?.posterUrl)
						.placeholder(R.drawable.image_placeholder)
						.into(binding.poster)
					binding.title.text = resource.data?.nameRu ?: resource.data?.nameEn
							?: resource.data?.nameOriginal
					/*supportActionBar?.title =
						resource.data?.nameRu ?: resource.data?.nameEn
								?: resource.data?.nameOriginal*/ //TODO
					binding.description.text = resource.data?.description
					binding.genres.text = resources.getText(R.string.genres)
					var genresStr = ""

					for (i in resource.data?.genres ?: 0..1)
						genresStr += "$i, "
					binding.genresList.text = genresStr.substring(0..genresStr.length - 3)
				}

				is Resource.Error -> {
					hideProgressBar(binding.progressBar)
					Snackbar.make(
						binding.root,
						resource.message ?: getString(R.string.error),
						Snackbar.LENGTH_SHORT
					).show()
				}

				is Resource.Loading -> showProgressBar(binding.progressBar)
			}
		}

		movieId?.let { vm.getMovieById(it) } ?: Snackbar.make(
			binding.root,
			getString(R.string.error),
			Snackbar.LENGTH_SHORT
		).show()
	}

	override fun onStart() {
		super.onStart()
		binding.topAppBar.setNavigationOnClickListener {
			//findNavController().popBackStack()
			findNavController().navigateUp() //TODO
		}

		binding.topAppBar.setOnMenuItemClickListener { menuItem ->
			when (menuItem.itemId) {
				R.id.add_to_favorites -> {
					Toast.makeText(context, "Fav icon pressed!", Toast.LENGTH_SHORT).show()
					true
				}

				else -> false
			}
		}
	}
}
