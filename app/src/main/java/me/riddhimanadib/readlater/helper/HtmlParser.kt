package me.riddhimanadib.readlater.helper

import me.riddhimanadib.readlater.model.Link
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


/**
 * Created by Adib on 28-May-17.
 */
class HtmlParser {

    companion object {

        fun get(url: String): Link {

            val document = Jsoup.connect(url).get()

            val title = document.title()

            var link: Link = Link(title, url)

            val description: String? = getMetaTag(document, "description")
            if (description == null) {
                link.description = getMetaTag(document, "og:description") ?: ""
            }

            link.imageUrl = getMetaTag(document, "og:image") ?: ""

            return link
        }

        private fun getMetaTag(document: Document, attr: String): String? {
            var elements = document.select("meta[name=$attr]")
            for (element in elements) {
                val s = element.attr("content")
                if (s != null) return s
            }
            elements = document.select("meta[property=$attr]")
            for (element in elements) {
                val s = element.attr("content")
                if (s != null) return s
            }
            return null
        }

    }

}