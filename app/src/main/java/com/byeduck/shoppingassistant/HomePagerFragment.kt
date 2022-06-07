package com.byeduck.shoppingassistant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.byeduck.shoppingassistant.databinding.FragmentHomePagerBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomePagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomePagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager
        viewPager.adapter = HomePageAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }
        return binding.root
    }

    private fun getTabTitle(position: Int): String? = when (position) {
        SETTING_PAGE_INDEX -> getString(R.string.settings_screen_title)
        FILTER_PAGE_INDEX -> getString(R.string.filter_screen_title)
        else -> null
    }
}