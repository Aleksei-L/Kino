package com.example.kino.repo

import com.example.kino.data.Movie
import com.example.kino.data.MovieSet
import com.example.kino.util.Resource
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

private const val API_KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"

class MovieAPI(private val httpClient: OkHttpClient /* TODO DI */) {
	private val moshi = Moshi.Builder().build()

	suspend fun getMovieById(id: Int): Resource<Movie> = withContext(Dispatchers.IO) {
		val request = Request.Builder()
			.addHeader("X-API-KEY", API_KEY)
			.url("https://kinopoiskapiunofficial.tech/api/v2.2/films/$id")
			.build()
		try {
			httpClient.newCall(request).execute().use { response ->
				if (!response.isSuccessful) {
					return@withContext Resource.Error(response.message)
				}

				val json = response.body?.string()
				val jsonAdapter = moshi.adapter(Movie::class.java)
				val movie = json?.let { jsonAdapter.fromJson(it) }

				return@withContext Resource.Success(movie!!)
			}
		} catch (e: Exception) {
			return@withContext Resource.Error("Network error")
		}
	}

	suspend fun getTopMovies(page: Int): Resource<MovieSet> = withContext(Dispatchers.IO) {
		val request = Request.Builder()
			.addHeader("X-API-KEY", API_KEY)
			.url("https://kinopoiskapiunofficial.tech/api/v2.2/films/collections?type=TOP_POPULAR_ALL&page=$page")
			.build()
		try {
			httpClient.newCall(request).execute().use { response ->
				if (!response.isSuccessful) {
					return@withContext Resource.Error(response.message)
				}

				val json = response.body?.string()
				val jsonAdapter = moshi.adapter(MovieSet::class.java)
				val movies = jsonAdapter.fromJson(json!!)

				return@withContext Resource.Success(movies!!)
			}
		} catch (e: Exception) {
			return@withContext Resource.Error("Network error")
		}
	}
}
