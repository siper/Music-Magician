package ru.stersh.musicmagician.feature.editor.core.ui

import androidx.viewpager.widget.ViewPager

open class EmptyOnPageChangeListener : ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {}
}