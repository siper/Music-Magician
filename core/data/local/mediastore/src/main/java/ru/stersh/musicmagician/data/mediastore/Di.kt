package ru.stersh.musicmagician.data.mediastore

import org.koin.dsl.bind
import org.koin.dsl.module
import ru.stersh.musicmagician.data.core.internal.AlbumRepository
import ru.stersh.musicmagician.data.core.internal.TrackRepository

val mediaStoreDataModule = module {
    single { MediastoreAlbumRepository(get()) } bind AlbumRepository::class
    single { MediastoreTrackRepository(get()) } bind TrackRepository::class
}