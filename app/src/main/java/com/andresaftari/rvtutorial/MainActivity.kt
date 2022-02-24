package com.andresaftari.rvtutorial

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.andresaftari.rvtutorial.databinding.ActivityMainBinding
import com.andresaftari.rvtutorial.model.Movie
import com.andresaftari.rvtutorial.utils.ListAdapter
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        populateMovie()
    }

    @SuppressLint("Recycle")
    private fun populateMovie() {
        val listMovie = ArrayList<Movie>()

        val images = resources.obtainTypedArray(R.array.images)
        val title = resources.getStringArray(R.array.title)
        val genre = resources.getStringArray(R.array.genre)

        for (pos in title.indices) {
            listMovie.add(
                Movie(
                    title = title[pos],
                    genre = genre[pos],
                    images = images.getResourceId(pos, -1)
                )
            )
        }

       val listAdapter =  ListAdapter(listMovie) {
            // KODE UNTUK AKSI SAAT ITEM RV DIKLIK DI SINI
            Snackbar.make(binding.root, "Clicked ${it.title}!", Snackbar.LENGTH_SHORT).show()
        }

        binding.rvMovie.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter =  listAdapter // kodingan kontroler
            setHasFixedSize(true)
        }
    }
}