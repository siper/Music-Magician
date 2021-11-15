package ru.stersh.musicmagician.feature.editor.track

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.stersh.musicmagician.feature.editor.track.data.TrackEditorRepository
import ru.stersh.musicmagician.feature.editor.track.data.TrackEditorRepositoryImpl
import ru.stersh.musicmagician.feature.editor.track.domain.TrackEditorInteractor
import ru.stersh.musicmagician.feature.editor.track.domain.TrackTagEditorInteractor
import ru.stersh.musicmagician.feature.editor.track.domain.TrackTagSearchInteractor
import ru.stersh.musicmagician.feature.editor.track.ui.editor.TrackEditorViewModel
import ru.stersh.musicmagician.feature.editor.track.ui.tageditor.TrackTagEditorViewModel
import ru.stersh.musicmagician.feature.editor.track.ui.tagsearch.TrackTagSearchViewModel

val trackEditorModule = module {
    single { TrackEditorRepositoryImpl() } bind TrackEditorRepository::class
    single { TrackEditorInteractor(get(), get(), get()) }
    single { TrackTagEditorInteractor(get()) }
    single { TrackTagSearchInteractor(get(), get(), get(), get()) }
    viewModel { TrackEditorViewModel(get()) }
    viewModel { TrackTagEditorViewModel(get()) }
    viewModel { TrackTagSearchViewModel(get()) }
}