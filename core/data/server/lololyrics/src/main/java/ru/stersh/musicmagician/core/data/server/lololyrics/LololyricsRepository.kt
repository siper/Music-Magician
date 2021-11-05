package ru.stersh.musicmagician.core.data.server.lololyrics

import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import ru.stersh.musicmagician.core.data.server.core.LyricTagRepository
import ru.stersh.musicmagician.core.data.server.core.entity.LyricsTag
import javax.xml.parsers.DocumentBuilderFactory

class LololyricsRepository(private val lololyricsApi: LololyricsApi) : LyricTagRepository {
    override suspend fun getTags(title: String, artist: String): List<LyricsTag> {
        if (title.isEmpty() || artist.isEmpty()) {
            return emptyList()
        }
        return lololyricsApi
            .searchLyrics(title, artist)
            .let {
                val doc: Document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(it.byteStream())

                val nodes: NodeList = doc.getElementsByTagName("result")
                if (nodes.length == 0) {
                    return@let emptyList<LyricsTag>()
                }

                val node: Node = nodes.item(0)
                for (i in 0 until node.childNodes.length) {
                    val temp: Node = node.childNodes.item(i)
                    if (temp.nodeName.equals("response", ignoreCase = true)) {
                        val lyrics = temp.textContent
                        if (!lyrics.startsWith("No lyric found") && lyrics.isNotEmpty())
                            return@let listOf(
                                LyricsTag(lyrics)
                            )
                    }
                }
                return@let emptyList<LyricsTag>()
            }
    }
}