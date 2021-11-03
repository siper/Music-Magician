package ru.stersh.musicmagician.feature.library.track.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.stersh.musicmagician.feature.library.core.LibraryFragment
import ru.stersh.musicmagician.feature.library.track.R
import ru.stersh.musicmagician.feature.library.track.navigation.TrackLibraryNavigation
import ru.stersh.musicmagician.feature.library.track.entity.UiItem
import ru.stersh.musicmagician.feature.library.track.entity.UiTrackSortOrder
import ru.stersh.musicmagician.ui.extension.dp

class TrackLibraryFragment : LibraryFragment<UiItem, UiTrackSortOrder, TrackLibraryViewModel>() {
    private val navigation by inject<TrackLibraryNavigation>()
    override val viewModel: TrackLibraryViewModel by viewModel()
    override val adapter = TrackLibraryAdapter { track, _ ->
        navigation.openTrackEditor(track.id)
    }
    override val menuLayout: Int = R.menu.tracks_menu
    override val titleRes: Int = R.string.tracks
    override val searchItemId: Int = R.id.search

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.az_title_sort -> {
                viewModel.sort(UiTrackSortOrder.AZ_TITLE)
                return true
            }
            R.id.za_title_sort -> {
                viewModel.sort(UiTrackSortOrder.ZA_TITLE)
                return true
            }
            R.id.az_artist_sort -> {
                viewModel.sort(UiTrackSortOrder.AZ_ARTIST)
                return true
            }
            R.id.za_artist_sort -> {
                viewModel.sort(UiTrackSortOrder.ZA_ARTIST)
                return true
            }
            R.id.oldest_sort -> {
                viewModel.sort(UiTrackSortOrder.OLDEST)
                return true
            }
            R.id.newest_sort -> {
                viewModel.sort(UiTrackSortOrder.NEWEST)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val orientation = requireActivity().resources.configuration.orientation
        val layoutManager = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutManager(activity).apply {
                setOrientation(LinearLayoutManager.VERTICAL)
            }
        } else {
            GridLayoutManager(activity, 2).apply {
                setOrientation(LinearLayoutManager.VERTICAL)
            }
        }
        binding.content.adapter = adapter
        binding.content.layoutManager = layoutManager
        binding.content.addItemDecoration(TopAndBottomMargin(8.dp))
        binding.content.setHasFixedSize(true)

        subscribeSortOrder()
        subscribeItems()
    }

    private fun subscribeSortOrder() = lifecycleScope.launchWhenStarted {
        viewModel.sortOrder.collect {
            val itemId = when (it) {
                UiTrackSortOrder.AZ_TITLE -> R.id.az_title_sort
                UiTrackSortOrder.ZA_TITLE -> R.id.za_title_sort
                UiTrackSortOrder.AZ_ARTIST -> R.id.az_artist_sort
                UiTrackSortOrder.ZA_ARTIST -> R.id.za_artist_sort
                UiTrackSortOrder.NEWEST -> R.id.newest_sort
                UiTrackSortOrder.OLDEST -> R.id.oldest_sort
            }
            binding.toolbar.menu?.findItem(itemId)?.isChecked = true
        }
    }

    private fun subscribeItems() = lifecycleScope.launchWhenStarted {
        viewModel.items.collect {
            adapter.items = it
        }
    }
}