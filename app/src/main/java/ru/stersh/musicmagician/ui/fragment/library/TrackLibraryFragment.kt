package ru.stersh.musicmagician.ui.fragment.library

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.terrakok.cicerone.Router
import moxy.ktx.moxyPresenter
import org.koin.android.ext.android.inject
import org.koin.core.get
import ru.stersh.musicmagician.R
import ru.stersh.musicmagician.Screens
import ru.stersh.musicmagician.di.Di
import ru.stersh.musicmagician.entity.app.ui.TrackProgressItem
import ru.stersh.musicmagician.entity.app.ui.TrackSortOrder
import ru.stersh.musicmagician.extention.dp
import ru.stersh.musicmagician.extention.gone
import ru.stersh.musicmagician.extention.show
import ru.stersh.musicmagician.presentation.presenter.library.TrackLibraryPresenter
import ru.stersh.musicmagician.ui.adapter.library.TrackLibraryAdapter
import ru.stersh.musicmagician.ui.divider.TopAndBottomMargin

class TrackLibraryFragment : LibraryFragment() {
    private val router by inject<Router>()
    private val presenter by moxyPresenter {
        TrackLibraryPresenter(Di.get(), Di.get())
    }
    private val progressItems: List<TrackProgressItem> by lazy {
        val items = mutableListOf<TrackProgressItem>()
        for (i in 1..30) {
            items.add(TrackProgressItem)
        }
        return@lazy items.toList()
    }
    override val adapter = TrackLibraryAdapter { track, image ->
        router.navigateTo(Screens.trackEditor(track))
    }
    override val title: String by lazy { getString(R.string.drawer_tracks) }
    override val menuLayout: Int = R.menu.tracks_menu

    override fun search(query: String) = presenter.search(query)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.az_title_sort -> {
                presenter.sort(TrackSortOrder.AZ_TITLE)
                return true
            }
            R.id.za_title_sort -> {
                presenter.sort(TrackSortOrder.ZA_TITLE)
                return true
            }
            R.id.az_artist_sort -> {
                presenter.sort(TrackSortOrder.AZ_ARTIST)
                return true
            }
            R.id.za_artist_sort -> {
                presenter.sort(TrackSortOrder.ZA_ARTIST)
                return true
            }
            R.id.oldest_sort -> {
                presenter.sort(TrackSortOrder.OLDEST)
                return true
            }
            R.id.newest_sort -> {
                presenter.sort(TrackSortOrder.NEWEST)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setSortOrder(order: Int) {
        val itemId = when (order) {
            TrackSortOrder.ZA_TITLE.order -> R.id.za_title_sort
            TrackSortOrder.AZ_ARTIST.order -> R.id.az_artist_sort
            TrackSortOrder.ZA_ARTIST.order -> R.id.za_artist_sort
            TrackSortOrder.NEWEST.order -> R.id.newest_sort
            TrackSortOrder.OLDEST.order -> R.id.oldest_sort
            else -> R.id.az_title_sort
        }
        menu?.findItem(itemId)?.isChecked = true
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        setSortOrder(presenter.getSortOrder())
        super.onPrepareOptionsMenu(menu)
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
    }

    override fun showProgress() {
        adapter.items = progressItems
        binding.content.show()
        binding.stub.root.gone()
    }
}