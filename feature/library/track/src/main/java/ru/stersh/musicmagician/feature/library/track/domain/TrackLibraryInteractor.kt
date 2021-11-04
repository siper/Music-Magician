package ru.stersh.musicmagician.feature.library.track.domain

import kotlinx.coroutines.flow.Flow
import ru.stersh.musicmagician.feature.library.core.domain.LibraryInteractor
import ru.stersh.musicmagician.feature.library.track.data.library.LibraryTrack
import ru.stersh.musicmagician.feature.library.track.data.library.TrackLibraryRepository
import ru.stersh.musicmagician.feature.library.track.data.sortorder.TrackSortOrder
import ru.stersh.musicmagician.feature.library.track.data.sortorder.TrackSortOrderRepository

class TrackLibraryInteractor(
    private val libraryRepository: TrackLibraryRepository,
    private val sortOrderRepository: TrackSortOrderRepository
) : LibraryInteractor<LibraryTrack, TrackSortOrder>() {
    override fun dataSource() = libraryRepository.getTracks()
    override fun sortOrder(): Flow<TrackSortOrder> = sortOrderRepository.getSortOrder()

    override fun getSearchPredicate(item: LibraryTrack, query: String): Boolean {
        return item.title.contains(query, true)
                || item.artist.contains(query, true)
                || item.album.contains(query, true)
    }

    override fun getSortComparator(sortOrder: TrackSortOrder): Comparator<LibraryTrack> {
        return when (sortOrder) {
            TrackSortOrder.AZ_TITLE -> Comparator { o1, o2 -> o1.title.compareTo(o2.title) }
            TrackSortOrder.ZA_TITLE -> Comparator { o1, o2 -> o2.title.compareTo(o1.title) }
            TrackSortOrder.AZ_ARTIST -> Comparator { o1, o2 -> o1.artist.compareTo(o2.artist) }
            TrackSortOrder.ZA_ARTIST -> Comparator { o1, o2 -> o2.artist.compareTo(o1.artist) }
            TrackSortOrder.OLDEST -> Comparator { o1, o2 -> o1.dateAdded.compareTo(o2.dateAdded) }
            TrackSortOrder.NEWEST -> Comparator { o1, o2 -> o2.dateAdded.compareTo(o1.dateAdded) }
        }
    }

    override suspend fun sort(order: TrackSortOrder) {
        sortOrderRepository.saveSortOrder(order)
    }
}