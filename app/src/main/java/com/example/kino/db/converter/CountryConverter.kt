package com.example.kino.db.converter

import androidx.room.TypeConverter
import com.example.kino.data.Country

class CountryConverter {
	@TypeConverter
	fun fromCountryToString(countries: List<Country>?): String {
		var res = ""
		countries?.let {
			it.forEach { country ->
				res += "$country "
			}
		}
		return res
	}

	@TypeConverter
	fun fromStringToCountry(data: String): List<Country> {
		return data.split(" ").map { Country(it) }
	}
}
