package com.example.kino.util

import com.example.kino.activity.MainActivity
import com.example.kino.data.Movie

/**
 * Получение доступа к массиву, хранящему фильмы для их детального отображения во фрагментах
 *
 * Сам массив хранится в [MainActivity]
 *
 * 0 - Все фильмы
 *
 * 1 - Избранные фильмы
 */
interface ShowDetailsForMovie {
	fun setMovie(movie: Movie, position: Int)

	companion object {
		const val MAIN_DETAILS = 0
		const val FAVORITE_DETAILS = 1
	}
}
