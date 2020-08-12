package com.andresaftari.recyclerview.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.andresaftari.recyclerview.R
import com.andresaftari.recyclerview.model.Movie
import com.andresaftari.recyclerview.util.ListAdapter
import kotlinx.android.synthetic.main.activity_main.*

/*
* MainActivity adalah home activity untuk aplikasi ini yang akan menjadi parent layout untuk
* menampilkan RecyclerView
 */
class MainActivity : AppCompatActivity() {
    private lateinit var listAdapter: ListAdapter
    private var list: ArrayList<Movie> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.addAll(getMovies())
        populateMovie()
    }

    private fun populateMovie() {
        listAdapter = ListAdapter(list)

        rv_movie.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listAdapter
            setHasFixedSize(true)
        }
    }

    @SuppressLint("Recycle")
    private fun getMovies(): ArrayList<Movie> {
        val listMovie = ArrayList<Movie>()

        val images = resources.obtainTypedArray(R.array.images)
        val title = resources.getStringArray(R.array.title)
        val genre = resources.getStringArray(R.array.genre)
        val year = resources.getStringArray(R.array.year)

        for (position in title.indices) {
            val movie = Movie(
                title[position],
                genre[position],
                year[position],
                images.getResourceId(position, -1)
            )
            listMovie.add(movie)
        }
        return listMovie
    }
}