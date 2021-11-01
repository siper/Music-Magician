package ru.stersh.musicmagician.di

import androidx.preference.PreferenceManager
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.dsl.module
import ru.stersh.musicmagician.data.mediastore.mediastoreDataModule
import ru.stersh.musicmagician.data.server.cajunlyrics.CajunlyricsRepository
import ru.stersh.musicmagician.model.data.repository.api.chartlyrics.ChartlyricsRepository
import ru.stersh.musicmagician.model.data.repository.api.deezer.DeezerAlbumTagRepository
import ru.stersh.musicmagician.model.data.repository.api.deezer.DeezerTrackTagRepository
import ru.stersh.musicmagician.model.data.repository.api.itunes.ItunesAlbumTagRepository
import ru.stersh.musicmagician.model.data.repository.api.itunes.ItunesTrackTagRepository
import ru.stersh.musicmagician.model.data.repository.api.lololyrics.LololyricsRepository
import ru.stersh.musicmagician.model.data.repository.app.PermissionsRepository
import ru.stersh.musicmagician.model.data.repository.app.UserRepository
import ru.stersh.musicmagician.feature.library.album.AlbumLibraryInteractor
import ru.stersh.musicmagician.feature.library.track.TrackLibraryInteractor
import ru.stersh.musicmagician.model.interactor.search.AlbumSearchInteractor
import ru.stersh.musicmagician.model.interactor.search.TrackSearchInteractor
import ru.stersh.musicmagician.utils.FileDownloader

object Di : KoinComponent {
    val modules by lazy {
        mutableListOf(android, utils, repository, network, interactor, navigation, mediastoreDataModule).apply {
            addAll(Api.modules)
            toList()
        }
    }

    private val android = module {
        single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
        single { androidContext().contentResolver }
    }

    private val navigation = module {
        single { Cicerone.create() }
        single { get<Cicerone<Router>>().router }
        single { get<Cicerone<Router>>().getNavigatorHolder() }
    }

    private val utils = module {
        single { Gson() }
        single { FileDownloader(get()) }
    }

    private val network = module {
        single {
            OkHttpClient()
                    .newBuilder()
                    .build()
        }
    }

    private val repository = module {
        // User
        single { UserRepository(get()) }

        // Permissions
        single { PermissionsRepository(androidContext()) }

        // Deezer
        single { DeezerAlbumTagRepository(get()) }
        single { DeezerTrackTagRepository(get()) }

        // Itunes
        single { ItunesTrackTagRepository(get()) }
        single { ItunesAlbumTagRepository(get()) }

        // Chartlyrics
        single { ChartlyricsRepository(get()) }

        // Cajunlyrics
        single { ru.stersh.musicmagician.data.server.cajunlyrics.CajunlyricsRepository(get()) }

        // Lololyrics
        single { LololyricsRepository(get()) }
    }

    private val interactor = module {
        single { TrackSearchInteractor(get(), get(), get(), get(), get()) }
        single { AlbumSearchInteractor(get(), get()) }
        factory { ru.stersh.musicmagician.feature.library.track.TrackLibraryInteractor(get()) }
        factory { ru.stersh.musicmagician.feature.library.album.AlbumLibraryInteractor(get()) }
    }
}