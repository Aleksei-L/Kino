package com.example.kino.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kino.data.Movie
import com.example.kino.db.converter.CountryConverter
import com.example.kino.db.converter.GenreConverter

@Database(entities = [Movie::class], version = 1)
@TypeConverters(GenreConverter::class, CountryConverter::class)
abstract class MovieDatabase : RoomDatabase() {
	abstract fun movieDao(): MovieDao

	companion object {
		@Volatile
		private var INSTANCE: MovieDatabase? = null
		private var LOCK = Any()
		operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
			INSTANCE ?: buildDatabase(context).also {
				INSTANCE = it
			}
		}

		private fun buildDatabase(context: Context): MovieDatabase =
			Room.databaseBuilder(
				context.applicationContext, MovieDatabase::class.java, "movie-database"
			).build()
	}
}
