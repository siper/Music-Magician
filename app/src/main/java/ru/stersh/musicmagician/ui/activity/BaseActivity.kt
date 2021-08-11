package ru.stersh.musicmagician.ui.activity

import android.content.Context
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import moxy.MvpAppCompatActivity

abstract class BaseActivity : MvpAppCompatActivity() {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }
}