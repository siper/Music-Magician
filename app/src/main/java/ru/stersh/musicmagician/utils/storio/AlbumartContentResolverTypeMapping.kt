package ru.stersh.musicmagician.utils.storio

import com.pushtorefresh.storio3.contentresolver.ContentResolverTypeMapping
import ru.stersh.musicmagician.entity.mediastore.Albumart

class AlbumartContentResolverTypeMapping : ContentResolverTypeMapping<Albumart?>(AlbumartStorIOContentResolverPutResolver(),
        AlbumartStorIOContentResolverGetResolver(),
        AlbumartStorIOContentResolverDeleteResolver())