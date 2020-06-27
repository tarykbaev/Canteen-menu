package kg.turar.arykbaev.canteenmenu.types

class Menu(
    val date: String,
    val name: List<String>,
    val calorie: List<String>,
    val url: List<String>
) {
    override fun equals(other: Any?): Boolean {
        return this.date == other.toString()
    }
}