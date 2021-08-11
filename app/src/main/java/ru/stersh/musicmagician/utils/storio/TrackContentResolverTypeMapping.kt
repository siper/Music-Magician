package ru.stersh.musicmagician.utils.storio

import com.pushtorefresh.storio3.contentresolver.ContentResolverTypeMapping
import ru.stersh.musicmagician.entity.mediastore.Track

class TrackContentResolverTypeMapping : ContentResolverTypeMapping<Track?>(TrackStorIOContentResolverPutResolver(),
        TrackStorIOContentResolverGetResolver(),
        TrackStorIOContentResolverDeleteResolver())