package ru.stersh.musicmagician.data.server.cajunlyrics

import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val cajunLyricsModule = module {
    single(named("cajunlyrics")) {
        Retrofit.Builder()
            .client(get())
            .baseUrl("http://api.cajunlyrics.com/")
            .build()
    }
    single { get<Retrofit>(named("cajunlyrics")).create(CajunlyricsApi::class.java) }
}