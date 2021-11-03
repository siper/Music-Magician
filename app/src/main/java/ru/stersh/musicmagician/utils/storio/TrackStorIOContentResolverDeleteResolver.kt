package ru.stersh.musicmagician.utils.storio

import com.pushtorefresh.storio3.contentresolver.operations.delete.DefaultDeleteResolver
import com.pushtorefresh.storio3.contentresolver.queries.DeleteQuery

class TrackStorIOContentResolverDeleteResolver :
    DefaultDeleteResolver<ru.stersh.musicmagician.data.core.entity.Track>() {

    public override fun mapToDeleteQuery(`object`: ru.stersh.musicmagician.data.core.entity.Track): DeleteQuery {
        return DeleteQuery.builder()
            .uri("content://media/external/audio/media")
            .where("_id = ?")
            .whereArgs(`object`.id)
            .build()
    }
}