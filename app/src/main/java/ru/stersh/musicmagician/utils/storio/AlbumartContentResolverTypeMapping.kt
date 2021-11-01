package ru.stersh.musicmagician.utils.storio

import com.pushtorefresh.storio3.contentresolver.ContentResolverTypeMapping
import ru.stersh.musicmagician.data.core.entity.Albumart

class AlbumartContentResolverTypeMapping : ContentResolverTypeMapping<ru.stersh.musicmagician.data.core.entity.Albumart?>(AlbumartStorIOContentResolverPutResolver(),
        AlbumartStorIOContentResolverGetResolver(),
        AlbumartStorIOContentResolverDeleteResolver())