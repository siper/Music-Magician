package ru.stersh.musicmagician.navigation

import com.github.terrakok.cicerone.Router
import ru.stersh.musicmagician.Screens
import ru.stersh.musicmagician.feature.library.track.navigation.TrackLibraryNavigation

class TrackLibraryNavigationImpl(private val router: Router) : TrackLibraryNavigation {
    override fun openTrackEditor(trackId: Long) {
        router.navigateTo(Screens.trackEditor(trackId))
    }
}