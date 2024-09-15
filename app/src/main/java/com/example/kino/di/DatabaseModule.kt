package com.example.kino.di

import android.content.Context
import androidx.room.Room
import com.example.kino.db.MovieDao
import com.example.kino.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
	@Provides
	@Singleton
	fun provideMovieDatabase(@ApplicationContext appContext: Context): MovieDatabase =
		Room.databaseBuilder(
			appContext,
			MovieDatabase::class.java,
			"movie_database"
		).build()

	@Provides
	@Singleton
	fun provideMovieDao(db: MovieDatabase): MovieDao = db.movieDao()
}
