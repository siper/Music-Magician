package ru.stersh.musicmagician.feature.library.album.domain

import kotlinx.coroutines.flow.Flow
import ru.stersh.musicmagician.feature.library.album.data.library.AlbumLibraryRepository
import ru.stersh.musicmagician.feature.library.album.data.library.LibraryAlbum
import ru.stersh.musicmagician.feature.library.album.data.sortorder.AlbumSortOrder
import ru.stersh.musicmagician.feature.library.album.data.sortorder.AlbumSortOrderRepository
import ru.stersh.musicmagician.feature.library.core.domain.LibraryInteractor


class AlbumLibraryInteractor(
    private val albumRepository: AlbumLibraryRepository,
    private val sortOrderRepository: AlbumSortOrderRepository
) : LibraryInteractor<LibraryAlbum, AlbumSortOrder>() {
    override fun dataSource() = albumRepository.getAlbums()

    override fun sortOrder(): Flow<AlbumSortOrder> = sortOrderRepository.getSortOrder()

    override suspend fun sort(order: AlbumSortOrder) = sortOrderRepository.saveSortOrder(order)

    override fun getSearchPredicate(item: LibraryAlbum, query: String): Boolean {
        return item.title.contains(query, true) || item.artist.contains(query, true)
    }

    override fun getSortComparator(sortOrder: AlbumSortOrder): java.util.Comparator<LibraryAlbum> {
        return when (sortOrder) {
            AlbumSortOrder.AZ_TITLE -> Comparator { o1, o2 -> o1.title.compareTo(o2.title) }
            AlbumSortOrder.ZA_TITLE -> Comparator { o1, o2 -> o2.title.compareTo(o1.title) }
            AlbumSortOrder.AZ_ARTIST -> Comparator { o1, o2 -> o1.artist.compareTo(o2.artist) }
            AlbumSortOrder.ZA_ARTIST -> Comparator { o1, o2 -> o2.artist.compareTo(o1.artist) }
            AlbumSortOrder.EARLIEST_YEAR -> Comparator { o1, o2 ->
                if (o1 == null && o2 == null) {
                    return@Comparator 0
                }
                if (o1 == null && o2 != null) {
                    return@Comparator -1
                }
                if (o1 != null && o2 == null) {
                    return@Comparator 1
                }
                o1.year!!.compareTo(o2.year!!)
            }
            AlbumSortOrder.LATEST_YEAR -> Comparator { o1, o2 ->
                if (o2 == null && o1 == null) {
                    return@Comparator 0
                }
                if (o2 == null && o1 != null) {
                    return@Comparator -1
                }
                if (o2 != null && o1 == null) {
                    return@Comparator 1
                }
                o2.year!!.compareTo(o1.year!!)
            }
        }
    }
}