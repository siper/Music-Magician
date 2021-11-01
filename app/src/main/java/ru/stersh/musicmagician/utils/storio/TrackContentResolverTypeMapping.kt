package ru.stersh.musicmagician.utils.storio

import com.pushtorefresh.storio3.contentresolver.ContentResolverTypeMapping
import ru.stersh.musicmagician.data.core.entity.Track

class TrackContentResolverTypeMapping : ContentResolverTypeMapping<ru.stersh.musicmagician.data.core.entity.Track?>(TrackStorIOContentResolverPutResolver(),
        TrackStorIOContentResolverGetResolver(),
        TrackStorIOContentResolverDeleteResolver())