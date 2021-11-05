package ru.stersh.musicmagician.feature.editor.track.ui.tagsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import ru.stersh.musicmagician.feature.editor.core.ui.UiItem
import ru.stersh.musicmagician.feature.editor.track.R
import ru.stersh.musicmagician.feature.editor.track.data.EditableTrack
import ru.stersh.musicmagician.feature.editor.track.domain.TrackTagSearchInteractor

class TrackTagSearchViewModel(private val interactor: TrackTagSearchInteractor) : ViewModel() {
    private val progress = List(20) { UiItem.Progress }
    private val _searchResults = MutableStateFlow<List<UiItem>>(progress)
    val searchResults: Flow<List<UiItem>>
        get() = _searchResults

    init {
        viewModelScope.launch {
            interactor
                .getTrack()
                .distinctUntilChanged { old, new ->
                    old.title == new.title && old.artist == new.artist
                }
                .collect {
                    searchTags(it)
                }
        }
    }

    private fun searchTags(track: EditableTrack) = viewModelScope.launch {
        val query = when {
            track.title.isNotBlank() && track.artist.isNotEmpty() -> "${track.artist} ${track.title}"
            track.title.isNotEmpty() && track.artist.isEmpty() -> track.title
            else -> return@launch
        }
        _searchResults.value = progress
        val results = mutableListOf<UiItem>()
        val trackTags = interactor.getTrackTags(query)
        if (trackTags.isNotEmpty()) {
            results.add(UiItem.Header(R.string.tracks))
            trackTags.map { tag ->
                UiItem.Track(
                    title = tag.title,
                    artist = tag.artist,
                    album = tag.album,
                    albumArtUrl = tag.albumArtUrl,
                    genre = tag.genre,
                    year = tag.year,
                    number = tag.number
                )
            }.also {
                results.addAll(it)
            }

        }
        if (track.title.isNotEmpty() && track.artist.isNotEmpty()) {
            val lyricTags = interactor.getLyricTags(track.title, track.artist)
            if (lyricTags.isNotEmpty()) {
                results.add(UiItem.Header(R.string.tag_lyrics))
                lyricTags.map { tag ->
                    UiItem.Lyric(
                        lyrics = tag.lyrics
                    )
                }.also {
                    results.addAll(it)
                }
            }
        }
        _searchResults.value = results.toList()
    }

    fun applyTag(item: UiItem) {

    }
}