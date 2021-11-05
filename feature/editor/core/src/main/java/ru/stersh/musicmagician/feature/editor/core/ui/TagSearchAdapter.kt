package ru.stersh.musicmagician.feature.editor.core.ui

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import coil.load
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import ru.stersh.musicmagician.feature.editor.core.R

class TagSearchAdapter(callback: (UiItem) -> Unit) : AsyncListDifferDelegationAdapter<UiItem>(DIFF_CALLBACK) {
    init {
        delegatesManager
            .addDelegate(trackTag { callback.invoke(it) })
            .addDelegate(albumTag { callback.invoke(it) })
            .addDelegate(lyricsTag { callback.invoke(it) })
            .addDelegate(tagProgress())
            .addDelegate(tagHeader())
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UiItem>() {
                override fun areItemsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
                    if (oldItem is UiItem.Progress && newItem is UiItem.Progress) {
                        return true
                    }
                    if (oldItem is UiItem.Track && newItem is UiItem.Track) {
                        return true
                    }
                    if (oldItem is UiItem.Album && newItem is UiItem.Album) {
                        return true
                    }
                    if (oldItem is UiItem.Lyric && newItem is UiItem.Lyric) {
                        return true
                    }
                    if (oldItem is UiItem.Header && newItem is UiItem.Header) {
                        return true
                    }
                    return false
                }

                override fun areContentsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
                    if (oldItem is UiItem.Track && newItem is UiItem.Track) {
                        return oldItem.title == newItem.title
                                && oldItem.artist == newItem.artist
                                && oldItem.album == newItem.album
                                && oldItem.albumArtUrl == newItem.albumArtUrl
                                && oldItem.genre == newItem.genre
                                && oldItem.number == newItem.number
                                && oldItem.year == newItem.year
                    }
                    if (oldItem is UiItem.Album && newItem is UiItem.Album) {
                        return oldItem.artist == newItem.artist
                                && oldItem.album == newItem.album
                                && oldItem.albumArtUrl == newItem.albumArtUrl
                                && oldItem.year == newItem.year
                    }
                    if (oldItem is UiItem.Progress && newItem is UiItem.Progress) {
                        return true
                    }
                    if (oldItem is UiItem.Lyric && newItem is UiItem.Lyric) {
                        return oldItem.lyrics == newItem.lyrics
                    }
                    if (oldItem is UiItem.Header && newItem is UiItem.Header) {
                        return oldItem.titleResId == newItem.titleResId
                    }
                    return false
                }
            }

        fun tagHeader() = adapterDelegate<UiItem.Header, UiItem>(R.layout.tag_header) {
            val title = findViewById<TextView>(R.id.title)

            bind {
                title.setText(item.titleResId)
            }
        }

        fun trackTag(
            tagCallback: (UiItem.Track) -> Unit
        ) = adapterDelegate<UiItem.Track, UiItem>(R.layout.track_tag_item) {
                val layout = findViewById<LinearLayout>(R.id.track_item)
                val title = findViewById<TextView>(R.id.title)
                val subtitle = findViewById<TextView>(R.id.artist)
                val albumart = findViewById<ImageView>(R.id.albumart)

                layout.setOnClickListener { tagCallback.invoke(item) }

                bind {
                    title.text = item.title
                    subtitle.text = "${item.artist} (${item.album})"

                    albumart.load(item.albumArtUrl)
                }
            }

        fun lyricsTag(
            tagCallback: (UiItem.Lyric) -> Unit
        ) = adapterDelegate<UiItem.Lyric, UiItem>(R.layout.lyrics_tag_item) {
                val layout = findViewById<ConstraintLayout>(R.id.root)
                val lyrics = findViewById<TextView>(R.id.lyrics)

                layout.setOnClickListener { tagCallback.invoke(item) }

                bind {
                    lyrics.text = item.lyrics
                }
            }

        fun albumTag(
            tagCallback: (UiItem.Album) -> Unit
        ) = adapterDelegate<UiItem.Album, UiItem>(R.layout.track_tag_item) {
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

                    albumart.load(item.albumArtUrl)
                }
            }

        fun tagProgress() = adapterDelegate<UiItem.Progress, UiItem>(R.layout.track_shimmer_item) {}
    }
}