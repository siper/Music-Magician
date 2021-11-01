package ru.stersh.musicmagician.utils.storio

import com.pushtorefresh.storio3.contentresolver.ContentResolverTypeMapping
import ru.stersh.musicmagician.data.core.entity.Album

class AlbumContentResolverTypeMapping : ContentResolverTypeMapping<ru.stersh.musicmagician.data.core.entity.Album?>(AlbumStorIOContentResolverPutResolver(),
        AlbumStorIOContentResolverGetResolver(),
        AlbumStorIOContentResolverDeleteResolver())