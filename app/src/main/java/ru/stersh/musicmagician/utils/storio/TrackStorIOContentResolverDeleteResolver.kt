package ru.stersh.musicmagician.utils.storio

import com.pushtorefresh.storio3.contentresolver.operations.delete.DefaultDeleteResolver
import com.pushtorefresh.storio3.contentresolver.queries.DeleteQuery
import ru.stersh.musicmagician.entity.mediastore.Track

class TrackStorIOContentResolverDeleteResolver : DefaultDeleteResolver<Track>() {

    public override fun mapToDeleteQuery(`object`: Track): DeleteQuery {
        return DeleteQuery.builder()
                .uri("content://media/external/audio/media")
                .where("_id = ?")
                .whereArgs(`object`.id)
                .build()
    }
}