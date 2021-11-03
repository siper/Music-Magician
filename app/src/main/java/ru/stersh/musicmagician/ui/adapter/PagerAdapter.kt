package ru.stersh.musicmagician.ui.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.stersh.musicmagician.ui.view.WrapViewPager


class PagerAdapter(
    val fragments: List<Fragment>,
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var mCurrentPosition = -1

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)
        if (position != mCurrentPosition && container is WrapViewPager) {
            val fragment = `object` as Fragment
            val pager: WrapViewPager = container
            if (fragment.view != null) {
                mCurrentPosition = position
                pager.measureCurrentView(fragment.view)
            }
        }
    }
}