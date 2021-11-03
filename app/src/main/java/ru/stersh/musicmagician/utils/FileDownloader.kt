package ru.stersh.musicmagician.utils

import io.reactivex.Observable
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream


data class FileDownloader(private val okHttpClient: OkHttpClient) {

    fun download(url: String, path: String): Observable<Int> {
        val f = File(path)
        if (f.exists()) f.delete()
        val call: Call = okHttpClient.newCall(
            Request
                .Builder()
                .url(url)
                .get()
                .build()
        )
        return Observable.fromPublisher<Int> {
            try {
                val response: Response = call.execute()
                if (response.isSuccessful && response.code == 200) {
                    var input: InputStream? = null
                    var output: OutputStream? = null
                    try {
                        input = response.body?.byteStream()
                        output = FileOutputStream(path)
                        val buff = ByteArray(512)
                        var downloaded: Long = 0
                        val total: Long = response.body?.contentLength() ?: 0L
                        it.onNext(0)
                        while (true) {
                            val readed: Int = input?.read(buff) ?: 0
                            if (readed == -1) break
                            downloaded += readed.toLong()
                            output.write(buff, 0, readed)
                            it.onNext(((100 * downloaded) / total).toInt())
                        }
                        it.onNext(100)
                    } finally {
                        output?.flush()
                        output?.close()
                        input?.close()
                    }
                    it.onComplete()
                } else {
                    it.onError(Throwable("Request error"))
                }
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }
}