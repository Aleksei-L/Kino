package com.example.kino.data

data class Movie(
	val title: String,
	val description: String
) {
	companion object {
		fun getFakeMovies(): List<Movie> = mutableListOf(
			Movie("Film 1", "Desc 1"),
			Movie("Film 2", "Desc 2"),
			Movie("Film 3", "Desc 3"),
			Movie("Film 4", "Desc 4"),
			Movie("Film 5", "Desc 5")
		)
	}
}
