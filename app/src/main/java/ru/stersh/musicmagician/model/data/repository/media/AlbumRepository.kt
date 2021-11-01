//package ru.stersh.musicmagician.model.data.repository.media
//
//import android.provider.MediaStore
//import com.pushtorefresh.storio3.contentresolver.StorIOContentResolver
//import com.pushtorefresh.storio3.contentresolver.queries.Query
//import io.reactivex.BackpressureStrategy
//import io.reactivex.Completable
//import io.reactivex.Flowable
//import io.reactivex.Observable
//import io.reactivex.subjects.BehaviorSubject
//import ru.stersh.musicmagician.data.core.entity.Album
//import ru.stersh.musicmagician.data.core.entity.Track
//import ru.stersh.musicmagician.tempAlbumart
//import ru.stersh.musicmagician.utils.tag.AlbumartUtils
//import ru.stersh.musicmagician.utils.tag.TrackUtils
//import timber.log.Timber
//import java.io.File
//
//class AlbumRepository(private val storIOContentResolver: StorIOContentResolver) {
//    private val cache: MutableMap<Long, BehaviorSubject<ru.stersh.musicmagician.data.core.entity.Album>> = mutableMapOf()
//
//    fun getAlbums(): Flowable<List<ru.stersh.musicmagician.data.core.entity.Album>> {
//        val whereCause = MediaStore.Audio.Albums.ARTIST + " != ?"
//        return storIOContentResolver
//                .get()
//                .listOfObjects(ru.stersh.musicmagician.data.core.entity.Album::class.java)
//                .withQuery(Query.builder()
//                        .uri(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI)
//                        .where(whereCause)
//                        .whereArgs(MediaStore.UNKNOWN_STRING)
//                        .build())
//                .prepare()
//                .asRxFlowable(BackpressureStrategy.BUFFER)
//    }
//
//    fun getAlbum(albumId: Long): Observable<ru.stersh.musicmagician.data.core.entity.Album> {
//        if (!cache.contains(albumId) || cache[albumId] == null) {
//            val album = storIOContentResolver
//                    .get()
//                    .`object`(ru.stersh.musicmagician.data.core.entity.Album::class.java)
//                    .withQuery(
//                            Query
//                                    .builder()
//                                    .uri("content://media/external/audio/albums")
//                                    .where("_id = ?")
//                                    .whereArgs(albumId)
//                                    .build()
//                    )
//                    .prepare()
//                    .executeAsBlocking()
//            if (album == null) {
//                cache[albumId] = BehaviorSubject.create()
//                cache[albumId]?.onError(Throwable("Album with this id does't exists"))
//            } else {
//                cache[albumId] = BehaviorSubject.createDefault(album)
//            }
//        }
//        return cache[albumId] as Observable<ru.stersh.musicmagician.data.core.entity.Album>
//    }
//
//    fun updateAlbum(album: ru.stersh.musicmagician.data.core.entity.Album?) {
//        cache[album!!.id]?.onNext(album)
//    }
//
//    fun clearCache(album: ru.stersh.musicmagician.data.core.entity.Album?) {
//        if (album != null) {
//            val cover = File(tempAlbumart)
//            if (cover.exists()) cover.delete()
//            cache.remove(album.id)
//        }
//    }
//
//    fun save(album: ru.stersh.musicmagician.data.core.entity.Album?): Completable {
//        return Completable.fromCallable {
//            val whereCause = "album_id = ?"
//            val tracks: MutableList<ru.stersh.musicmagician.data.core.entity.Track>? = storIOContentResolver
//                    .get()
//                    .listOfObjects(ru.stersh.musicmagician.data.core.entity.Track::class.java)
//                    .withQuery(
//                            Query
//                                    .builder()
//                                    .uri(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
//                                    .where(whereCause)
//                                    .whereArgs<Long>(album!!.id)
//                                    .build()
//                    )
//                    .prepare()
//                    .executeAsBlocking()
//
//            if (tracks == null || tracks.isEmpty()) {
//                AlbumartUtils.delete(album.id)
//            } else {
//                Timber.d("Editing songs")
//                val editedTracks = tracks.map {
//                    Timber.d("Edit: ${it.title}")
//                    return@map it.copy(
//                            album = album.title,
//                            artist = album.artist,
//                            year = album.year,
//                            albumart = album.albumart
//                    )
//                }
//
//                Timber.d("Write tags to tracks")
//                editedTracks.forEach { TrackUtils.save(it) }
//
//                Timber.d("Write tracks to Mediastore")
//                storIOContentResolver
//                        .put()
//                        .objects(editedTracks)
//                        .prepare()
//                        .executeAsBlocking()
//                val path = editedTracks[0].path
//                Timber.d("Get new track")
//                val track: ru.stersh.musicmagician.data.core.entity.Track? = storIOContentResolver
//                        .get()
//                        .`object`(ru.stersh.musicmagician.data.core.entity.Track::class.java)
//                        .withQuery(
//                                Query
//                                        .builder()
//                                        .uri(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
//                                        .where("_data = ?")
//                                        .whereArgs(path)
//                                        .build()
//                        )
//                        .prepare()
//                        .executeAsBlocking()
//                if (track != null) {
//                    Timber.d("Track != null")
//                    AlbumartUtils.put(track.albumId, album.albumart)
//                }
//            }
//        }
//    }
//}