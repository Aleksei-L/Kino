package com.example.kino.repo

import kotlinx.coroutines.delay
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MoviesRepo(private val httpClient: OkHttpClient /* TODO DI */) {
	private val apiKey = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"

	suspend fun getMovieDataById(id: Int): String {
		var data = "NO_DATA"
		val request = Request.Builder()
			.addHeader("X-API-KEY", apiKey)
			.url("https://kinopoiskapiunofficial.tech/api/v2.2/films/$id")
			.build()

		httpClient.newCall(request).enqueue(object : Callback {
			override fun onFailure(call: Call, e: IOException) = e.printStackTrace()

			override fun onResponse(call: Call, response: Response) {
				response.use {
					if (!response.isSuccessful)
						throw IOException("Unexpected code $response")

					data = response.body!!.string()
				}
			}
		})
		delay(700) //TODO и как это убрать
		return data
	}

	suspend fun getTopMovies(page: Int): String {
		var data = "NO_DATA"
		val request = Request.Builder()
			.addHeader("X-API-KEY", apiKey)
			.url("https://kinopoiskapiunofficial.tech/api/v2.2/films/collections?type=TOP_POPULAR_ALL&page=$page")
			.build()

		httpClient.newCall(request).enqueue(object : Callback {
			override fun onFailure(call: Call, e: IOException) = e.printStackTrace()

			override fun onResponse(call: Call, response: Response) {
				response.use {
					if (!response.isSuccessful)
						throw IOException("Unexpected code $response")

					data = response.body!!.string()
				}
			}
		})
		delay(700) //TODO и как это убрать
		return data
	}
}
