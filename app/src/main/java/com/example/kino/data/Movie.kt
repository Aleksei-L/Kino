package com.example.kino.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
data class Movie(
	@PrimaryKey val kinopoiskId: Int,
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
