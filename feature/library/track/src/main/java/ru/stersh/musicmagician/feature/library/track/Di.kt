package ru.stersh.musicmagician.feature.library.track

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.stersh.musicmagician.feature.library.track.data.TrackLibraryRepositoryImpl
import ru.stersh.musicmagician.feature.library.track.data.TrackSortOrderRepositoryImpl
import ru.stersh.musicmagician.feature.library.track.data.library.TrackLibraryRepository
import ru.stersh.musicmagician.feature.library.track.data.sortorder.TrackSortOrderRepository
import ru.stersh.musicmagician.feature.library.track.domain.TrackLibraryInteractor
import ru.stersh.musicmagician.feature.library.track.ui.TrackLibraryViewModel

val trackLibraryModule = module {
    single { TrackLibraryRepositoryImpl(get(), get()) } bind TrackLibraryRepository::class
    single { TrackSortOrderRepositoryImpl(get()) } bind TrackSortOrderRepository::class
    single { TrackLibraryInteractor(get(), get()) }
    viewModel { TrackLibraryViewModel(get()) }
}