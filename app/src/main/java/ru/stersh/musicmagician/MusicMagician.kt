package ru.stersh.musicmagician

import android.app.Application
import android.content.Context
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree


class MusicMagician : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext
        initKoin()
        initCalligraphy()
        initTimber()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MusicMagician)
            modules(Di.modules)
        }
    }

    private fun initCalligraphy() {
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/Montserrat-Medium.ttf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )
    }

    private fun initTimber() {
        Timber.plant(DebugTree())
    }

    companion object {
        lateinit var context: Context
    }
}
