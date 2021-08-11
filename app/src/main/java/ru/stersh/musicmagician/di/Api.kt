package ru.stersh.musicmagician.di

import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.stersh.musicmagician.model.data.api.cajunlyrics.CajunlyricsApi
import ru.stersh.musicmagician.model.data.api.chartlyrics.ChartlyricsApi
import ru.stersh.musicmagician.model.data.api.deezer.DeezerApi
import ru.stersh.musicmagician.model.data.api.itunes.ItunesApi
import ru.stersh.musicmagician.model.data.api.lololyrics.LololyricsApi

object Api {
    val modules: List<Module> by lazy {
        listOf(deezer, itunes, chartlyrics, cajunlyrics, lololyrics)
    }

    private val deezer = module {
        single(named("deezer")) {
            Retrofit.Builder()
                    .client(get())
                    .addConverterFactory(GsonConverterFactory.create(get()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("https://api.deezer.com/")
                    .build()
        }
        single { get<Retrofit>(named("deezer")).create(DeezerApi::class.java) }
    }

    private val itunes = module {
        single(named("itunes")) {
            Retrofit.Builder()
                    .client(get())
                    .addConverterFactory(GsonConverterFactory.create(get()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("https://itunes.apple.com/")
                    .build()
        }
        single { get<Retrofit>(named("itunes")).create(ItunesApi::class.java) }
    }

    private val chartlyrics = module {
        single(named("chartlyrics")) {
            Retrofit.Builder()
                    .client(get())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("http://api.chartlyrics.com/")
                    .build()
        }
        single { get<Retrofit>(named("chartlyrics")).create(ChartlyricsApi::class.java) }
    }

    private val cajunlyrics = module {
        single(named("cajunlyrics")) {
            Retrofit.Builder()
                    .client(get())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("http://api.cajunlyrics.com/")
                    .build()
        }
        single { get<Retrofit>(named("cajunlyrics")).create(CajunlyricsApi::class.java) }
    }

    private val lololyrics = module {
        single(named("lololyrics")) {
            Retrofit.Builder()
                    .client(get())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("http://api.lololyrics.com/0.5/")
                    .build()
        }
        single { get<Retrofit>(named("lololyrics")).create(LololyricsApi::class.java) }
    }
}