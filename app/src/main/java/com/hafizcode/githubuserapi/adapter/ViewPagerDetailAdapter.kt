package com.hafizcode.githubuserapi.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.hafizcode.githubuserapi.R
import com.hafizcode.githubuserapi.view.fragment.FragmentFollower
import com.hafizcode.githubuserapi.view.fragment.FragmentFollowing

class ViewPagerDetailAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pages = listOf(
        FragmentFollowing(),
        FragmentFollower()
    )

    private val tabTitles = intArrayOf(
        R.string.following_no_space,
        R.string.follower_no_space
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(tabTitles[position])
    }
}