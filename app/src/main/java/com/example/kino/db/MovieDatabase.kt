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

		fun getInstance(context: Context): MovieDatabase {
			synchronized(this) {
				var instance = INSTANCE
				if (instance == null) {
					instance = Room.databaseBuilder(
						context.applicationContext,
						MovieDatabase::class.java,
						"movie_database"
					).build()
					INSTANCE = instance
				}
				return instance
			}
		}
	}
}
