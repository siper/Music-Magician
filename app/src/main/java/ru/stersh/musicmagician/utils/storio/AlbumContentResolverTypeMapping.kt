package ru.stersh.musicmagician.utils.storio

import com.pushtorefresh.storio3.contentresolver.ContentResolverTypeMapping
import ru.stersh.musicmagician.entity.mediastore.Album

class AlbumContentResolverTypeMapping : ContentResolverTypeMapping<Album?>(AlbumStorIOContentResolverPutResolver(),
        AlbumStorIOContentResolverGetResolver(),
        AlbumStorIOContentResolverDeleteResolver())