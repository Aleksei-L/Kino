package com.example.kino.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kino.data.Movie
import com.example.kino.db.converter.CountryConverter
import com.example.kino.db.converter.GenreConverter

@Database(entities = [Movie::class], version = 1)
@TypeConverters(GenreConverter::class, CountryConverter::class)
abstract class MovieDatabase : RoomDatabase() {
	abstract fun movieDao(): MovieDao
}
