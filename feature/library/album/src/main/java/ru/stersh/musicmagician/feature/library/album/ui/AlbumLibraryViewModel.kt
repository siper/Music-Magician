package ru.stersh.musicmagician.feature.library.album.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.stersh.musicmagician.feature.library.album.data.sortorder.AlbumSortOrder
import ru.stersh.musicmagician.feature.library.album.domain.AlbumLibraryInteractor
import ru.stersh.musicmagician.feature.library.album.ui.entity.UiAlbumSortOrder
import ru.stersh.musicmagician.feature.library.album.ui.entity.UiItem

class AlbumLibraryViewModel(private val interactor: AlbumLibraryInteractor) : ViewModel() {
    val items: Flow<List<UiItem>> = interactor
        .getContent()
        .map { albums ->
            albums.map {
                UiItem.Album(
                    id = it.id,
                    title = it.title,
                    artist = it.artist,
                    albumArtUri = it.albumArtUri
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, List(10) { UiItem.Progress })

    val sortOrder: Flow<UiAlbumSortOrder> = interactor
        .sortOrder()
        .map {
            when (it) {
                AlbumSortOrder.AZ_TITLE -> UiAlbumSortOrder.AZ_TITLE
                AlbumSortOrder.ZA_TITLE -> UiAlbumSortOrder.ZA_TITLE
                AlbumSortOrder.AZ_ARTIST -> UiAlbumSortOrder.AZ_ARTIST
                AlbumSortOrder.ZA_ARTIST -> UiAlbumSortOrder.ZA_ARTIST
                AlbumSortOrder.EARLIEST_YEAR -> UiAlbumSortOrder.EARLIEST_YEAR
                AlbumSortOrder.LATEST_YEAR -> UiAlbumSortOrder.LATEST_YEAR
            }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, UiAlbumSortOrder.AZ_TITLE)

    fun sort(order: UiAlbumSortOrder) = viewModelScope.launch {
        when (order) {
            UiAlbumSortOrder.AZ_TITLE -> AlbumSortOrder.AZ_TITLE
            UiAlbumSortOrder.ZA_TITLE -> AlbumSortOrder.ZA_TITLE
            UiAlbumSortOrder.AZ_ARTIST -> AlbumSortOrder.AZ_ARTIST
            UiAlbumSortOrder.ZA_ARTIST -> AlbumSortOrder.ZA_ARTIST
            UiAlbumSortOrder.EARLIEST_YEAR -> AlbumSortOrder.EARLIEST_YEAR
            UiAlbumSortOrder.LATEST_YEAR -> AlbumSortOrder.LATEST_YEAR
        }.run {
            interactor.sort(this)
        }
    }

    fun search(query: String) = interactor.search(query)
}