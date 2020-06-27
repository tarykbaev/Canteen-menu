package kg.turar.arykbaev.canteenmenu.parsers

import io.reactivex.rxjava3.core.Single
import kg.turar.arykbaev.canteenmenu.types.Menu
import org.jsoup.Jsoup

class MenuSource {
    companion object {
        fun getItemMenu(): Single<List<Menu>> {
            return Single.create {
                val menuItem = ArrayList<Menu>()

                val doc = Jsoup.connect("http://bis.manas.edu.kg/menu/").get();
                val content = doc.getElementsByTag("tbody")[0]

                for (i in content.children()) {
                    val name = ArrayList<String>()
                    val calorie = ArrayList<String>()
                    val url = ArrayList<String>()

                    name.addAll(
                        listOf(
                            i.child(1).text(), i.child(3).text(),
                            i.child(5).text(), i.child(7).text()
                        )
                    )
                    calorie.addAll(
                        listOf(
                            i.child(2).text() + " kalori", i.child(4).text() + " kalori",
                            i.child(6).text() + " kalori", i.child(8).text() + " kalori"
                        )
                    )
                    url.addAll(
                        listOf(
                            i.child(1).getElementsByAttribute("href").attr("href"),
                            i.child(3).getElementsByAttribute("href").attr("href"),
                            i.child(5).getElementsByAttribute("href").attr("href"),
                            i.child(7).getElementsByAttribute("href").attr("href")
                        )
                    )

                    menuItem += Menu(i.child(0).text(), name, calorie, url)
                }
                it.onSuccess(menuItem)
            }
        }
    }
}