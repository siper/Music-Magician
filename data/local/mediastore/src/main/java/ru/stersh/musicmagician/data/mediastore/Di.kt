package ru.stersh.musicmagician.data.mediastore

import org.koin.dsl.bind
import org.koin.dsl.module
import ru.stersh.musicmagician.data.core.AlbumRepository
import ru.stersh.musicmagician.data.core.TrackRepository

val mediastoreDataModule = module {
    single { MediastoreAlbumRepository(get()) } bind AlbumRepository::class
    single { MediastoreTrackRepository(get()) } bind TrackRepository::class
}