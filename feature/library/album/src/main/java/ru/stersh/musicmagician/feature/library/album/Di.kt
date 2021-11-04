package ru.stersh.musicmagician.feature.library.album

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.stersh.musicmagician.feature.library.album.data.AlbumLibraryRepositoryImpl
import ru.stersh.musicmagician.feature.library.album.data.AlbumSortOrderRepositoryImpl
import ru.stersh.musicmagician.feature.library.album.data.library.AlbumLibraryRepository
import ru.stersh.musicmagician.feature.library.album.data.sortorder.AlbumSortOrderRepository
import ru.stersh.musicmagician.feature.library.album.domain.AlbumLibraryInteractor
import ru.stersh.musicmagician.feature.library.album.ui.AlbumLibraryViewModel

val albumLibraryModule = module {
    single { AlbumLibraryRepositoryImpl(get()) } bind AlbumLibraryRepository::class
    single { AlbumSortOrderRepositoryImpl(get()) } bind AlbumSortOrderRepository::class
    single { AlbumLibraryInteractor(get(), get()) }
    viewModel { AlbumLibraryViewModel(get()) }
}