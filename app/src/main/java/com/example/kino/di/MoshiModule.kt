package com.example.kino.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MoshiModule {
	@Provides
	@Singleton
	fun provideMoshi(): Moshi = Moshi.Builder().build()
}
