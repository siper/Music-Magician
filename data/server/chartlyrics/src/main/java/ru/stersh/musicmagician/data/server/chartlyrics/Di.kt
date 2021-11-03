package ru.stersh.musicmagician.data.server.chartlyrics

import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val chartlyricsModule = module {
    single(named(API_NAME)) {
        Retrofit.Builder()
            .client(get())
            .baseUrl("http://api.chartlyrics.com/")
            .build()
    }
    single { get<Retrofit>(named(API_NAME)).create(ChartlyricsApi::class.java) }
    single { ChartlyricsRepository(get(named(API_NAME))) }
}

internal const val API_NAME = "chartlyrics"