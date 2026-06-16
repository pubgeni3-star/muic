package com.musicplayer.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.musicplayer.fragments.SongsFragment
import com.musicplayer.fragments.PlaylistsFragment
import com.musicplayer.fragments.FavoritesFragment

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SongsFragment()
            1 -> PlaylistsFragment()
            2 -> FavoritesFragment()
            else -> SongsFragment()
        }
    }
}
