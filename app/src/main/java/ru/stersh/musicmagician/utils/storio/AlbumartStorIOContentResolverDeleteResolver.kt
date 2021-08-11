package ru.stersh.musicmagician.utils.storio

import android.content.ContentUris
import com.pushtorefresh.storio3.contentresolver.operations.delete.DefaultDeleteResolver
import com.pushtorefresh.storio3.contentresolver.queries.DeleteQuery
import ru.stersh.musicmagician.albumartUri
import ru.stersh.musicmagician.entity.mediastore.Albumart

class AlbumartStorIOContentResolverDeleteResolver : DefaultDeleteResolver<Albumart>() {

    override fun mapToDeleteQuery(albumart: Albumart): DeleteQuery {
        return DeleteQuery
                .builder()
                .uri(ContentUris.withAppendedId(albumartUri, albumart.albumId))
                .build()
    }
}