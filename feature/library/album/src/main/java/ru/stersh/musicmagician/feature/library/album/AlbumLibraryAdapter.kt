package ru.stersh.musicmagician.feature.library.album

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import io.reactivex.disposables.CompositeDisposable
import ru.stersh.musicmagician.ui.fragment.library.UiItem

class AlbumLibraryAdapter(callback: (UiItem.Album) -> Unit) : AsyncListDifferDelegationAdapter<UiItem>(
    DIFF_CALLBACK
) {
    init {
        delegatesManager
                .addDelegate(albumItem { callback.invoke(it) })
                .addDelegate(albumProgressItem())
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UiItem>() {
            override fun areItemsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
                if (oldItem is UiItem.Progress && newItem is UiItem.Progress) {
                    return true
                }
                if (oldItem is UiItem.Album && newItem is UiItem.Album) {
                    return oldItem.id == newItem.id
                }
                return false
            }

            override fun areContentsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
                if (oldItem is UiItem.Album && newItem is UiItem.Album) {
                    return oldItem.title == newItem.title
                            && oldItem.artist == newItem.artist
                            && oldItem.albumArt == newItem.albumArt
                }
                if (oldItem is UiItem.Progress && newItem is UiItem.Progress) {
                    return true
                }
                return false
            }
        }

        fun albumItem(callback: (UiItem.Album) -> Unit) = adapterDelegate<UiItem.Album, UiItem>(R.layout.album_item) {
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

//                if (item.albumArt) {
//                    Picasso
//                            .get()
//                            .load(R.drawable.no_albumart)
//                            .fit()
//                            .error(R.drawable.no_albumart)
//                            .into(albumart)
//                } else {
//                    Picasso
//                            .get()
//                            .load(File(item.albumart))
//                            .error(R.drawable.no_albumart)
//                            .fit()
//                            .into(albumart, object : Callback.EmptyCallback() {
//                                override fun onSuccess() {
//                                    RxPalette
//                                            .generate((albumart.drawable as BitmapDrawable).bitmap)
//                                            .subscribeOn(Schedulers.io())
//                                            .observeOn(AndroidSchedulers.mainThread())
//                                            .subscribe { palette: Palette? ->
//                                                palette?.dominantSwatch?.let {
//                                                    val backgroundColor = it.rgb
//                                                    val textColor = it.bodyTextColor
//                                                    titleLayout.setBackgroundColor(backgroundColor)
//                                                    artist.setBackgroundColor(backgroundColor)
//                                                    title.setBackgroundColor(backgroundColor)
//                                                    artist.setTextColor(textColor)
//                                                    title.setTextColor(textColor)
//                                                }
//                                            }
//                                            .addTo(lifecycle)
//                                }
//
//                                override fun onError(e: Exception?) {
//                                    Timber.e(e)
//                                }
//                            })
//                }
            }
        }

        fun albumProgressItem() = adapterDelegate<UiItem.Album, UiItem>(R.layout.shimmer_album_item) {}
    }
}