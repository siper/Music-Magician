package ru.stersh.musicmagician.model.data.repository.api.chartlyrics

import io.reactivex.Single
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import ru.stersh.musicmagician.data.server.core.entity.LyricsTag
import ru.stersh.musicmagician.model.data.api.chartlyrics.ChartlyricsApi
import ru.stersh.musicmagician.model.data.repository.api.LyricsTagRepository
import javax.xml.parsers.DocumentBuilderFactory

class ChartlyricsRepository(private val chartlyricsApi: ChartlyricsApi) : LyricsTagRepository() {
    override fun getTags(title: String, artist: String): Single<List<ru.stersh.musicmagician.data.server.core.entity.LyricsTag>> {
        if (title.isEmpty() || artist.isEmpty()) return Single.just(emptyList())
        return chartlyricsApi
                .searchLyrics(title, artist)
                .map {
                    val doc: Document = DocumentBuilderFactory
                            .newInstance()
                            .newDocumentBuilder()
                            .parse(it.byteStream())

                    val nodes: NodeList = doc.getElementsByTagName("GetLyricResult")
                    if (nodes.length == 0) return@map emptyList<ru.stersh.musicmagician.data.server.core.entity.LyricsTag>()

                    val node: Node = nodes.item(0)
                    for (i in 0 until node.childNodes.length) {
                        val temp: Node = node.childNodes.item(i)
                        val lyrics = temp.textContent
                        if (temp.nodeName.equals("Lyric", ignoreCase = true) && lyrics.trim().isNotEmpty()) {
                            return@map listOf(
                                ru.stersh.musicmagician.data.server.core.entity.LyricsTag(
                                    temp.textContent,
                                    providerName
                                )
                            )
                        }
                    }
                    return@map emptyList<ru.stersh.musicmagician.data.server.core.entity.LyricsTag>()
                }
    }
}