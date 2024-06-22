package com.example.kino.repo

import okhttp3.OkHttpClient

class APIInstance {
	companion object {
		val httpClient by lazy {
			OkHttpClient()
		}
	}
}
