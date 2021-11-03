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

class TagSearchAdapter(callback: (ru.stersh.musicmagician.data.server.core.entity.Tag) -> Unit) :
    AsyncListDifferDelegationAdapter<ru.stersh.musicmagician.data.server.core.entity.TagEntity>(DIFF_CALLBACK) {
    init {
        delegatesManager
            .addDelegate(trackTag { callback.invoke(it) })
            .addDelegate(albumTag { callback.invoke(it) })
            .addDelegate(lyricsTag { callback.invoke(it) })
            .addDelegate(tagProgress())
            .addDelegate(tagHeader())
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<ru.stersh.musicmagician.data.server.core.entity.TagEntity>() {
                override fun areItemsTheSame(
                    oldItem: ru.stersh.musicmagician.data.server.core.entity.TagEntity,
                    newItem: ru.stersh.musicmagician.data.server.core.entity.TagEntity
                ): Boolean {
                    if (oldItem is TagProgressItem && newItem is TagProgressItem) return true
                    if (oldItem is ru.stersh.musicmagician.data.server.core.entity.TrackTag && newItem is ru.stersh.musicmagician.data.server.core.entity.TrackTag) return true
                    return false
                }

                override fun areContentsTheSame(
                    oldItem: ru.stersh.musicmagician.data.server.core.entity.TagEntity,
                    newItem: ru.stersh.musicmagician.data.server.core.entity.TagEntity
                ): Boolean {
                    if (oldItem is ru.stersh.musicmagician.data.server.core.entity.TrackTag && newItem is ru.stersh.musicmagician.data.server.core.entity.TrackTag) {
                        return oldItem.title == newItem.title && oldItem.artist == newItem.artist && oldItem.album == newItem
                            .album && oldItem.albumart == newItem.albumart
                    }
                    if (oldItem is ru.stersh.musicmagician.data.server.core.entity.AlbumTag && newItem is ru.stersh.musicmagician.data.server.core.entity.AlbumTag) {
                        return oldItem.artist == newItem.artist && oldItem.album == newItem.album
                                && oldItem.albumart == newItem.albumart
                    }
                    if (oldItem is TagProgressItem && newItem is TagProgressItem) return true
                    return false
                }
            }

        fun tagHeader() =
            adapterDelegate<TagHeader, ru.stersh.musicmagician.data.server.core.entity.TagEntity>(R.layout.tag_header) {
                val title = findViewById<TextView>(R.id.title)

                bind {
                    title.text = getString(item.stringId)
                }
            }

        fun trackTag(tagCallback: (ru.stersh.musicmagician.data.server.core.entity.TrackTag) -> Unit) =
            adapterDelegate<ru.stersh.musicmagician.data.server.core.entity.TrackTag, ru.stersh.musicmagician.data.server.core.entity.TagEntity>(
                R.layout.track_item
            ) {
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

        fun lyricsTag(tagCallback: (ru.stersh.musicmagician.data.server.core.entity.LyricsTag) -> Unit) =
            adapterDelegate<ru.stersh.musicmagician.data.server.core.entity.LyricsTag, ru.stersh.musicmagician.data.server.core.entity.TagEntity>(
                R.layout
                    .lyrics_tag_item
            ) {
                val layout = findViewById<ConstraintLayout>(R.id.root)
                val lyrics = findViewById<TextView>(R.id.lyrics)

                layout.setOnClickListener { tagCallback.invoke(item) }

                bind {
                    lyrics.text = item.lyrics
                }
            }

        fun albumTag(tagCallback: (ru.stersh.musicmagician.data.server.core.entity.AlbumTag) -> Unit) =
            adapterDelegate<ru.stersh.musicmagician.data.server.core.entity.AlbumTag, ru.stersh.musicmagician.data.server.core.entity.TagEntity>(
                R.layout.track_item
            ) {
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

        fun tagProgress() =
            adapterDelegate<TagProgressItem, ru.stersh.musicmagician.data.server.core.entity.TagEntity>(R.layout.shimmer_item) {}
    }
}