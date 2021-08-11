package ru.stersh.musicmagician.model.interactor.library

import ru.stersh.musicmagician.entity.app.ui.TrackSortOrder
import ru.stersh.musicmagician.entity.mediastore.Track
import ru.stersh.musicmagician.model.data.repository.media.TrackRepository

class TrackLibraryInteractor(private val repository: TrackRepository) : LibraryInteractor<Track>() {
    override fun dataSource() = repository.getTracks()

    override fun getSearchPredicate(item: Track, query: String): Boolean {
        return item.title.toLowerCase().contains(query)
                || item.artist.toLowerCase().contains(query) || item.album.toLowerCase().contains(query)
    }

    override fun getSortComparator(sortOrder: Int): Comparator<Track> {
        return when (sortOrder) {
            TrackSortOrder.ZA_TITLE.order -> Comparator { o1, o2 -> o2.title.compareTo(o1.title) }
            TrackSortOrder.AZ_ARTIST.order -> Comparator { o1, o2 -> o1.artist.compareTo(o2.artist) }
            TrackSortOrder.ZA_ARTIST.order -> Comparator { o1, o2 -> o2.artist.compareTo(o1.artist) }
            TrackSortOrder.OLDEST.order -> Comparator { o1, o2 ->
                o1.dateAdded.toString().compareTo(o2.dateAdded.toString())
            }
            TrackSortOrder.NEWEST.order -> Comparator { o1, o2 ->
                o2.dateAdded.toString().compareTo(o1.dateAdded.toString())
            }
            else -> Comparator { o1, o2 -> o1.title.compareTo(o2.title) }
        }
    }
}