package ru.stersh.musicmagician.data.server.mediator

import org.koin.dsl.bind
import org.koin.dsl.module
import ru.stersh.musicmagician.data.server.cajunlyrics.CajunlyricsRepository
import ru.stersh.musicmagician.data.server.cajunlyrics.cajunLyricsModule
import ru.stersh.musicmagician.data.server.chartlyrics.ChartlyricsRepository
import ru.stersh.musicmagician.data.server.chartlyrics.chartlyricsModule
import ru.stersh.musicmagician.data.server.core.AlbumTagRepository
import ru.stersh.musicmagician.data.server.core.LyricTagRepository
import ru.stersh.musicmagician.data.server.core.TrackTagRepository
import ru.stersh.musicmagician.data.server.deezer.DeezerAlbumTagRepository
import ru.stersh.musicmagician.data.server.deezer.DeezerTrackTagRepository
import ru.stersh.musicmagician.data.server.deezer.deezerModule
import ru.stersh.musicmagician.data.server.itunes.ItunesAlbumTagRepository
import ru.stersh.musicmagician.data.server.itunes.ItunesTrackTagRepository
import ru.stersh.musicmagician.data.server.itunes.itunesModule
import ru.stersh.musicmagician.data.server.lololyrics.LololyricsRepository
import ru.stersh.musicmagician.data.server.lololyrics.lololyricsModule

val mediatorRepositoryModule = module {
    single {
        MediatorAlbumTagRepository(get<DeezerAlbumTagRepository>(), get<ItunesAlbumTagRepository>())
    } bind AlbumTagRepository::class

    single {
        MediatorTrackTagRepository(get<DeezerTrackTagRepository>(), get<ItunesTrackTagRepository>())
    } bind TrackTagRepository::class

    single {
        MediatorLyricTagRepository(
            get<LololyricsRepository>(),
            get<ChartlyricsRepository>(),
            get<CajunlyricsRepository>()
        )
    } bind LyricTagRepository::class
}.apply {
    plus(deezerModule)
    plus(itunesModule)
    plus(lololyricsModule)
    plus(chartlyricsModule)
    plus(cajunLyricsModule)
}