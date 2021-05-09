package com.andresaftari.apiexample.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.andresaftari.apiexample.data.remote.api.model.Item
import com.andresaftari.apiexample.databinding.UserItemBinding
import com.bumptech.glide.Glide
import java.util.*

class SearchAdapter(
    private var items: MutableList<Item>,
    var handler: (Int, Item) -> Unit
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>(), Filterable {
    private lateinit var fullSearchResult: MutableList<Item>
    private lateinit var filteredSearchResult: MutableList<Item>

    fun update(newList: MutableList<Item>) = with(this.items) {
        clear()
        addAll(newList)
    }

    fun clear() {
        with(this.items) {
            size
            clear()
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder) {
        bind(items[position])
        binding.root.setOnClickListener { handler(position, items[position]) }
    }


    override fun getItemCount() = items.size

    class ViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: Item) = with(binding) {
            Glide.with(this.root)
                .load(user.avatar_url)
                .into(githubProfilePic)

            githubLogin.text = user.login
            githubLink.text = user.html_url
        }
    }

    override fun getFilter() = searchFilter

    private val searchFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<Item> = arrayListOf()

            if (constraint.isEmpty()) filteredList.addAll(fullSearchResult)
            else {
                val pattern = constraint.toString().toLowerCase(Locale.ROOT).trim { it <= ' ' }

                for (item in fullSearchResult)
                    if (item.login.toLowerCase(Locale.ROOT).contains(pattern))
                        filteredList.add(item)
            }

            val resultList = FilterResults()
            resultList.values = filteredList

            return resultList
        }

        override fun publishResults(constraint: CharSequence, resultList: FilterResults) {
            with(filteredSearchResult) {
                clear()
                addAll(resultList.values as MutableList<Item>)
                notifyDataSetChanged()
            }
        }
    }
}