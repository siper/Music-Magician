package ru.stersh.nusicmagician.core.data.local.jaudiotagger

import org.koin.dsl.bind
import org.koin.dsl.module
import ru.stersh.musicmagician.data.core.external.ExternalTrackRepository

val jAudioTaggerRepositoryModule = module {
    single { JAudioTaggerRepository(get()) } bind ExternalTrackRepository::class
}