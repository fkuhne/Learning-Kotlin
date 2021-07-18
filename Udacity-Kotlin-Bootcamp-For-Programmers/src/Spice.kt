class Spice(private val spiceName: String, private val spiciness: String = "mild") {
    val heat: Int
        get() = when(spiciness) {
                "sauce" -> 0
                "mild" -> 1
                "spicy" -> 2
                "hotter" -> 3
                "hottest" -> 4
                "hell" -> 5
                else -> 1
        }

    override fun toString(): String = "[${this.spiceName}:${this.spiciness}]"

    init {
        println("Created spice '${this.spiceName}'\twith spiciness\t'${this.spiciness}' (level ${this.heat}).")
    }
}

fun makeSalt() = Spice("salt")

fun main() {
    val curry = Spice("curry")
    val pepper = Spice("pepper", "sauce")
    val cayenne = Spice("cayenne", "mild")
    val ginger = Spice("ginger", "spicy")
    val redCurry = Spice("red curry", "hottest")
    val greenCurry = Spice("green curry", "hell")
    val redPepper = Spice("red pepper", "hell")

    makeSalt()

    val mySpices = listOf(curry, pepper, cayenne, ginger, redCurry, greenCurry, redPepper)
    println(mySpices)

    val mySoftSpices = mySpices.filter{it.heat <= 2}
    println("mySoftSpices = $mySoftSpices")
}
