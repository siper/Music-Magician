package ru.stersh.musicmagician.feature.editor.album.ui//package ru.stersh.musicmagician.ui.fragment.editor.album
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import moxy.ktx.moxyPresenter
//import org.koin.core.get
//import ru.stersh.musicmagician.Di
//import ru.stersh.musicmagician.presentation.presenter.editor.album.AlbumEditorPresenter
//import ru.stersh.musicmagician.ui.fragment.editor.EditorFragment
//
//class AlbumEditorFragment : EditorFragment() {
//    private val albumId by lazy { requireArguments().getLong(ALBUM_ID) }
//    private val presenter by moxyPresenter {
//        AlbumEditorPresenter(Di.get(), Di.get(), albumId)
//    }
//
//    override fun saveTags() = presenter.save()
//
//    override fun getEditor(): Fragment = AlbumTagEditorFragment.albumId(albumId)
//
//    override fun getSearch(): Fragment = AlbumTagSearchFragment.albumId(albumId)
//
//    companion object {
//        const val ALBUM_ID = "album_id"
//        fun edit(id: Long): AlbumEditorFragment {
//            val bundle = Bundle().apply {
//                putLong(ALBUM_ID, id)
//            }
//            return AlbumEditorFragment().apply {
//                arguments = bundle
//            }
//        }
//    }
//}