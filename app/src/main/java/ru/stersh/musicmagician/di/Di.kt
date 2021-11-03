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
import ru.stersh.musicmagician.model.interactor.search.AlbumSearchInteractor
import ru.stersh.musicmagician.model.interactor.search.TrackSearchInteractor
import ru.stersh.musicmagician.utils.FileDownloader

object Di : KoinComponent {
    val modules by lazy {
        mutableListOf(android, utils, network, interactor, navigation, mediastoreDataModule).apply {
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

    private val interactor = module {
        single { TrackSearchInteractor(get(), get(), get(), get(), get()) }
        single { AlbumSearchInteractor(get(), get()) }
    }
}