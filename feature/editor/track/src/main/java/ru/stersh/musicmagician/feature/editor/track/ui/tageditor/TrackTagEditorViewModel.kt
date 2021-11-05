package ru.stersh.musicmagician.feature.editor.track.ui.tageditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.stersh.musicmagician.feature.editor.track.domain.TrackTagEditorInteractor

class TrackTagEditorViewModel(private val interactor: TrackTagEditorInteractor) : ViewModel() {
    private val _title = MutableStateFlow("")
    val title: Flow<String>
        get() = _title

    private val _album = MutableStateFlow("")
    val album: Flow<String>
        get() = _album

    private val _artist = MutableStateFlow("")
    val artist: Flow<String>
        get() = _artist

    private val _comment = MutableStateFlow("")
    val comment: Flow<String>
        get() = _comment

    private val _year = MutableStateFlow("")
    val year: Flow<String>
        get() = _year

    private val _trackNumber = MutableStateFlow("")
    val trackNumber: Flow<String>
        get() = _trackNumber

    private val _genre = MutableStateFlow("")
    val genre: Flow<String>
        get() = _genre

    private val _lyrics = MutableStateFlow("")
    val lyrics: Flow<String>
        get() = _lyrics

    init {
        viewModelScope.launch {
            interactor.getTrack().collect {
                _title.value = it.title
                _album.value = it.album
                _artist.value = it.artist
                _comment.value = it.comment
                _year.value = it.year
                _trackNumber.value = it.trackNumber
                _genre.value = it.genre
                _lyrics.value = it.lyrics
            }
        }
    }

    fun updateTitle(title: String) = viewModelScope.launch {
        _title.value = title
        interactor.updateTitle(title)
    }

    fun updateAlbum(album: String) = viewModelScope.launch {
        _album.value = album
        interactor.updateAlbum(album)
    }

    fun updateArtist(artist: String) = viewModelScope.launch {
        _artist.value = artist
        interactor.updateArtist(artist)
    }

    fun updateComment(comment: String) = viewModelScope.launch {
        _comment.value = comment
        interactor.updateComment(comment)
    }

    fun updateYear(year: String) = viewModelScope.launch {
        _year.value = year
        interactor.updateYear(year)
    }

    fun updateTrackNumber(trackNumber: String) = viewModelScope.launch {
        _trackNumber.value = trackNumber
        interactor.updateTrackNumber(trackNumber)
    }

    fun updateGenre(genre: String) = viewModelScope.launch {
        _genre.value = genre
        interactor.updateGenre(genre)
    }

    fun updateLyrics(lyrics: String) = viewModelScope.launch {
        _lyrics.value = lyrics
        interactor.updateLyrics(lyrics)
    }
}