package ru.stersh.musicmagician.feature.library.track.ui

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import coil.load
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import ru.stersh.musicmagician.feature.library.track.R
import ru.stersh.musicmagician.feature.library.track.ui.entity.UiItem

class TrackLibraryAdapter(
    callback: (UiItem.UiTrack, ImageView) -> Unit
) : AsyncListDifferDelegationAdapter<UiItem>(DIFF_CALLBACK) {
    init {
        delegatesManager
            .addDelegate(
                trackItem { track, image ->
                    callback.invoke(track, image)
                }
            )
            .addDelegate(trackProgressItem())
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UiItem>() {
            override fun areItemsTheSame(
                oldItem: UiItem,
                newItem: UiItem
            ): Boolean {
                if (oldItem is UiItem.Progress && newItem is UiItem.Progress) {
                    return true
                }
                if (oldItem is UiItem.UiTrack && newItem is UiItem.UiTrack) {
                    return oldItem.id == newItem.id
                }
                return false
            }

            override fun areContentsTheSame(
                oldItem: UiItem,
                newItem: UiItem
            ): Boolean {
                if (oldItem is UiItem.Progress && newItem is UiItem.Progress) {
                    return true
                }
                if (oldItem is UiItem.UiTrack && newItem is UiItem.UiTrack) {
                    return oldItem.title == newItem.title
                            && oldItem.artist == newItem.artist
                            && oldItem.album == newItem.album
                            && oldItem.albumArtUri == newItem.albumArtUri
                }
                return false
            }
        }

        fun trackItem(callback: (UiItem.UiTrack, ImageView) -> Unit) =
            adapterDelegate<UiItem.UiTrack, UiItem>(R.layout.track_item) {
                val layout = findViewById<LinearLayout>(R.id.track_item)
                val title = findViewById<TextView>(R.id.title)
                val artist = findViewById<TextView>(R.id.artist)
                val albumart = findViewById<ImageView>(R.id.albumart)

                layout.setOnClickListener { callback.invoke(item, albumart) }

                bind {
                    albumart.transitionName = "transition_${item.id}"
                    title.text = item.title
                    artist.text = "${item.artist} (${item.album})"

                    item.albumArtUri?.let {
                        albumart.load(it)
                    }
                }
            }

        fun trackProgressItem() = adapterDelegate<UiItem.Progress, UiItem>(R.layout.track_shimmer_item) {}
    }
}