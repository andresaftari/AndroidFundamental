package com.andresaftari.tablayout.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andresaftari.tablayout.R
import com.andresaftari.tablayout.adapter.TabPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabPagerAdapter = TabPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = tabPagerAdapter
        tabLayout.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f
    }
}