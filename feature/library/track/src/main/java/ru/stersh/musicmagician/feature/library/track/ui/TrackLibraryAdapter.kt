package ru.stersh.musicmagician.feature.library.track.ui

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import com.squareup.picasso.Picasso
import ru.stersh.musicmagician.entity.app.ui.TrackProgressItem
import ru.stersh.musicmagician.data.core.entity.MediastoreItem
import ru.stersh.musicmagician.feature.library.track.R
import java.io.File

class TrackLibraryAdapter(callback: (ru.stersh.musicmagician.data.core.entity.Track, ImageView) -> Unit) : AsyncListDifferDelegationAdapter<ru.stersh.musicmagician.data.core.entity.MediastoreItem>
(DIFF_CALLBACK) {
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ru.stersh.musicmagician.data.core.entity.MediastoreItem>() {
            override fun areItemsTheSame(oldItem: ru.stersh.musicmagician.data.core.entity.MediastoreItem, newItem: ru.stersh.musicmagician.data.core.entity.MediastoreItem): Boolean {
                if (oldItem is TrackProgressItem && newItem is TrackProgressItem) return true
                if (oldItem is ru.stersh.musicmagician.data.core.entity.Track && newItem is ru.stersh.musicmagician.data.core.entity.Track) return oldItem.id == newItem.id
                return false
            }

            override fun areContentsTheSame(oldItem: ru.stersh.musicmagician.data.core.entity.MediastoreItem, newItem: ru.stersh.musicmagician.data.core.entity.MediastoreItem): Boolean {
                if (oldItem is ru.stersh.musicmagician.data.core.entity.Track && newItem is ru.stersh.musicmagician.data.core.entity.Track) {
                    return oldItem.title == newItem.title && oldItem.artist == newItem.artist
                            && oldItem.album == newItem.album && oldItem.albumart == newItem.albumart
                }
                if (oldItem is TrackProgressItem && newItem is TrackProgressItem) return true
                return false
            }
        }

        fun trackItem(callback: (ru.stersh.musicmagician.data.core.entity.Track, ImageView) -> Unit) = adapterDelegate<ru.stersh.musicmagician.data.core.entity.Track, ru.stersh.musicmagician.data.core.entity.MediastoreItem>(
            R.layout.track_item) {
            val layout = findViewById<LinearLayout>(R.id.track_item)
            val title = findViewById<TextView>(R.id.title)
            val artist = findViewById<TextView>(R.id.artist)
            val albumart = findViewById<ImageView>(R.id.albumart)

            layout.setOnClickListener { callback.invoke(item, albumart) }

            bind {
                albumart.transitionName = "transition_${item.id}"
                title.text = item.title
                artist.text = "${item.artist} (${item.album})"

                if (item.albumart.isNullOrEmpty()) {
                    Picasso
                            .get()
                            .load(R.drawable.no_albumart)
                            .fit()
                            .error(R.drawable.no_albumart)
                            .into(albumart)
                } else {
                    Picasso
                            .get()
                            .load(File(item.albumart))
                            .fit()
                            .error(R.drawable.no_albumart)
                            .into(albumart)
                }
            }
        }

        fun trackProgressItem() = adapterDelegate<TrackProgressItem, ru.stersh.musicmagician.data.core.entity.MediastoreItem>(
            R.layout.shimmer_item) {}
    }
}