package ru.stersh.musicmagician.extention

import android.content.res.Resources
import android.view.View

fun View.gone() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()