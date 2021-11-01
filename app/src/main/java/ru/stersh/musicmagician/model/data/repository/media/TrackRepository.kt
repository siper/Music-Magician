//package ru.stersh.musicmagician.model.data.repository.media
//
//import android.graphics.BitmapFactory
//import android.provider.MediaStore
//import com.pushtorefresh.storio3.contentresolver.StorIOContentResolver
//import com.pushtorefresh.storio3.contentresolver.queries.Query
//import io.reactivex.BackpressureStrategy
//import io.reactivex.Completable
//import io.reactivex.Flowable
//import io.reactivex.Observable
//import io.reactivex.functions.BiFunction
//import io.reactivex.subjects.BehaviorSubject
//import kotlinx.coroutines.flow.Flow
//import org.jaudiotagger.audio.AudioFileIO
//import org.jaudiotagger.tag.FieldKey
//import ru.stersh.musicmagician.data.core.entity.Album
//import ru.stersh.musicmagician.data.core.entity.Track
//import ru.stersh.musicmagician.extention.getFieldOrEmpty
//import ru.stersh.musicmagician.tempAlbumart
//import ru.stersh.musicmagician.utils.tag.AlbumartUtils
//import ru.stersh.musicmagician.utils.tag.TrackUtils
//import timber.log.Timber
//import java.io.File
//
//class TrackRepository(
//    private val storIOContentResolver: StorIOContentResolver
//) {
//    private val cache: MutableMap<String, BehaviorSubject<ru.stersh.musicmagician.data.core.entity.Track>> = mutableMapOf()
//
//    fun getTracks(hideShortTracks: Boolean = true): Flow<List<ru.stersh.musicmagician.data.core.entity.Track>> {
//        var whereCause = MediaStore.Audio.Media.IS_MUSIC + " != 0"
//        if (hideShortTracks) {
//            whereCause += " AND duration > 30000"
//        }
//        val tracks = storIOContentResolver
//            .get()
//            .listOfObjects(ru.stersh.musicmagician.data.core.entity.Track::class.java)
//            .withQuery(
//                Query.builder()
//                    .uri(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
//                    .where(whereCause)
//                    .build()
//            )
//            .prepare()
//            .asRxFlowable(BackpressureStrategy.BUFFER)
//        val albums = getAlbums()
//        return Flowable.combineLatest(tracks, albums, BiFunction { t, a ->
//            return@BiFunction t.map { track ->
//                return@map track.copy(albumart = a.firstOrNull { it.id == track.albumId }?.albumart.orEmpty())
//            }
//        })
//    }
//
//    fun getTrack(path: String) = getTrack(File(path))
//
//    fun getTrack(file: File): Observable<ru.stersh.musicmagician.data.core.entity.Track> {
//        val path = file.absolutePath
//        if (!cache.containsKey(path) || cache[path] == null) {
//            val mp3File = AudioFileIO.read(file)
//            val tag = mp3File.tag
//            val track = ru.stersh.musicmagician.data.core.entity.Track(
//                path = file.absolutePath,
//                title = if (tag.hasField(FieldKey.TITLE)) {
//                    tag.getFirst(FieldKey.TITLE)
//                } else {
//                    val filename: String = file.name
//                    filename.substring(0, filename.lastIndexOf("."))
//                },
//                album = tag.getFieldOrEmpty(FieldKey.ALBUM),
//                artist = tag.getFieldOrEmpty(FieldKey.ARTIST),
//                trackNumber = tag.getFieldOrEmpty(FieldKey.TRACK),
//                year = tag.getFieldOrEmpty(FieldKey.YEAR),
//                genre = tag.getFieldOrEmpty(FieldKey.GENRE),
//                comment = tag.getFieldOrEmpty(FieldKey.COMMENT),
//                lyrics = tag.getFieldOrEmpty(FieldKey.LYRICS),
//                albumart = if (tag.hasField(FieldKey.COVER_ART)) {
//                    val cover = tag.firstArtwork.binaryData
//                    if (cover != null) {
//                        val bmp = BitmapFactory.decodeByteArray(cover, 0, cover.size)
//                        AlbumartUtils.saveAlbumartToSd(bmp)
//                        tempAlbumart
//                    } else {
//                        ""
//                    }
//                } else {
//                    ""
//                }
//            )
//            cache[path] = BehaviorSubject.createDefault(track)
//        }
//        return cache[path] as Observable<ru.stersh.musicmagician.data.core.entity.Track>
//    }
//
//    fun updateTrack(track: ru.stersh.musicmagician.data.core.entity.Track) {
//        cache[track.path]?.onNext(track)
//    }
//
//    fun clearCache(track: ru.stersh.musicmagician.data.core.entity.Track?) {
//        if (track == null) return
//
//        if (track.albumart.isNotEmpty()) {
//            val cover = File(track.albumart)
//            if (cover.exists()) cover.delete()
//        }
//        cache.remove(track.path)
//    }
//
//    fun save(track: ru.stersh.musicmagician.data.core.entity.Track?): Completable {
//        return Completable.fromCallable {
//            if (track == null) return@fromCallable null
//            TrackUtils.save(track)
//            if (track.album.isEmpty() || track.album.isBlank()) {
//                return@fromCallable null
//            }
//            try {
//                val oldTrack = storIOContentResolver
//                    .get()
//                    .`object`(ru.stersh.musicmagician.data.core.entity.Track::class.java)
//                    .withQuery(
//                        Query
//                            .builder()
//                            .uri(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
//                            .where("_data = ?")
//                            .whereArgs(track.path)
//                            .build()
//                    )
//                    .prepare()
//                    .executeAsBlocking()
//                if (oldTrack != null) {
//                    val oldTrackCopy = oldTrack.copy(
//                        title = track.title,
//                        artist = track.artist,
//                        album = track.album,
//                        trackNumber = track.trackNumber,
//                        year = track.year
//                    )
//
//                    storIOContentResolver
//                        .put()
//                        .`object`(oldTrackCopy)
//                        .prepare()
//                        .executeAsBlocking()
//                    val newTrack = storIOContentResolver
//                        .get()
//                        .`object`(ru.stersh.musicmagician.data.core.entity.Track::class.java)
//                        .withQuery(
//                            Query
//                                .builder()
//                                .uri(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
//                                .where("_data = ?")
//                                .whereArgs(track.path)
//                                .build()
//                        )
//                        .prepare()
//                        .executeAsBlocking()
//                    if (newTrack != null) {
//                        AlbumartUtils.put(newTrack.albumId, track.albumart)
//                    }
//                }
//                return@fromCallable null
//            } catch (e: Exception) {
//                Timber.e(e)
//            }
//        }
//    }
//
//    private fun getAlbums(): Flowable<List<ru.stersh.musicmagician.data.core.entity.Album>> {
//        return storIOContentResolver
//            .get()
//            .listOfObjects(ru.stersh.musicmagician.data.core.entity.Album::class.java)
//            .withQuery(
//                Query
//                    .builder()
//                    .uri(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI)
//                    .build()
//            )
//            .prepare()
//            .asRxFlowable(BackpressureStrategy.BUFFER)
//    }
//}