package Spices

enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF),
    YELLOW(0xFFFF00)
}

sealed class Spice(
    val name: String,
    private val spiciness: String = "mild",
    color: SpiceColor): SpiceColor by color {
    private val heat
        get() = when(spiciness) {
            "sauce" -> 0
            "mild" -> 1
            "spicy" -> 2
            "hotter" -> 3
            "hottest" -> 4
            "hell" -> 5
            else -> 1
        }

    override fun toString(): String = "[${this.name}:${this.spiciness}:${this.color}]"

    abstract fun prepareSpice()

    init {
        println("Created spice '${this}.")
    }
}

interface Grinder {
    fun grind()
}

class Curry(spiciness: String, color:SpiceColor = YellowSpiceColor):
    Spice("Curry", spiciness, color), Grinder {

    override fun grind() {
        println("Grinding...")
    }

    override fun prepareSpice() {
        println("Preparing a curry spice")
        grind()
    }
}

interface SpiceColor {
    val color: Color
}

object YellowSpiceColor: SpiceColor {
    override val color = Color.YELLOW
}

fun delegate() {
    val curry = Curry("spicy")
    println("Spice color is ${curry.color}.")
    curry.prepareSpice()
}

data class SpiceContainer(val spice: Spice) {
    val label by lazy { spice.name }
}

fun main() {
    delegate()

    val s1 = SpiceContainer(Curry("mild"))
    val s2 = SpiceContainer(Curry("soft"))
    val s3 = SpiceContainer(Curry("hell"))

    val spiceCabinet = listOf(s1, s2, s3)

    for(it in spiceCabinet) println(s1.label)



}

