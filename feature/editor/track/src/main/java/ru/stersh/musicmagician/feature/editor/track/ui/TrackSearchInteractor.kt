//package ru.stersh.musicmagician.model.interactor.search
//
//import io.reactivex.Single
//import io.reactivex.functions.Function5
//import ru.stersh.musicmagician.R
//import ru.stersh.musicmagician.data.server.cajunlyrics.CajunlyricsRepository
//import ru.stersh.musicmagician.entity.tag.*
//import ru.stersh.musicmagician.model.data.repository.api.chartlyrics.ChartlyricsRepository
//import ru.stersh.musicmagician.model.data.repository.api.deezer.DeezerTrackTagRepository
//import ru.stersh.musicmagician.model.data.repository.api.itunes.ItunesTrackTagRepository
//import ru.stersh.musicmagician.model.data.repository.api.lololyrics.LololyricsRepository
//
//class TrackSearchInteractor(
//    private val deezerTrackTagRepository: DeezerTrackTagRepository,
//    private val itunesTrackTagRepository: ItunesTrackTagRepository,
//    private val chartlyricsRepository: ChartlyricsRepository,
//    private val cajunlyricsRepository: ru.stersh.musicmagician.data.server.cajunlyrics.CajunlyricsRepository,
//    private val lololyricsRepository: LololyricsRepository
//) {
//    fun searchTags(
//        title: String,
//        artist: String
//    ): Single<List<ru.stersh.musicmagician.data.server.core.entity.TagEntity>> {
//        return Single.zip(
//            deezer(title, artist),
//            itunes(title, artist),
//            chartlyrics(title, artist),
//            cajunlyrics(title, artist),
//            lololyrics(title, artist),
//            Function5 { t1, t2, t3, t4, t5 ->
//                val tags = mutableListOf<ru.stersh.musicmagician.data.server.core.entity.Tag>().apply {
//                    addAll(t1)
//                    addAll(t2)
//                    sortByDescending { it.priority }
//                    toList()
//                }
//                val lyrics = mutableListOf<ru.stersh.musicmagician.data.server.core.entity.Tag>().apply {
//                    addAll(t3)
//                    addAll(t4)
//                    addAll(t5)
//                    toList()
//                }
//                mutableListOf<ru.stersh.musicmagician.data.server.core.entity.TagEntity>().apply {
//                    addAll(tags)
//                    if (lyrics.isNotEmpty()) {
//                        add(TagHeader(R.string.tag_lyrics))
//                        addAll(lyrics)
//                    }
//                    toList()
//                }
//            }
//        )
//    }
//
//    private fun deezer(
//        title: String,
//        artist: String
//    ): Single<List<ru.stersh.musicmagician.data.server.core.entity.TrackTag>> {
//        return deezerTrackTagRepository
//            .getTags(title, artist)
//            .onErrorReturn { emptyList() }
//    }
//
//    private fun itunes(
//        title: String,
//        artist: String
//    ): Single<List<ru.stersh.musicmagician.data.server.core.entity.TrackTag>> {
//        return itunesTrackTagRepository
//            .getTags(title, artist)
//            .onErrorReturn { emptyList() }
//    }
//
//    private fun chartlyrics(
//        title: String,
//        artist: String
//    ): Single<List<ru.stersh.musicmagician.data.server.core.entity.LyricsTag>> {
//        return chartlyricsRepository
//            .getTags(title, artist)
//            .onErrorReturn { emptyList() }
//    }
//
//
//    private fun cajunlyrics(
//        title: String,
//        artist: String
//    ): Single<List<ru.stersh.musicmagician.data.server.core.entity.LyricsTag>> {
//        return cajunlyricsRepository
//            .getTags(title, artist)
//            .onErrorReturn { emptyList() }
//    }
//
//    private fun lololyrics(
//        title: String,
//        artist: String
//    ): Single<List<ru.stersh.musicmagician.data.server.core.entity.LyricsTag>> {
//        return lololyricsRepository
//            .getTags(title, artist)
//            .onErrorReturn { emptyList() }
//    }
//}