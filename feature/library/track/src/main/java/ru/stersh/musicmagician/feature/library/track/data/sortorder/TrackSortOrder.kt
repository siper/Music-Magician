package ru.stersh.musicmagician.feature.library.track.data.sortorder

enum class TrackSortOrder(val order: Int) {
    AZ_TITLE(0),
    ZA_TITLE(1),
    AZ_ARTIST(2),
    ZA_ARTIST(3),
    OLDEST(4),
    NEWEST(5)
}