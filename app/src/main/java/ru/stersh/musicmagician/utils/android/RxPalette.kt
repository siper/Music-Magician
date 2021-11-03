package ru.stersh.musicmagician.utils.android

import android.graphics.Bitmap
import androidx.palette.graphics.Palette
import androidx.palette.graphics.Target
import io.reactivex.Single

object RxPalette {
    private val DOMINANT = Target
        .Builder()
        .setPopulationWeight(1f)
        .setSaturationWeight(0f)
        .setLightnessWeight(0f)
        .setExclusive(false)
        .build()

    fun generate(bitmap: Bitmap): Single<Palette> {
        return Single.fromCallable {
            Palette.from(bitmap).addTarget(DOMINANT).generate()
        }
    }
}