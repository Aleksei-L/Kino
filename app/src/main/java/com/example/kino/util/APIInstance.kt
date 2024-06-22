package com.example.kino.util

import okhttp3.OkHttpClient

class APIInstance {
	companion object {
		val httpClient by lazy {
			OkHttpClient()
		}
	}
}
