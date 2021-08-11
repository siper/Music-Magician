package ru.stersh.musicmagician.ui.adapter.library

import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import ru.stersh.musicmagician.R
import ru.stersh.musicmagician.entity.app.AlbumProgressItem
import ru.stersh.musicmagician.entity.mediastore.Album
import ru.stersh.musicmagician.entity.mediastore.MediastoreItem
import ru.stersh.musicmagician.utils.android.RxPalette
import timber.log.Timber
import java.io.File

class AlbumLibraryAdapter(callback: (Album) -> Unit) : AsyncListDifferDelegationAdapter<MediastoreItem>(DIFF_CALLBACK) {
    init {
        delegatesManager
                .addDelegate(albumItem { callback.invoke(it) })
                .addDelegate(albumProgressItem())
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MediastoreItem>() {
            override fun areItemsTheSame(oldItem: MediastoreItem, newItem: MediastoreItem): Boolean {
                if (oldItem is AlbumProgressItem && newItem is AlbumProgressItem) return true
                if (oldItem is Album && newItem is Album) return oldItem.id == newItem.id
                return false
            }

            override fun areContentsTheSame(oldItem: MediastoreItem, newItem: MediastoreItem): Boolean {
                if (oldItem is Album && newItem is Album) {
                    return oldItem.title == newItem.title && oldItem.artist == newItem.artist
                            && oldItem.albumart == newItem.albumart
                }
                if (oldItem is AlbumProgressItem && newItem is AlbumProgressItem) return true
                return false
            }
        }

        fun albumItem(callback: (Album) -> Unit) = adapterDelegate<Album, MediastoreItem>(R.layout.album_item) {
            val lifecycle = CompositeDisposable()
            val titleLayout = findViewById<LinearLayout>(R.id.title_layout)
            val title = findViewById<TextView>(R.id.title)
            val artist = findViewById<TextView>(R.id.artist)
            val albumart = findViewById<ImageView>(R.id.albumart)
            val layout = findViewById<CardView>(R.id.album_item_layout)

            layout.setOnClickListener { callback.invoke(item) }

            onViewRecycled {
                lifecycle.clear()
            }

            bind {
                title.text = item.title
                artist.text = item.artist

                artist.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.md_grey_300))
                title.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.md_grey_300))
                titleLayout.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.md_grey_300))
                artist.setTextColor(ContextCompat.getColor(itemView.context, R.color.text_primary))
                title.setTextColor(ContextCompat.getColor(itemView.context, R.color.text_primary))

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
                            .error(R.drawable.no_albumart)
                            .fit()
                            .into(albumart, object : Callback.EmptyCallback() {
                                override fun onSuccess() {
                                    RxPalette
                                            .generate((albumart.drawable as BitmapDrawable).bitmap)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe { palette: Palette? ->
                                                palette?.dominantSwatch?.let {
                                                    val backgroundColor = it.rgb
                                                    val textColor = it.bodyTextColor
                                                    titleLayout.setBackgroundColor(backgroundColor)
                                                    artist.setBackgroundColor(backgroundColor)
                                                    title.setBackgroundColor(backgroundColor)
                                                    artist.setTextColor(textColor)
                                                    title.setTextColor(textColor)
                                                }
                                            }
                                            .addTo(lifecycle)
                                }

                                override fun onError(e: Exception?) {
                                    Timber.e(e)
                                }
                            })
                }
            }
        }

        fun albumProgressItem() = adapterDelegate<AlbumProgressItem, MediastoreItem>(R.layout.shimmer_album_item) {}
    }
}