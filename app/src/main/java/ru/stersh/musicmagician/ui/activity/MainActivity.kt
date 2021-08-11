package ru.stersh.musicmagician.ui.activity

import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import com.github.terrakok.cicerone.NavigatorHolder
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import moxy.ktx.moxyPresenter
import org.koin.android.ext.android.inject
import org.koin.core.get
import ru.stersh.musicmagician.R
import ru.stersh.musicmagician.databinding.ActivityMainBinding
import ru.stersh.musicmagician.di.Di
import ru.stersh.musicmagician.extention.gone
import ru.stersh.musicmagician.extention.show
import ru.stersh.musicmagician.model.data.repository.app.PermissionsRepository
import ru.stersh.musicmagician.presentation.presenter.main.MainPresenter
import ru.stersh.musicmagician.presentation.view.main.MainView
import ru.stersh.musicmagician.utils.DefaultNavigator

class MainActivity : BaseActivity(), MainView {
    private val presenter by moxyPresenter {
        MainPresenter(Di.get(), Di.get(), Di.get())
    }

    private lateinit var binding: ActivityMainBinding
    private val drawer: Drawer by lazy { createDrawer() }

    private val permissions by inject<PermissionsRepository>()
    private val navigationHolder by inject<NavigatorHolder>()
    private val navigator by lazy { DefaultNavigator(this, R.id.container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_Light)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        this.permissions.onRequestPermissionResult(requestCode, permissions, grantResults)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigator)
    }

    override fun onResume() {
        super.onResume()
        permissions.attachActivity(this)
    }

    override fun onPause() {
        super.onPause()
        permissions.detachActivity()
        navigationHolder.removeNavigator()
    }

    override fun hideBilling() = drawer.removeItem(2)

    override fun albumLibrary() {
        binding.stubLayout.root.gone()
        binding.container.show()
        drawer.setSelection(1, false)
    }

    override fun trackLibrary() {
        binding.stubLayout.root.gone()
        binding.container.show()
        drawer.setSelection(0, false)
    }

    override fun showPermissionsError() {
        binding.container.gone()
        binding.stubLayout.root.show()
        binding.stubLayout.stubTitle.text = getString(R.string.permissions_error_title)
        binding.stubLayout.stubMessage.text = getString(R.string.permissions_error_message)
        binding.stubLayout.stubButton.show()
        binding.stubLayout.stubButton.text = getString(R.string.permissions_error_button)
        binding.stubLayout.stubButton.setOnClickListener { presenter.requestPermissions() }
    }

    fun openDrawer() = drawer.openDrawer()

    fun lockDrawer() = drawer.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

    fun unlockDrawer() = drawer.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

    private fun createDrawer(): Drawer {
        val headerResult = AccountHeaderBuilder()
                .withActivity(this)
                .build()
        val tracks = PrimaryDrawerItem()
                .withIdentifier(0)
                .withName(R.string.drawer_tracks)
                .withIconTintingEnabled(true)
                .withDisabledIconColorRes(R.color.colorGray)
                .withIcon(R.drawable.ic_audiotrack_24px)
                .withIconColorRes(R.color.colorGray)
        val albums = PrimaryDrawerItem()
                .withIdentifier(1)
                .withName(R.string.drawer_albums)
                .withIconTintingEnabled(true)
                .withDisabledIconColorRes(R.color.colorGray)
                .withIcon(R.drawable.ic_album_24px)
                .withIconColorRes(R.color.colorGray)
        val feedback = SecondaryDrawerItem()
                .withIdentifier(4)
                .withName(R.string.leave_feedback)
                .withIconTintingEnabled(true)
                .withSelectable(false)
                .withDisabledIconColorRes(R.color.colorGray)
                .withIcon(R.drawable.ic_email_24px)
                .withIconColorRes(R.color.colorGray)
        val translate = SecondaryDrawerItem()
                .withIdentifier(8)
                .withName(R.string.translate_app)
                .withIconTintingEnabled(true)
                .withSelectable(false)
                .withDisabledIconColorRes(R.color.colorGray)
                .withIcon(R.drawable.ic_translate_24px)
                .withIconColorRes(R.color.colorGray)
        val piracyPolicy = SecondaryDrawerItem()
                .withIdentifier(6)
                .withName(R.string.privacy_policy)
                .withIconTintingEnabled(true)
                .withSelectable(false)
                .withDisabledIconColorRes(R.color.colorGray)
                .withIcon(R.drawable.ic_info_24px)
                .withIconColorRes(R.color.colorGray)
        return DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        tracks,
                        albums,
                        DividerDrawerItem(),
                        translate,
                        piracyPolicy,
                        feedback
                )
                .withCloseOnClick(true)
                .withOnDrawerItemClickListener { view, position, drawerItem ->
                    when (drawerItem.identifier.toInt()) {
                        0 -> {
                            presenter.trackLibrary()
                            false
                        }
                        1 -> {
                            presenter.albumLibrary()
                            false
                        }
                        3 -> {
                            false
                        }
                        4 -> {
                            presenter.feedback()
                            false
                        }
                        6 -> {
                            presenter.privacyPolicy()
                            false
                        }
                        8 -> {
                            presenter.translate()
                            false
                        }
                        else -> {
                            presenter.trackLibrary()
                            false
                        }
                    }
                }
                .build()
    }
}