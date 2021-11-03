package ru.stersh.musicmagician.data.server.chartlyrics

import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import ru.stersh.musicmagician.data.server.core.LyricTagRepository
import ru.stersh.musicmagician.data.server.core.entity.LyricsTag
import javax.xml.parsers.DocumentBuilderFactory

class ChartlyricsRepository(private val chartlyricsApi: ChartlyricsApi) : LyricTagRepository {

    override suspend fun getTags(title: String, artist: String): List<LyricsTag> {
        if (title.isEmpty() || artist.isEmpty()) {
            return emptyList()
        }
        return chartlyricsApi
            .searchLyrics(title, artist)
            .let {
                val doc: Document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(it.byteStream())

                val nodes: NodeList = doc.getElementsByTagName("GetLyricResult")
                if (nodes.length == 0) {
                    return@let emptyList<LyricsTag>()
                }

                val node: Node = nodes.item(0)
                for (i in 0 until node.childNodes.length) {
                    val temp: Node = node.childNodes.item(i)
                    val lyrics = temp.textContent
                    if (temp.nodeName.equals("Lyric", ignoreCase = true) && lyrics.trim().isNotEmpty()) {
                        return@let listOf(
                            LyricsTag(temp.textContent)
                        )
                    }
                }
                return@let emptyList()
            }
    }
}