package ru.stersh.musicmagician.feature.library.track.data.sortorder

import kotlinx.coroutines.flow.Flow

interface TrackSortOrderRepository {
    fun getSortOrder(): Flow<TrackSortOrder>
    suspend fun saveSortOrder(order: TrackSortOrder)
}