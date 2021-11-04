package ru.stersh.musicmagician.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import ru.stersh.musicmagician.R
import ru.stersh.musicmagician.Screens
import ru.stersh.musicmagician.core.navigation.DrawerNavigation
import ru.stersh.musicmagician.databinding.ActivityMainBinding
import ru.stersh.musicmagician.presentation.view.main.MainView
import ru.stersh.musicmagician.utils.DefaultNavigator

class MainActivity : AppCompatActivity(), MainView, DrawerNavigation {
//    private val presenter by moxyPresenter {
//        MainPresenter(Di.get(), Di.get(), Di.get())
//    }

    private lateinit var binding: ActivityMainBinding

//    private val permissions by inject<PermissionsRepository>()
    private val navigationHolder by inject<NavigatorHolder>()
    private val navigator by lazy { DefaultNavigator(this, R.id.container) }
    private val router by inject<Router>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_Light)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        router.newRootScreen(Screens.trackLibraryScreen())

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.tracks -> {
                    router.newRootScreen(Screens.trackLibraryScreen())
                    true
                }
                R.id.albums -> {
                    router.newRootScreen(Screens.albumLibraryScreen())
                    true
                }
                R.id.translate -> {
                    router.navigateTo(Screens.translate())
                    true
                }
                R.id.feedback -> {
                    router.navigateTo(Screens.feedback())
                    true
                }
                R.id.privacy_policy -> {
                    router.navigateTo(Screens.piracyPolicy())
                    true
                }
                else -> false
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        this.permissions.onRequestPermissionResult(permissions, grantResults)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigator)
    }

    override fun onResume() {
        super.onResume()
//        permissions.attachActivity(this)
    }

    override fun onPause() {
        super.onPause()
//        permissions.detachActivity()
        navigationHolder.removeNavigator()
    }

    override fun albumLibrary() {
//        binding.stubLayout.root.gone()
//        binding.container.show()
        binding.navView.setCheckedItem(R.id.albums)
    }

    override fun trackLibrary() {
//        binding.stubLayout.root.gone()
//        binding.container.show()
        binding.navView.setCheckedItem(R.id.tracks)
    }

    override fun showPermissionsError() {
//        binding.container.gone()
//        binding.stubLayout.root.show()
//        binding.stubLayout.stubTitle.text = getString(R.string.permissions_error_title)
//        binding.stubLayout.stubMessage.text = getString(R.string.permissions_error_message)
//        binding.stubLayout.stubButton.show()
//        binding.stubLayout.stubButton.text = getString(R.string.permissions_error_button)
//        binding.stubLayout.stubButton.setOnClickListener { presenter.requestPermissions() }
    }

    override fun openDrawer() = binding.drawerLayout.openDrawer(GravityCompat.START)

    override fun lockDrawer() = binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

    override fun unlockDrawer() = binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

    override fun closeDrawer() = binding.drawerLayout.closeDrawer(GravityCompat.START)
}