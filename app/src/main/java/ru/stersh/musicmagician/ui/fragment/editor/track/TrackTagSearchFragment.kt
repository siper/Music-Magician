package ru.stersh.musicmagician.ui.fragment.editor.track

import android.os.Bundle
import moxy.ktx.moxyPresenter
import org.koin.core.get
import ru.stersh.musicmagician.di.Di
import ru.stersh.musicmagician.data.server.core.entity.Tag
import ru.stersh.musicmagician.presentation.presenter.editor.track.TrackTagSearchPresenter
import ru.stersh.musicmagician.ui.fragment.editor.TagSearchFragment

class TrackTagSearchFragment : TagSearchFragment() {
    private val path: String by lazy { arguments!!.getString(PATH_KEY)!! }
    private val presenter: TrackTagSearchPresenter by moxyPresenter {
        TrackTagSearchPresenter(Di.get(), Di.get(), Di.get(), path)
    }

    override fun applyTag(tag: ru.stersh.musicmagician.data.server.core.entity.Tag) = presenter.applyTag(tag)

    companion object {
        private const val PATH_KEY = "path"
        fun path(path: String?): TrackTagSearchFragment {
            val fr = TrackTagSearchFragment()
            val bundle = Bundle()
            bundle.putString(PATH_KEY, path)
            fr.arguments = bundle
            return fr
        }
    }
}