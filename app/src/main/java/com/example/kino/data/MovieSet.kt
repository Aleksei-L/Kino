package com.example.kino.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieSet(
	val items: List<Movie>?,
	val total: Int?,
	val totalPages: Int
)
