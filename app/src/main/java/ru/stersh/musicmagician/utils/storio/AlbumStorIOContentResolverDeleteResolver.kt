package ru.stersh.musicmagician.utils.storio

import com.pushtorefresh.storio3.contentresolver.operations.delete.DefaultDeleteResolver
import com.pushtorefresh.storio3.contentresolver.queries.DeleteQuery

class AlbumStorIOContentResolverDeleteResolver :
    DefaultDeleteResolver<ru.stersh.musicmagician.data.core.internal.entity.Album>() {
    /**
     * {@inheritDoc}
     */
    public override fun mapToDeleteQuery(`object`: ru.stersh.musicmagician.data.core.internal.entity.Album): DeleteQuery {
        return DeleteQuery.builder()
            .uri("content://media/external/audio/albums")
            .where("_id = ?")
            .whereArgs(`object`.id)
            .build()
    }
}