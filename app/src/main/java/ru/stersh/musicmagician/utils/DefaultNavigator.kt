package ru.stersh.musicmagician.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.transition.TransitionInflater
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.stersh.musicmagician.extention.hideKeyboard
import ru.stersh.musicmagician.feature.library.core.LibraryFragment
import ru.stersh.musicmagician.ui.activity.MainActivity
import ru.stersh.musicmagician.ui.fragment.editor.EditorFragment

class DefaultNavigator(
    private val main: MainActivity, container: Int
) : AppNavigator(main, container) {
    override fun applyCommands(commands: Array<out Command>) {
        main.hideKeyboard()
        main.lockDrawer()
        super.applyCommands(commands)
    }

    override fun setupFragmentTransaction(
        screen: FragmentScreen,
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment
    ) {
        if (currentFragment is ru.stersh.musicmagician.feature.library.core.LibraryFragment && nextFragment is EditorFragment) {
            nextFragment.apply {
                enterTransition = TransitionInflater
                    .from(this@DefaultNavigator.activity)
                    .inflateTransition(android.R.transition.fade)
                exitTransition = TransitionInflater
                    .from(this@DefaultNavigator.activity)
                    .inflateTransition(android.R.transition.fade)
            }
        }
    }
}