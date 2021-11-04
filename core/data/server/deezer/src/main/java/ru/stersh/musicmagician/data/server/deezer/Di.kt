package ru.stersh.musicmagician.data.server.deezer

import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val deezerModule = module {
    single(named(API_NAME)) {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .baseUrl("https://api.deezer.com/")
            .build()
    }
    single { get<Retrofit>(named(API_NAME)).create(DeezerApi::class.java) }
    single { DeezerAlbumTagRepository(get(named(API_NAME))) }
    single { DeezerTrackTagRepository(get(named(API_NAME))) }
}

internal const val API_NAME = "deezer"