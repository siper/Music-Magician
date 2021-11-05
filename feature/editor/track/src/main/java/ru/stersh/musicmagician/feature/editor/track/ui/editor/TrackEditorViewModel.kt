package ru.stersh.musicmagician.feature.editor.track.ui.editor

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope
import ru.stersh.musicmagician.feature.editor.track.domain.TrackEditorInteractor
import timber.log.Timber

class TrackEditorViewModel(private val trackEditorInteractor: TrackEditorInteractor) : ViewModel() {

    private val _exit = Channel<Boolean>(1)
    val exit: Flow<Boolean>
        get() = _exit.receiveAsFlow()

    val title: Flow<String> = trackEditorInteractor
        .track
        .map { it.title }
        .stateIn(viewModelScope, SharingStarted.Eagerly, "")

    val artist: Flow<String> = trackEditorInteractor
        .track
        .map { it.artist }
        .stateIn(viewModelScope, SharingStarted.Eagerly, "")

    val albumArt: Flow<Bitmap?> = trackEditorInteractor
        .track
        .map { it.albumArt }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    fun edit(id: Long) = viewModelScope.launch {
        runCatching { trackEditorInteractor.editTrack(id) }.onFailure {
            Timber.w(it)
            _exit.send(true)
        }
    }

    fun save() = viewModelScope.launch {
        trackEditorInteractor.save()
    }
}