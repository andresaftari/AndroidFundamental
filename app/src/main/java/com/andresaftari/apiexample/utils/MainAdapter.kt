package com.andresaftari.apiexample.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andresaftari.apiexample.data.remote.api.model.UserResponse
import com.andresaftari.apiexample.databinding.UserItemBinding
import com.bumptech.glide.Glide

class MainAdapter(
    private var items: ArrayList<UserResponse>,
    var handler: (Int, UserResponse) -> Unit
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) = with(holder) {
        bind(items[position])
        binding.root.setOnClickListener { handler(position, items[position]) }
    }

    override fun getItemCount() = items.size

    class MainViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserResponse) = with(binding) {
            Glide.with(this.root)
                .load(user.avatar_url)
                .into(githubProfilePic)

            githubLogin.text = user.login
            githubLink.text = user.html_url
        }
    }
}