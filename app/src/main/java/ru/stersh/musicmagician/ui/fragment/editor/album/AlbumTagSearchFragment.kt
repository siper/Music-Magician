package ru.stersh.musicmagician.ui.fragment.editor.album

import android.os.Bundle
import moxy.ktx.moxyPresenter
import org.koin.core.get
import ru.stersh.musicmagician.di.Di
import ru.stersh.musicmagician.entity.tag.Tag
import ru.stersh.musicmagician.presentation.presenter.editor.album.AlbumTagSearchPresenter
import ru.stersh.musicmagician.ui.fragment.editor.TagSearchFragment
import ru.stersh.musicmagician.ui.fragment.editor.album.AlbumEditorFragment.Companion.ALBUM_ID

class AlbumTagSearchFragment : TagSearchFragment() {
    private val albumId by lazy { requireArguments().getLong(ALBUM_ID) }
    private val presenter by moxyPresenter {
        AlbumTagSearchPresenter(Di.get(), Di.get(), Di.get(), albumId)
    }

    override fun applyTag(tag: Tag) = presenter.applyTag(tag)

    companion object {
        fun albumId(id: Long): AlbumTagSearchFragment {
            val bundle = Bundle().apply {
                putLong(ALBUM_ID, id)
            }
            return AlbumTagSearchFragment().apply {
                arguments = bundle
            }
        }
    }
}