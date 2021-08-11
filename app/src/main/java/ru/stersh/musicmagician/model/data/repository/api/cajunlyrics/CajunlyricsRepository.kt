package ru.stersh.musicmagician.model.data.repository.api.cajunlyrics

import io.reactivex.Single
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import ru.stersh.musicmagician.entity.tag.LyricsTag
import ru.stersh.musicmagician.model.data.api.cajunlyrics.CajunlyricsApi
import ru.stersh.musicmagician.model.data.repository.api.LyricsTagRepository
import javax.xml.parsers.DocumentBuilderFactory

class CajunlyricsRepository(private val cajunlyricsApi: CajunlyricsApi) : LyricsTagRepository() {
    override fun getTags(title: String, artist: String): Single<List<LyricsTag>> {
        if (title.isEmpty() || artist.isEmpty()) return Single.just(emptyList())
        return cajunlyricsApi
                .searchLyrics(title, artist)
                .map {
                    val doc: Document = DocumentBuilderFactory
                            .newInstance()
                            .newDocumentBuilder()
                            .parse(it.byteStream())

                    val nodes: NodeList = doc.getElementsByTagName("GetLyricResult")
                    if (nodes.length == 0) return@map emptyList<LyricsTag>()

                    val node: Node = nodes.item(0)
                    for (i in 0 until node.childNodes.length) {
                        val temp: Node = node.childNodes.item(i)
                        if (temp.nodeName.equals("Lyric", ignoreCase = true)) {
                            val lyrics: String = temp.textContent
                            if (lyrics.trim() != "Not found") {
                                return@map listOf(LyricsTag(lyrics, providerName))
                            }
                        }
                    }
                    return@map emptyList<LyricsTag>()
                }
    }
}