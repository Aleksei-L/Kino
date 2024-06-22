package com.example.kino.util

import android.view.View
import com.google.android.material.progressindicator.LinearProgressIndicator

interface ProgressBar {
	fun hideProgressBar(progressBar: LinearProgressIndicator) {
		progressBar.visibility = View.INVISIBLE
	}

	fun showProgressBar(progressBar: LinearProgressIndicator) {
		progressBar.visibility = View.VISIBLE
	}
}
