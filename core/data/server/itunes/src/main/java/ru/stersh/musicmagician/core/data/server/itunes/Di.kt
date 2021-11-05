package ru.stersh.musicmagician.core.data.server.itunes

import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val itunesModule = module {
    single(named(API_NAME)) {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .baseUrl("https://itunes.apple.com/")
            .build()
    }
    single(named(API_NAME)) { get<Retrofit>(named(API_NAME)).create(ItunesApi::class.java) }
    single { ItunesAlbumTagRepository(get(named(API_NAME))) }
    single { ItunesTrackTagRepository(get(named(API_NAME))) }
}

internal const val API_NAME = "itunes"