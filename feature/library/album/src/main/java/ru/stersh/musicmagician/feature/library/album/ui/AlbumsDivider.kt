package ru.stersh.musicmagician.feature.library.album.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.stersh.musicmagician.ui.extension.dp

class AlbumsDivider(private val margin: Int = 8.dp) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val viewPosition = parent.getChildAdapterPosition(view)
        if (viewPosition in 0..1) {
            outRect.top = margin
        }
        if (viewPosition == 0) {
            outRect.left = margin
        }
        if (viewPosition % 2 == 0 && viewPosition != 0) {
            outRect.left = margin
        }
        if (viewPosition % 2 == 1 && viewPosition != 0) {
            outRect.right = margin
        }
        val itemsCount = parent.adapter!!.itemCount
        if (itemsCount % 2 == 0) {
            if (viewPosition in itemsCount - 2..itemsCount) {
                outRect.bottom = margin
            }
        } else {
            if (viewPosition == itemsCount - 1) {
                outRect.bottom = margin
            }
        }
    }
}