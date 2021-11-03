package ru.stersh.musicmagician.feature.library.track

import org.koin.dsl.bind
import org.koin.dsl.module
import ru.stersh.musicmagician.feature.library.track.data.library.TrackLibraryRepository
import ru.stersh.musicmagician.feature.library.track.data.TrackLibraryRepositoryImpl
import ru.stersh.musicmagician.feature.library.track.data.TrackSortOrderRepositoryImpl
import ru.stersh.musicmagician.feature.library.track.data.sortorder.TrackSortOrderRepository

val trackLibraryModule = module {
    single { TrackLibraryRepositoryImpl(get(), get()) } bind TrackLibraryRepository::class
    single { TrackSortOrderRepositoryImpl(get()) } bind TrackSortOrderRepository::class
}