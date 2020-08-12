package com.andresaftari.tablayout.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.andresaftari.tablayout.ui.fragment.FragmentOne
import com.andresaftari.tablayout.ui.fragment.FragmentThree
import com.andresaftari.tablayout.ui.fragment.FragmentTwo
import com.andresaftari.tablayout.R

class TabPagerAdapter(private val context: Context, fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        @StringRes
        private val TAB_TITLES =
            intArrayOf(
                R.string.fragment1,
                R.string.fragment2,
                R.string.fragment3
            )
    }

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> FragmentOne()
            1 -> FragmentTwo()
            2 -> FragmentThree()
            else -> Fragment()
        }

    override fun getCount(): Int = 3

    override fun getPageTitle(position: Int): CharSequence? =
        context.resources.getString(TAB_TITLES[position])
}