package ru.stersh.musicmagician.data.server.cajunlyrics

import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val cajunLyricsModule = module {
    single(named(API_NAME)) {
        Retrofit.Builder()
            .client(get())
            .baseUrl("http://api.cajunlyrics.com/")
            .build()
    }
    single { get<Retrofit>(named(API_NAME)).create(CajunlyricsApi::class.java) }
    single { CajunlyricsRepository(get(named(API_NAME))) }
}

internal const val API_NAME = "cajunlyrics"