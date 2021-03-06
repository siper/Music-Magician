package ru.stersh.musicmagician

import android.content.Intent
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.stersh.musicmagician.entity.mediastore.Album
import ru.stersh.musicmagician.entity.mediastore.Track
import ru.stersh.musicmagician.ui.fragment.PrivacyPolicyFragment
import ru.stersh.musicmagician.ui.fragment.editor.album.AlbumEditorFragment
import ru.stersh.musicmagician.ui.fragment.editor.track.TrackEditorFragment
import ru.stersh.musicmagician.ui.fragment.library.AlbumLibraryFragment
import ru.stersh.musicmagician.ui.fragment.library.TrackLibraryFragment

object Screens {
    fun trackLibraryScreen() = FragmentScreen {
        TrackLibraryFragment()
    }

    fun albumLibraryScreen() = FragmentScreen {
        AlbumLibraryFragment()
    }

    fun piracyPolicy() = FragmentScreen {
        PrivacyPolicyFragment()
    }

    fun translate() = ActivityScreen {
        Intent(Intent.ACTION_VIEW).apply {
            data = translateUrl
        }
    }

    fun albumEditor(album: Album) = FragmentScreen {
        AlbumEditorFragment.edit(album.id)
    }

    fun trackEditor(track: Track) = FragmentScreen {
        TrackEditorFragment.edit(track)
    }

    fun feedback() = ActivityScreen {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "MusicMagician - Music tag editor")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(feedbackEmail))
        }
        Intent.createChooser(intent, it.getString(R.string.leave_feedback))
    }
}