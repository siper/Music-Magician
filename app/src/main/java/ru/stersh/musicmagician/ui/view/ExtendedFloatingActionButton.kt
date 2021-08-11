package ru.stersh.musicmagician.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import ru.stersh.musicmagician.R

class ExtendedFloatingActionButton : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val extendedFab by lazy { findViewById<CardView>(R.id.extendedFab) }

    init {
        inflate(context, R.layout.extended_fab, this)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        extendedFab.setOnClickListener(l)
    }
}