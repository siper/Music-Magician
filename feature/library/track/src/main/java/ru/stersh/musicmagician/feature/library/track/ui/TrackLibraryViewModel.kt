package ru.stersh.musicmagician.feature.library.track.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.stersh.musicmagician.feature.library.core.LibraryViewModel
import ru.stersh.musicmagician.feature.library.track.data.sortorder.TrackSortOrder
import ru.stersh.musicmagician.feature.library.track.domain.TrackLibraryInteractor
import ru.stersh.musicmagician.feature.library.track.entity.UiItem
import ru.stersh.musicmagician.feature.library.track.entity.UiTrackSortOrder

class TrackLibraryViewModel(
    private val interactor: TrackLibraryInteractor
) : LibraryViewModel<UiItem, UiTrackSortOrder>() {
    override val items: Flow<List<UiItem>> = interactor
        .getContent()
        .map { tracks ->
            tracks.map {
                UiItem.UiTrack(
                    id = it.id,
                    title = it.title,
                    dateAdded = it.dateAdded,
                    artist = it.artist,
                    album = it.album,
                    uri = it.uri,
                    albumArtUri = it.albumArtUri
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, List(10) { UiItem.Progress })

    override val sortOrder: Flow<UiTrackSortOrder> = interactor
        .sortOrder()
        .map {
            when (it) {
                TrackSortOrder.AZ_TITLE -> UiTrackSortOrder.AZ_TITLE
                TrackSortOrder.ZA_TITLE -> UiTrackSortOrder.ZA_TITLE
                TrackSortOrder.AZ_ARTIST -> UiTrackSortOrder.AZ_ARTIST
                TrackSortOrder.ZA_ARTIST -> UiTrackSortOrder.ZA_ARTIST
                TrackSortOrder.OLDEST -> UiTrackSortOrder.OLDEST
                TrackSortOrder.NEWEST -> UiTrackSortOrder.NEWEST
            }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, UiTrackSortOrder.AZ_TITLE)

    override fun sort(order: UiTrackSortOrder) {
        viewModelScope.launch {
            when (order) {
                UiTrackSortOrder.AZ_TITLE -> TrackSortOrder.AZ_TITLE
                UiTrackSortOrder.ZA_TITLE -> TrackSortOrder.ZA_TITLE
                UiTrackSortOrder.AZ_ARTIST -> TrackSortOrder.AZ_ARTIST
                UiTrackSortOrder.ZA_ARTIST -> TrackSortOrder.ZA_ARTIST
                UiTrackSortOrder.OLDEST -> TrackSortOrder.OLDEST
                UiTrackSortOrder.NEWEST -> TrackSortOrder.NEWEST
            }.run {
                interactor.sort(this)
            }
        }
    }

    override fun search(query: String) = interactor.search(query)
}