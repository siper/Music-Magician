package ru.stersh.musicmagician.feature.editor.track.ui.tagsearch

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.stersh.musicmagician.feature.editor.core.ui.tagsearch.TagSearchFragment
import ru.stersh.musicmagician.feature.editor.core.ui.tagsearch.UiItem

class TrackTagSearchFragment : TagSearchFragment() {
    private val viewModel: TrackTagSearchViewModel by viewModel()

    override fun applyTag(tag: UiItem) {
        viewModel.applyTag(tag)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.searchResults.collect {
                showContent(it)
            }
        }
    }
}