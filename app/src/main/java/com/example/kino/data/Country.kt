package com.example.kino.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Country(
	val country: String
) {
	override fun toString(): String = country
}
