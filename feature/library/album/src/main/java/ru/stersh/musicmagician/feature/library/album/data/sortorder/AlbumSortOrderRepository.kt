package ru.stersh.musicmagician.feature.library.album.data.sortorder

import kotlinx.coroutines.flow.Flow

interface AlbumSortOrderRepository {
    fun getSortOrder(): Flow<AlbumSortOrder>
    suspend fun saveSortOrder(order: AlbumSortOrder)
}