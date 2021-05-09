package com.andresaftari.apiexample.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.andresaftari.apiexample.R
import com.andresaftari.apiexample.ui.follows.FollowersFragment
import com.andresaftari.apiexample.ui.follows.FollowingsFragment

class TabPagerAdapter(private val context: Context, fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.title_followers,
            R.string.title_following,
        )
    }

    override fun getCount() = 2

    override fun getItem(position: Int) = when (position) {
        0 -> FollowersFragment()
        1 -> FollowingsFragment()
        else -> Fragment()
    }

    override fun getPageTitle(position: Int) = context.resources.getString(TAB_TITLES[position])
}