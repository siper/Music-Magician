package ru.stersh.musicmagician

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree


class MusicMagician : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        context = this.applicationContext
        initKoin()
        initTimber()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MusicMagician)
            modules(Di.modules)
        }
    }

    private fun initTimber() {
        Timber.plant(DebugTree())
    }

    companion object {
        lateinit var context: Context
    }
}
