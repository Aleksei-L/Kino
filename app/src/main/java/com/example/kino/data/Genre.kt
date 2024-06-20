package com.example.kino.data

import com.squareup.moshi.JsonClass
import java.util.Locale

@JsonClass(generateAdapter = true)
data class Genre(
	val genre: String
) {
	override fun toString(): String = genre

	fun toCapitalize(): String = genre.replaceFirstChar {
		if (it.isLowerCase())
			it.titlecase(Locale.getDefault())
		else
			it.toString()
	}
}
