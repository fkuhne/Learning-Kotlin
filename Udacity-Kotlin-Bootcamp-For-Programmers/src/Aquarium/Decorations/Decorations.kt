package Aquarium.Decorations

// 4.11: Data classes

data class Decorations(val rocks: String) {
}

data class Decorations2(val rocks: String,
                        val wood: String,
                        val diver: String) {

}


fun makeDecorations() {

    val d1 = Decorations("granite")
    println(d1)

    val d2 = Decorations("slate")
    println(d2)

    val d3 = Decorations("slate")
    println(d3)

    println(d1.equals(d2))
    println(d2.equals(d3))

    val d4 = d3.copy()
    println(d3)
    println(d4)

    val d5 = Decorations2("crystal", "wood", "diver")
    println(d5)

    // Decomposition: with this, we can get at the properties and
    // assign them to variables
    val (rock, wood, diver) = d5
    println(rock)
    println(wood)
    println(diver)
}

fun main() {
    makeDecorations()
}