package ru.stersh.musicmagician

import androidx.preference.PreferenceManager
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.stersh.musicmagician.core.data.server.mediator.mediatorRepositoryModules
import ru.stersh.musicmagician.data.mediastore.mediaStoreDataModule
import ru.stersh.musicmagician.feature.editor.track.trackEditorModule
import ru.stersh.musicmagician.feature.library.album.albumLibraryModule
import ru.stersh.musicmagician.feature.library.track.navigation.TrackLibraryNavigation
import ru.stersh.musicmagician.feature.library.track.trackLibraryModule
import ru.stersh.musicmagician.navigation.TrackLibraryNavigationImpl
import ru.stersh.musicmagician.utils.FileDownloader
import ru.stersh.nusicmagician.core.data.local.jaudiotagger.jAudioTaggerRepositoryModule

object Di : KoinComponent {
    val modules: List<Module> by lazy {
        listOf(
            android,
            utils,
            network,
            navigation,
            mediaStoreDataModule,
            jAudioTaggerRepositoryModule,
            trackLibraryModule,
            albumLibraryModule,
            trackEditorModule
        ) + mediatorRepositoryModules
    }

    private val android = module {
        single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
        single { androidContext().contentResolver }
    }

    private val navigation = module {
        single { Cicerone.create() }
        single { get<Cicerone<Router>>().router }
        single { get<Cicerone<Router>>().getNavigatorHolder() }
        single { TrackLibraryNavigationImpl(get()) } bind TrackLibraryNavigation::class
    }

    private val utils = module {
        single { Moshi.Builder().build() }
        single { FileDownloader(get()) }
    }

    private val network = module {
        single {
            OkHttpClient()
                .newBuilder()
                .build()
        }
    }
}