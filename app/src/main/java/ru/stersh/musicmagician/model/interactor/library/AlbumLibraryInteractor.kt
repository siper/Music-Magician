package ru.stersh.musicmagician.model.interactor.library

import ru.stersh.musicmagician.entity.mediastore.Album
import ru.stersh.musicmagician.model.data.repository.media.AlbumRepository
import java.util.*
import kotlin.Comparator

class AlbumLibraryInteractor(private val repository: AlbumRepository) : LibraryInteractor<Album>() {
    override fun dataSource() = repository.getAlbums()

    override fun getSearchPredicate(item: Album, query: String): Boolean {
        return item.title.toLowerCase(Locale.ROOT).contains(query) || item.artist.toLowerCase(Locale.ROOT).contains(query)
    }

    override fun getSortComparator(sortOrder: Int): Comparator<Album> {
        return when (sortOrder) {
            1 -> Comparator { o1, o2 -> o2.title.compareTo(o1.title) }
            2 -> Comparator { o1, o2 -> o1.artist.compareTo(o2.artist) }
            3 -> Comparator { o1, o2 -> o2.artist.compareTo(o1.artist) }
            4 -> Comparator { o1, o2 ->
                val oo1 = if (o1.year.isEmpty()) {
                    "0"
                } else {
                    o1.year
                }
                val oo2 = if (o2.year.isEmpty()) {
                    "0"
                } else {
                    o2.year
                }
                oo1.compareTo(oo2)
            }
            5 -> Comparator { o1, o2 ->
                val oo1 = if (o1.year.isEmpty()) {
                    "0"
                } else {
                    o1.year
                }
                val oo2 = if (o2.year.isEmpty()) {
                    "0"
                } else {
                    o2.year
                }
                oo2.compareTo(oo1)
            }
            else -> Comparator { o1, o2 -> o1.title.compareTo(o2.title) }
        }
    }
}