package ru.stersh.musicmagician.utils.storio

import android.content.ContentUris
import com.pushtorefresh.storio3.contentresolver.operations.delete.DefaultDeleteResolver
import com.pushtorefresh.storio3.contentresolver.queries.DeleteQuery
import ru.stersh.musicmagician.albumartUri

class AlbumartStorIOContentResolverDeleteResolver :
    DefaultDeleteResolver<ru.stersh.musicmagician.data.core.entity.Albumart>() {

    override fun mapToDeleteQuery(albumart: ru.stersh.musicmagician.data.core.entity.Albumart): DeleteQuery {
        return DeleteQuery
            .builder()
            .uri(ContentUris.withAppendedId(albumartUri, albumart.albumId))
            .build()
    }
}