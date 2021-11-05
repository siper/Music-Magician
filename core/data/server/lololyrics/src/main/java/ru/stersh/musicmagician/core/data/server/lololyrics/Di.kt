package ru.stersh.musicmagician.core.data.server.lololyrics

import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val lololyricsModule = module {
    single(named(API_NAME)) {
        Retrofit.Builder()
            .client(get())
            .baseUrl("https://api.lololyrics.com/0.5/")
            .build()
    }
    single(named(API_NAME)) { get<Retrofit>(named(API_NAME)).create(LololyricsApi::class.java) }
    single { LololyricsRepository(get(named(API_NAME))) }
}

internal const val API_NAME = "lololyrics"