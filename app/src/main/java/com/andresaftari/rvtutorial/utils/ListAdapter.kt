package com.andresaftari.rvtutorial.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andresaftari.rvtutorial.databinding.ItemMainBinding
import com.andresaftari.rvtutorial.model.Movie
import com.bumptech.glide.Glide

class ListAdapter(private val list: ArrayList<Movie>) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(
            ItemMainBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
        holder.bind(list[position])

    inner class ListViewHolder(private val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            with(binding) {
                Glide.with(this.root)
                    .load(movie.images)
                    .into(binding.ivCover)

                binding.tvTitle.text = movie.title
                binding.tvGenre.text = movie.genre
            }
        }
    }
}