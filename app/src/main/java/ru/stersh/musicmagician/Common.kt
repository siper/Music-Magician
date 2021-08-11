package ru.stersh.musicmagician

import android.net.Uri
import android.os.Build
import android.os.Environment
import timber.log.Timber
import java.io.File
import java.io.IOException

val internalCache: String
    get() = MusicMagician.context.cacheDir.absolutePath

val thumbnails: String
    get() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val p = MusicMagician.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath +
                    "/albumthumbs/"
            File(p).apply {
                if (!exists()) mkdirs()
            }
            File("$p.nomedia").apply {
                if (!exists()) try {
                    createNewFile()
                } catch (e: IOException) {
                    Timber.e(e, "Error creating .nomedia file")
                }
            }
            return p
        } else {
            return Environment.getExternalStorageDirectory()!!.absolutePath + "/albumthumbs/"
        }
    }

val tempAlbumart: String
    get() = "$internalCache/cover.jpg"

val albumartUri: Uri
    get() = Uri.parse("content://media/external/audio/albumart")

val translateUrl: Uri
    get() = Uri.parse("https://stersh.oneskyapp.com/collaboration/project?id=351498")

val feedbackEmail: String
    get() = "musicmagician@stersh.ru"