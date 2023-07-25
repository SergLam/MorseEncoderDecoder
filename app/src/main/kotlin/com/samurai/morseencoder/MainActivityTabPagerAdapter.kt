package com.samurai.morseencoder

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.samurai.morseencoder.fragments.TranslationFragment
import com.samurai.morseencoder.fragments.translation_rules.TranslationRulesFragment
import com.samurai.morseencoder.fragments.SettingsFragment
import com.samurai.sysequsol.R

class MainActivityTabPagerAdapter(activity: FragmentActivity?) : FragmentStateAdapter(activity!!) {

    private val mFragmentList: MutableList<Fragment> = mutableListOf(
        TranslationFragment(), SettingsFragment(), TranslationRulesFragment()
    )

    private var mFragmentTitleList: MutableList<String> = ArrayList()

    init {
        mFragmentTitleList = activity?.resources?.getStringArray(R.array.tab_names)
            ?.toMutableList()!!
    }

    fun getTabTitle(position: Int): String {
        return mFragmentTitleList[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }
}