package com.andresaftari.recyclerview.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andresaftari.recyclerview.R
import com.andresaftari.recyclerview.model.Movie
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie.view.*

/*
* Class ini adalah adapter, berfungsi sebagai jantung RecyclerView, pada adapter ini akan ditentukan
* seperti apa data-data akan ditampilkan pada RecyclerView nanti, berapa banyak data yang akan
* diproses, dan bagaimana kita menampilkannya di layout parent nanti
 */

class ListAdapter(private val list: ArrayList<Movie>) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_movie, parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
        holder.bind(list[position])

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            with(itemView) {
                Glide.with(context)
                    .load(movie.images)
                    .into(iv_cover)

                tv_genre?.text = movie.genre
                tv_title?.text = movie.title
            }
        }
    }
}