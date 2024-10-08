package com.example.kino.repo

import com.example.kino.data.Movie
import com.example.kino.data.MovieSet
import com.example.kino.util.APIKey.API_KEY
import com.example.kino.util.Resource
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class MovieAPI @Inject constructor(
	private val httpClient: OkHttpClient,
	private val moshi: Moshi
) {
	suspend fun getMovieById(id: Int): Resource<Movie> = withContext(Dispatchers.IO) {
		val request = Request.Builder()
			.addHeader("X-API-KEY", API_KEY)
			.url("https://kinopoiskapiunofficial.tech/api/v2.2/films/$id")
			.build()
		try {
			httpClient.newCall(request).execute().use { response ->
				if (!response.isSuccessful)
					return@withContext Resource.Error(response.message)

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

	suspend fun searchMovies(query: String): Resource<MovieSet> = withContext(Dispatchers.IO) {
		val request = Request.Builder()
			.addHeader("X-API-KEY", API_KEY)
			//TODO подключить библиотеку paging
			.url("https://kinopoiskapiunofficial.tech/api/v2.1/films/search-by-keyword?keyword=$query&page=1")
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
