package ru.stersh.musicmagician.feature.editor.track.ui.tagsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.stersh.musicmagician.core.data.server.core.entity.LyricsTag
import ru.stersh.musicmagician.core.data.server.core.entity.TrackTag
import ru.stersh.musicmagician.feature.editor.core.ui.tagsearch.UiItem
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
                    old.title.equals(new.title, true) && old.artist.equals(new.artist, true)
                }
                .debounce(300)
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
            results.add(UiItem.Header(ru.stersh.musicmagician.ui.R.string.tracks))
            trackTags
                .map { it.toUiTrack() }
                .also { results.addAll(it) }

        }
        if (track.title.isNotEmpty() && track.artist.isNotEmpty()) {
            val lyricTags = interactor.getLyricTags(track.title, track.artist)
            if (lyricTags.isNotEmpty()) {
                results.add(UiItem.Header(ru.stersh.musicmagician.feature.editor.core.R.string.tag_lyrics))
                lyricTags
                    .map { it.toUiLyric() }
                    .also { results.addAll(it) }
            }
        }
        _searchResults.value = results.toList()
    }

    fun applyTag(item: UiItem) = viewModelScope.launch {
        when (item) {
            is UiItem.Lyric -> interactor.setLyrics(item.lyrics)
            is UiItem.Track -> {
                interactor.setTags(
                    title = item.title,
                    artist = item.artist,
                    album = item.album,
                    genre = item.genre,
                    albumArtUrl = item.albumArtUrl,
                    year = item.year,
                    trackNumber = item.trackNumber
                )
            }
        }
    }

    private fun TrackTag.toUiTrack(): UiItem.Track {
        return UiItem.Track(
            title = title,
            artist = artist,
            album = album,
            albumArtUrl = albumArtUrl,
            genre = genre,
            year = year,
            trackNumber = number
        )
    }

    private fun LyricsTag.toUiLyric(): UiItem.Lyric {
        return UiItem.Lyric(lyrics)
    }
}