package com.byeduck.shoppingassistant

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.byeduck.shoppingassistant.products.FilterFragment
import com.byeduck.shoppingassistant.settings.SettingsFragment

const val SETTING_PAGE_INDEX = 0
const val FILTER_PAGE_INDEX = 1

class HomePageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        SETTING_PAGE_INDEX to { SettingsFragment() },
        FILTER_PAGE_INDEX to { FilterFragment() }
    )

    override fun getItemCount(): Int = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment =
        tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
}