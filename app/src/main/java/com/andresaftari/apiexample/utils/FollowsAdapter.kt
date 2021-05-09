package com.andresaftari.apiexample.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andresaftari.apiexample.data.remote.api.model.Item
import com.andresaftari.apiexample.databinding.UserItemBinding
import com.bumptech.glide.Glide

class FollowsAdapter(
    private var items: ArrayList<Item>,
    var handler: (Int, Item) -> Unit
) : RecyclerView.Adapter<FollowsAdapter.MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) = with(holder) {
        bind(items[position])
        binding.root.setOnClickListener { handler(position, items[position]) }
    }

    override fun getItemCount() = items.size

    class MainViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) = with(binding) {
            Glide.with(this.root)
                .load(item.avatar_url)
                .into(githubProfilePic)

            githubLogin.text = item.login
            githubLink.text = item.html_url
        }
    }
}