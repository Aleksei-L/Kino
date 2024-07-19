package com.example.kino.db.converter

import androidx.room.TypeConverter
import com.example.kino.data.Genre

class GenreConverter {
	@TypeConverter
	fun fromGenreToString(genres: List<Genre>?): String {
		var res = ""
		genres?.let {
			it.forEach { genre ->
				res += "$genre "
			}
		}
		return res
	}

	@TypeConverter
	fun fromStringToGenre(data: String): List<Genre> {
		return data.split(" ").map { Genre(it) }
	}
}
