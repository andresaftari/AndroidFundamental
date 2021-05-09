package com.andresaftari.apiexample.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.andresaftari.apiexample.databinding.ActivityDetailBinding
import com.andresaftari.apiexample.utils.TabPagerAdapter
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewmodel: DetailViewModel
    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpTabPager()
        viewmodel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        if (intent.hasExtra("EXTRA_DETAIL")) {
            username = intent.getStringExtra("EXTRA_DETAIL")!!
        }

        fetchData()
    }

    private fun fetchData() = with(viewmodel) {
        getDetail(username)
        detail.observe(this@DetailActivity) {
            Glide.with(binding.root)
                .load(it.avatar_url)
                .into(binding.ivAvatar)

            with(binding) {
                tvUsernameDetail.text = it.login
                tvFullnameDetail.text = it.name
                tvLinkDetail.text = it.html_url
            }
        }
    }

    private fun setUpTabPager() {
        val tabPagerAdapter = TabPagerAdapter(this, supportFragmentManager)

        with(binding) {
            viewPager.adapter = tabPagerAdapter
            tabLayout.setupWithViewPager(viewPager)
        }

        supportActionBar?.elevation = 0f
    }
}