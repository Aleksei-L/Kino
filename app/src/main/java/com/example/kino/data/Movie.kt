package com.example.kino.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
	val kinopoiskId: Int,
	val imdbId: String?,
	val nameRu: String?,
	val nameEn: String?,
	val nameOriginal: String?,
	val description: String?,
	val shortDescription: String?,
	val slogan: String?,
	val posterUrl: String?,
	val posterUrlPreview: String?,
	val filmLength: Int?,
	val year: Int?,
	val genres: List<Genre>?,
	val countries: List<Country>?,
	val reviewsCount: Int?,
	val ratingKinopoisk: Double?,
	val ratingImdb: Double?
)
