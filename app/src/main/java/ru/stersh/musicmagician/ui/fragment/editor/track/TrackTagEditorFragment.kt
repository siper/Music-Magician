//package ru.stersh.musicmagician.ui.fragment.editor.track
//
//import android.os.Bundle
//import android.text.Editable
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.disposables.CompositeDisposable
//import io.reactivex.rxkotlin.addTo
//import io.reactivex.schedulers.Schedulers
//import org.koin.android.ext.android.inject
//import ru.stersh.musicmagician.databinding.FragmentTrackEditorBinding
//import ru.stersh.musicmagician.model.data.repository.media.TrackRepository
//import ru.stersh.musicmagician.utils.android.EmptyTextWatcher
//import timber.log.Timber
//
//class TrackTagEditorFragment : Fragment() {
//    private val repository by inject<TrackRepository>()
//    private val path: String by lazy { requireArguments().getString(PATH_KEY)!! }
//
//    private val lifecycle = CompositeDisposable()
//    private var track: Track? = null
//
//    private val titleTextWatcher: EmptyTextWatcher = object : EmptyTextWatcher() {
//        override fun afterTextChanged(s: Editable?) {
//            track?.let {
//                repository.updateTrack(it.copy(title = binding.trackEditorTitle.text.toString()))
//            }
//        }
//    }
//    private val albumTextWatcher: EmptyTextWatcher = object : EmptyTextWatcher() {
//        override fun afterTextChanged(s: Editable?) {
//            track?.let {
//                repository.updateTrack(it.copy(album = binding.trackEditorAlbum.text.toString()))
//            }
//        }
//    }
//    private val artistTextWatcher: EmptyTextWatcher = object : EmptyTextWatcher() {
//        override fun afterTextChanged(s: Editable?) {
//            track?.let {
//                repository.updateTrack(it.copy(artist = binding.trackEditorArtist.text.toString()))
//            }
//        }
//    }
//    private val commentTextWatcher: EmptyTextWatcher = object : EmptyTextWatcher() {
//        override fun afterTextChanged(s: Editable?) {
//            track?.let {
//                repository.updateTrack(it.copy(comment = binding.trackEditorComment.text.toString()))
//            }
//        }
//    }
//    private val yearTextWatcher: EmptyTextWatcher = object : EmptyTextWatcher() {
//        override fun afterTextChanged(s: Editable?) {
//            track?.let {
//                repository.updateTrack(it.copy(year = binding.trackEditorYear.text.toString()))
//            }
//        }
//    }
//    private val trackNumberTextWatcher: EmptyTextWatcher = object : EmptyTextWatcher() {
//        override fun afterTextChanged(s: Editable?) {
//            track?.let {
//                repository.updateTrack(it.copy(trackNumber = binding.trackEditorTrackNumber.text.toString()))
//            }
//        }
//    }
//    private val genreTextWatcher: EmptyTextWatcher = object : EmptyTextWatcher() {
//        override fun afterTextChanged(s: Editable?) {
//            track?.let {
//                repository.updateTrack(it.copy(genre = binding.trackEditorGenre.text.toString()))
//            }
//        }
//    }
//    private val lyricsTextWatcher: EmptyTextWatcher = object : EmptyTextWatcher() {
//        override fun afterTextChanged(s: Editable?) {
//            track?.let {
//                repository.updateTrack(it.copy(lyrics = binding.trackEditorLyrics.text.toString()))
//            }
//        }
//    }
//
//    private var _binding: FragmentTrackEditorBinding? = null
//    private val binding
//        get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentTrackEditorBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    override fun onStop() {
//        super.onStop()
//        lifecycle.clear()
//    }
//
//    override fun onStart() {
//        super.onStart()
//        refreshData()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        lifecycle.dispose()
//    }
//
//    private fun refreshData() {
//        repository
//            .getTrack(path)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                { track: Track ->
//                    this.track = track
//                    disableEditor()
//                    if (track.title != binding.trackEditorTitle.text.toString()) {
//                        binding.trackEditorTitle.setText(track.title)
//                    }
//                    if (track.artist != binding.trackEditorArtist.text.toString()) {
//                        binding.trackEditorArtist.setText(track.artist)
//                    }
//                    if (track.album != binding.trackEditorAlbum.text.toString()) {
//                        binding.trackEditorAlbum.setText(track.album)
//                    }
//                    if (track.comment != binding.trackEditorComment.text.toString()) {
//                        binding.trackEditorComment.setText(track.comment)
//                    }
//                    if (track.year != binding.trackEditorYear.text.toString()) {
//                        binding.trackEditorYear.setText(track.year)
//                    }
//                    if (track.trackNumber != binding.trackEditorTrackNumber.text.toString()) {
//                        binding.trackEditorTrackNumber.setText(track.trackNumber)
//                    }
//                    if (track.genre != binding.trackEditorGenre.text.toString()) {
//                        binding.trackEditorGenre.setText(track.genre)
//                    }
//                    if (track.lyrics != binding.trackEditorLyrics.text.toString()) {
//                        binding.trackEditorLyrics.setText(track.lyrics)
//                    }
//                    enableEditor()
//                },
//                {
//                    Timber.e(it)
//                }
//            )
//            .addTo(lifecycle)
//    }
//
//    private fun enableEditor() = with(binding) {
//        trackEditorTitle.addTextChangedListener(titleTextWatcher)
//        trackEditorAlbum.addTextChangedListener(albumTextWatcher)
//        trackEditorArtist.addTextChangedListener(artistTextWatcher)
//        trackEditorComment.addTextChangedListener(commentTextWatcher)
//        trackEditorYear.addTextChangedListener(yearTextWatcher)
//        trackEditorTrackNumber.addTextChangedListener(trackNumberTextWatcher)
//        trackEditorGenre.addTextChangedListener(genreTextWatcher)
//        trackEditorLyrics.addTextChangedListener(lyricsTextWatcher)
//    }
//
//    private fun disableEditor() = with(binding) {
//        trackEditorTitle.removeTextChangedListener(titleTextWatcher)
//        trackEditorAlbum.removeTextChangedListener(albumTextWatcher)
//        trackEditorArtist.removeTextChangedListener(artistTextWatcher)
//        trackEditorComment.removeTextChangedListener(commentTextWatcher)
//        trackEditorYear.removeTextChangedListener(yearTextWatcher)
//        trackEditorTrackNumber.removeTextChangedListener(trackNumberTextWatcher)
//        trackEditorGenre.removeTextChangedListener(genreTextWatcher)
//        trackEditorLyrics.removeTextChangedListener(lyricsTextWatcher)
//    }
//
//    companion object {
//        private const val PATH_KEY = "path"
//
//        fun path(path: String): TrackTagEditorFragment {
//            val fr = TrackTagEditorFragment()
//            val bundle = Bundle()
//            bundle.putString(PATH_KEY, path)
//            fr.arguments = bundle
//            return fr
//        }
//    }
//}