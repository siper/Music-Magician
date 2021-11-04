//package ru.stersh.musicmagician.utils.storio
//
//import android.content.ContentValues
//import com.pushtorefresh.storio3.contentresolver.operations.put.DefaultPutResolver
//import com.pushtorefresh.storio3.contentresolver.queries.InsertQuery
//import com.pushtorefresh.storio3.contentresolver.queries.UpdateQuery
//
//class TrackStorIOContentResolverPutResolver : DefaultPutResolver<Track>() {
//
//    override fun mapToInsertQuery(`object`: Track): InsertQuery {
//        return InsertQuery.builder()
//            .uri("content://media/external/audio/media")
//            .build()
//    }
//
//    override fun mapToUpdateQuery(`object`: Track): UpdateQuery {
//        return UpdateQuery.builder()
//            .uri("content://media/external/audio/media")
//            .where("_id = ?")
//            .whereArgs(`object`.id)
//            .build()
//    }
//
//    override fun mapToContentValues(`object`: Track): ContentValues {
//        return ContentValues(11).apply {
//            put("_id", `object`.id)
//            put("_data", `object`.path)
//            put("title", `object`.title)
//            put("date_added", `object`.dateAdded)
//            put("date_modified", `object`.dateModified)
//            put("artist", `object`.artist)
//            put("album", `object`.album)
//            put("track", `object`.trackNumber)
//            put("year", `object`.year)
//            put("album_id", `object`.albumId)
//            put("duration", `object`.duration)
//        }
//    }
//}