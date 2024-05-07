package com.example.kino.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kino.R

class MovieListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
	val poster: ImageView = itemView.findViewById(R.id.poster)
	val title: TextView = itemView.findViewById(R.id.title)
	val description: TextView = itemView.findViewById(R.id.description)
}
