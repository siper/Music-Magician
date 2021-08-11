package ru.stersh.musicmagician.di

import androidx.preference.PreferenceManager
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.google.gson.Gson
import com.pushtorefresh.storio3.contentresolver.StorIOContentResolver
import com.pushtorefresh.storio3.contentresolver.impl.DefaultStorIOContentResolver
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.dsl.module
import ru.stersh.musicmagician.entity.mediastore.Album
import ru.stersh.musicmagician.entity.mediastore.Albumart
import ru.stersh.musicmagician.entity.mediastore.Track
import ru.stersh.musicmagician.model.data.repository.api.cajunlyrics.CajunlyricsRepository
import ru.stersh.musicmagician.model.data.repository.api.chartlyrics.ChartlyricsRepository
import ru.stersh.musicmagician.model.data.repository.api.deezer.DeezerAlbumTagRepository
import ru.stersh.musicmagician.model.data.repository.api.deezer.DeezerTrackTagRepository
import ru.stersh.musicmagician.model.data.repository.api.itunes.ItunesAlbumTagRepository
import ru.stersh.musicmagician.model.data.repository.api.itunes.ItunesTrackTagRepository
import ru.stersh.musicmagician.model.data.repository.api.lololyrics.LololyricsRepository
import ru.stersh.musicmagician.model.data.repository.app.PermissionsRepository
import ru.stersh.musicmagician.model.data.repository.app.UserRepository
import ru.stersh.musicmagician.model.data.repository.media.AlbumRepository
import ru.stersh.musicmagician.model.data.repository.media.TrackRepository
import ru.stersh.musicmagician.model.interactor.library.AlbumLibraryInteractor
import ru.stersh.musicmagician.model.interactor.library.TrackLibraryInteractor
import ru.stersh.musicmagician.model.interactor.search.AlbumSearchInteractor
import ru.stersh.musicmagician.model.interactor.search.TrackSearchInteractor
import ru.stersh.musicmagician.utils.FileDownloader
import ru.stersh.musicmagician.utils.storio.AlbumContentResolverTypeMapping
import ru.stersh.musicmagician.utils.storio.AlbumartContentResolverTypeMapping
import ru.stersh.musicmagician.utils.storio.TrackContentResolverTypeMapping

object Di : KoinComponent {
    val modules by lazy {
        mutableListOf(data, android, utils, repository, network, interactor, navigation).apply {
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

    private val data = module {
        single<StorIOContentResolver> {
            DefaultStorIOContentResolver
                    .builder()
                    .contentResolver(get())
                    .addTypeMapping(Track::class.java, TrackContentResolverTypeMapping())
                    .addTypeMapping(Album::class.java, AlbumContentResolverTypeMapping())
                    .addTypeMapping(Albumart::class.java, AlbumartContentResolverTypeMapping())
                    .build()
        }
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

        // Track
        single { TrackRepository(get()) }

        // Album
        single { AlbumRepository(get()) }

        // Deezer
        single { DeezerAlbumTagRepository(get()) }
        single { DeezerTrackTagRepository(get()) }

        // Itunes
        single { ItunesTrackTagRepository(get()) }
        single { ItunesAlbumTagRepository(get()) }

        // Chartlyrics
        single { ChartlyricsRepository(get()) }

        // Cajunlyrics
        single { CajunlyricsRepository(get()) }

        // Lololyrics
        single { LololyricsRepository(get()) }
    }

    private val interactor = module {
        single { TrackSearchInteractor(get(), get(), get(), get(), get()) }
        single { AlbumSearchInteractor(get(), get()) }
        factory { TrackLibraryInteractor(get()) }
        factory { AlbumLibraryInteractor(get()) }
    }
}