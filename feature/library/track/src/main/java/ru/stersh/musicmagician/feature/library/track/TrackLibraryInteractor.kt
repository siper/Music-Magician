package ru.stersh.musicmagician.feature.library.track

import ru.stersh.musicmagician.data.core.TrackRepository
import ru.stersh.musicmagician.data.core.entity.Track
import ru.stersh.musicmagician.feature.library.track.entity.TrackSortOrder

class TrackLibraryInteractor(private val repository: TrackRepository) : LibraryInteractor<Track>() {
    override fun dataSource() = repository.getAllTracks()

    override fun getSearchPredicate(item: Track, query: String): Boolean {
        return item.title.contains(query, true)
                || item.artist.contains(query, true)
                || item.album.contains(query, true)
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