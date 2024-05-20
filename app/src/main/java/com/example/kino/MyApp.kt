package com.example.kino

import android.app.Application
import okhttp3.OkHttpClient

class MyApp : Application() {
	lateinit var data: OkHttpClient

	override fun onCreate() {
		super.onCreate()
		data = OkHttpClient()
	}
}
