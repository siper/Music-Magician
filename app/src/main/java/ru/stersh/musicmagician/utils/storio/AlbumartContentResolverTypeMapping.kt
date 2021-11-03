package ru.stersh.musicmagician.utils.storio

import com.pushtorefresh.storio3.contentresolver.ContentResolverTypeMapping

class AlbumartContentResolverTypeMapping :
    ContentResolverTypeMapping<ru.stersh.musicmagician.data.core.entity.Albumart?>(
        AlbumartStorIOContentResolverPutResolver(),
        AlbumartStorIOContentResolverGetResolver(),
        AlbumartStorIOContentResolverDeleteResolver()
    )