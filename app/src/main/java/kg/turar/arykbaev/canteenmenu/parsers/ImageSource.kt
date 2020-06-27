package kg.turar.arykbaev.canteenmenu.parsers

import io.reactivex.rxjava3.core.Single
import org.jsoup.Jsoup

class ImageSource {
    companion object {
        fun getImageUrl(url1: String): Single<String> {
            return Single.create {
                val doc1 = Jsoup.connect(url1).userAgent("Mozilla/5.0").get()
                    .getElementsByTag("img")[1].attr("src")

                it.onSuccess(doc1)
            }
        }
    }
}