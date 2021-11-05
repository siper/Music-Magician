package ru.stersh.musicmagician.core.data.server.mediator

import org.koin.dsl.bind
import org.koin.dsl.module
import ru.stersh.musicmagician.core.data.server.cajunlyrics.CajunlyricsRepository
import ru.stersh.musicmagician.core.data.server.cajunlyrics.cajunLyricsModule
import ru.stersh.musicmagician.core.data.server.core.AlbumTagRepository
import ru.stersh.musicmagician.core.data.server.core.LyricTagRepository
import ru.stersh.musicmagician.core.data.server.core.TrackTagRepository
import ru.stersh.musicmagician.core.data.server.deezer.DeezerAlbumTagRepository
import ru.stersh.musicmagician.core.data.server.deezer.DeezerTrackTagRepository
import ru.stersh.musicmagician.core.data.server.deezer.deezerModule
import ru.stersh.musicmagician.core.data.server.itunes.ItunesAlbumTagRepository
import ru.stersh.musicmagician.core.data.server.itunes.ItunesTrackTagRepository
import ru.stersh.musicmagician.core.data.server.itunes.itunesModule

val mediatorRepositoryModules = listOf(
    deezerModule,
    itunesModule,
    //lololyricsModule,
    //chartlyricsModule,
    cajunLyricsModule,
    module {
        single {
            MediatorAlbumTagRepository(get<DeezerAlbumTagRepository>(), get<ItunesAlbumTagRepository>())
        } bind AlbumTagRepository::class

        single {
            MediatorTrackTagRepository(get<DeezerTrackTagRepository>(), get<ItunesTrackTagRepository>())
        } bind TrackTagRepository::class

        single {
            MediatorLyricTagRepository(
                //get<LololyricsRepository>(),
                //get<ChartlyricsRepository>(),
                get<CajunlyricsRepository>()
            )
        } bind LyricTagRepository::class
    }
)