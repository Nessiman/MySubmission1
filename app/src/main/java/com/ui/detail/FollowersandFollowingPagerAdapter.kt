package com.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FollowersandFollowingPagerAdapter(activity: AppCompatActivity) :
    FragmentStateAdapter(activity) {
    var username: String = ""


    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = FollowersAndFollowingFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowersAndFollowingFragment.ARG_POSITION, position + 1)
            putString(FollowersAndFollowingFragment.ARG_USERNAME, username)
        }

        return fragment
    }


    override fun getItemCount(): Int {
        return 2

    }
}