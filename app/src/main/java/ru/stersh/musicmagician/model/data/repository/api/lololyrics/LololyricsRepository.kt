package ru.stersh.musicmagician.model.data.repository.api.lololyrics

import io.reactivex.Single
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import ru.stersh.musicmagician.data.server.core.entity.LyricsTag
import ru.stersh.musicmagician.model.data.api.lololyrics.LololyricsApi
import ru.stersh.musicmagician.model.data.repository.api.LyricsTagRepository
import javax.xml.parsers.DocumentBuilderFactory

class LololyricsRepository(private val lololyricsApi: LololyricsApi) : LyricsTagRepository() {
    override fun getTags(title: String, artist: String): Single<List<ru.stersh.musicmagician.data.server.core.entity.LyricsTag>> {
        if (title.isEmpty() || artist.isEmpty()) return Single.just(emptyList())
        return lololyricsApi
                .searchLyrics(title, artist)
                .map {
                    val doc: Document = DocumentBuilderFactory
                            .newInstance()
                            .newDocumentBuilder()
                            .parse(it.byteStream())

                    val nodes: NodeList = doc.getElementsByTagName("result")
                    if (nodes.length == 0) return@map emptyList<ru.stersh.musicmagician.data.server.core.entity.LyricsTag>()

                    val node: Node = nodes.item(0)
                    for (i in 0 until node.childNodes.length) {
                        val temp: Node = node.childNodes.item(i)
                        if (temp.nodeName.equals("response", ignoreCase = true)) {
                            val lyrics = temp.textContent
                            if (!lyrics.startsWith("No lyric found") && lyrics.isNotEmpty())
                                return@map listOf(
                                    ru.stersh.musicmagician.data.server.core.entity.LyricsTag(
                                        lyrics,
                                        providerName
                                    )
                                )
                        }
                    }
                    return@map emptyList<ru.stersh.musicmagician.data.server.core.entity.LyricsTag>()
                }
    }
}