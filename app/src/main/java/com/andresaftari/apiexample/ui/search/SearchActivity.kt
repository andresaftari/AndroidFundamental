package com.andresaftari.apiexample.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.andresaftari.apiexample.R
import com.andresaftari.apiexample.data.remote.api.model.Item
import com.andresaftari.apiexample.databinding.ActivitySearchBinding
import com.andresaftari.apiexample.ui.detail.DetailActivity
import com.andresaftari.apiexample.utils.SearchAdapter
import com.google.android.material.snackbar.Snackbar

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewmodel: SearchViewModel
    private lateinit var searchAdapter: SearchAdapter

    private var list: MutableList<Item> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Search User"

        viewmodel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(SearchViewModel::class.java)

        setUpRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val menuItem = menu.findItem(R.id.menu_search)
        val searchView = menuItem.actionView as SearchView

        with(searchView) {
            imeOptions = EditorInfo.IME_ACTION_DONE
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    searchUser(query)
                    return false
                }

                override fun onQueryTextChange(newText: String?) = false
            })
        }

        return true
    }

    private fun searchUser(query: String) {
        showLoading(true)

        with(viewmodel) {
            getSearchData(query)
            userSearch.observe(this@SearchActivity, {
                searchAdapter.update(it.items)
                binding.rvSearch.post { searchAdapter.notifyDataSetChanged() }

                if (it.items.size == 0 || it.items.isEmpty()) {
                    Snackbar.make(
                        binding.root,
                        "User $query tidak ditemukan",
                        Snackbar.LENGTH_SHORT
                    ).show()

                    binding.tvNoData.visibility = View.VISIBLE
                    showLoading(true)
                } else {
                    binding.tvNoData.visibility = View.GONE
                    showLoading(false)
                }
            })
        }
    }

    private fun setUpRecyclerView() {
        searchAdapter = SearchAdapter(list) { _, item ->
            startActivity(
                Intent(
                    this,
                    DetailActivity::class.java
                ).putExtra("EXTRA_DETAIL", item.login)
            )
        }

        with(binding.rvSearch) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = searchAdapter
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) binding.pbSearch.visibility = View.VISIBLE
        else binding.pbSearch.visibility = View.GONE
    }
}