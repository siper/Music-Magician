package ru.stersh.musicmagician.ui.adapter.search

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import com.squareup.picasso.Picasso
import ru.stersh.musicmagician.R
import ru.stersh.musicmagician.entity.tag.*
import ru.stersh.musicmagician.extention.dp
import ru.stersh.musicmagician.utils.picasso.RoundedCornersTransformation

class TagSearchAdapter(callback: (Tag) -> Unit) : AsyncListDifferDelegationAdapter<TagEntity>(DIFF_CALLBACK) {
    init {
        delegatesManager
                .addDelegate(trackTag { callback.invoke(it) })
                .addDelegate(albumTag { callback.invoke(it) })
                .addDelegate(lyricsTag { callback.invoke(it) })
                .addDelegate(tagProgress())
                .addDelegate(tagHeader())
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TagEntity>() {
            override fun areItemsTheSame(oldItem: TagEntity, newItem: TagEntity): Boolean {
                if (oldItem is TagProgressItem && newItem is TagProgressItem) return true
                if (oldItem is TrackTag && newItem is TrackTag) return true
                return false
            }

            override fun areContentsTheSame(oldItem: TagEntity, newItem: TagEntity): Boolean {
                if (oldItem is TrackTag && newItem is TrackTag) {
                    return oldItem.title == newItem.title && oldItem.artist == newItem.artist && oldItem.album == newItem
                            .album && oldItem.albumart == newItem.albumart
                }
                if (oldItem is AlbumTag && newItem is AlbumTag) {
                    return oldItem.artist == newItem.artist && oldItem.album == newItem.album
                            && oldItem.albumart == newItem.albumart
                }
                if (oldItem is TagProgressItem && newItem is TagProgressItem) return true
                return false
            }
        }

        fun tagHeader() = adapterDelegate<TagHeader, TagEntity>(R.layout.tag_header) {
            val title = findViewById<TextView>(R.id.title)

            bind {
                title.text = getString(item.stringId)
            }
        }

        fun trackTag(tagCallback: (TrackTag) -> Unit) = adapterDelegate<TrackTag, TagEntity>(R.layout.track_item) {
            val layout = findViewById<LinearLayout>(R.id.track_item)
            val title = findViewById<TextView>(R.id.title)
            val subtitle = findViewById<TextView>(R.id.artist)
            val albumart = findViewById<ImageView>(R.id.albumart)

            layout.setOnClickListener { tagCallback.invoke(item) }

            bind {
                title.text = item.title
                subtitle.text = "${item.artist} (${item.album})"

                if (item.albumart.isNotEmpty()) {
                    Picasso
                            .get()
                            .load(item.albumart)
                            .error(R.drawable.no_albumart)
                            .placeholder(R.drawable.no_albumart)
                            .transform(RoundedCornersTransformation(8.dp, 0))
                            .fit()
                            .into(albumart)
                } else {
                    Picasso
                            .get()
                            .cancelRequest(albumart)
                    Picasso
                            .get()
                            .load(R.drawable.no_albumart)
                            .transform(RoundedCornersTransformation(8.dp, 0))
                            .fit()
                            .into(albumart)
                }
            }
        }

        fun lyricsTag(tagCallback: (LyricsTag) -> Unit) = adapterDelegate<LyricsTag, TagEntity>(R.layout
                .lyrics_tag_item) {
            val layout = findViewById<ConstraintLayout>(R.id.root)
            val lyrics = findViewById<TextView>(R.id.lyrics)

            layout.setOnClickListener { tagCallback.invoke(item) }

            bind {
                lyrics.text = item.lyrics
            }
        }

        fun albumTag(tagCallback: (AlbumTag) -> Unit) = adapterDelegate<AlbumTag, TagEntity>(R.layout.track_item) {
            val layout = findViewById<LinearLayout>(R.id.track_item)
            val title = findViewById<TextView>(R.id.title)
            val subtitle = findViewById<TextView>(R.id.artist)
            val albumart = findViewById<ImageView>(R.id.albumart)

            layout.setOnClickListener { tagCallback.invoke(item) }

            bind {
                title.text = item.album
                subtitle.text = if (item.year != 0) {
                    "${item.artist} (${item.year})"
                } else {
                    item.artist
                }

                if (item.albumart.isNotEmpty()) {
                    Picasso
                            .get()
                            .load(item.albumart)
                            .error(R.drawable.no_albumart)
                            .placeholder(R.drawable.no_albumart)
                            .transform(RoundedCornersTransformation(8.dp, 0))
                            .fit()
                            .into(albumart)
                } else {
                    Picasso
                            .get()
                            .cancelRequest(albumart)
                    Picasso
                            .get()
                            .load(R.drawable.no_albumart)
                            .transform(RoundedCornersTransformation(8.dp, 0))
                            .fit()
                            .into(albumart)
                }
            }
        }

        fun tagProgress() = adapterDelegate<TagProgressItem, TagEntity>(R.layout.shimmer_item) {}
    }
}