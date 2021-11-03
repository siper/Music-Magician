package ru.stersh.musicmagician.ui.activity

import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.github.terrakok.cicerone.NavigatorHolder
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

    private val permissions by inject<PermissionsRepository>()
    private val navigationHolder by inject<NavigatorHolder>()
    private val navigator by lazy { DefaultNavigator(this, R.id.container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_Light)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.tracks -> {
                    presenter.trackLibrary()
                    true
                }
                R.id.albums -> {
                    presenter.albumLibrary()
                    true
                }
                R.id.translate -> {
                    presenter.translate()
                    true
                }
                R.id.feedback -> {
                    presenter.feedback()
                    true
                }
                R.id.privacy_policy -> {
                    presenter.privacyPolicy()
                    true
                }
                else -> false
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        this.permissions.onRequestPermissionResult(permissions, grantResults)
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

    override fun albumLibrary() {
        binding.stubLayout.root.gone()
        binding.container.show()
        binding.navView.setCheckedItem(R.id.albums)
    }

    override fun trackLibrary() {
        binding.stubLayout.root.gone()
        binding.container.show()
        binding.navView.setCheckedItem(R.id.tracks)
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

    fun openDrawer() = binding.drawerLayout.openDrawer(GravityCompat.START)

    fun lockDrawer() = binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

    fun unlockDrawer() = binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
}