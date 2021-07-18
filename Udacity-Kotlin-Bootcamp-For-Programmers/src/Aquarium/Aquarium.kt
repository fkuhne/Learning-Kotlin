package Aquarium

import kotlin.math.PI as MathPI

open class Aquarium (var width: Int = 20, var height: Int = 40, var length: Int = 100) {

    // This creates public variables inside the class,
    // But in order to make a more useful class, we can add them in the
    // constructor, as above. When we use parameters like this when
    // creating a class, we are also creating the parameters!
    // This is very common in Kotlin.
//    var width: Int = 20
//    var height: Int = 40
//    var length: Int = 100

    open var volume: Int
        get() = width*height*length/1000
        set(value) {height = (value*1000)/(width*(length))}

    open var water = volume * 0.9 // inferred type is Double

    constructor(numberOfFish: Int) : this() {
        val water = numberOfFish * 2000
        val tank = water + water*0.1
        height = (tank/(length*width)).toInt()
    }
}

class TowerTank(): Aquarium() {

    override var volume: Int = 0
        get() = (width * height * length / 1000 * MathPI).toInt()

    override var water = volume * 0.8
}

// Abstract classes:
abstract class AquariumFish {
    abstract val color: String
}

// An interface:
interface FishAction {
    fun eat()
}

// Subclasses that implements the abstract classes and the interface
class Shark: AquariumFish(), FishAction {

    override val color = "gray"

    override fun eat() = println("Hunt and eat.")
}

class Plecostomus: AquariumFish(), FishAction {

    override val color = "gold"

    override fun eat() = println("Munch on algae.")
}

// An example showing that an interface can have also implementations
// of methods. We can use it when we have lots of methods, and just a
// few of them implement the called "default implementations"
interface AquariumAction {
    fun eat()
    fun jump()
    fun clean()
    fun catchFish()
    fun swim() = println("swim") // imagine that every fish, no matter
                                 // what type it is, needs to swim, so
                                 // we can have a default implementation
                                 // to this action. But the other actions
                                 // can be left open only according to the
                                 // fish type
}

// We use an abstract class every time we CANNOT complete a class.
// For example, here we can leave the color abstract while implementing
// a default for the eat method. This is because there is no really a
// good default color for a fish
abstract class AquariumFish2: FishAction {
    abstract val color: String
    override fun eat() = println("yum")
}

// Actually, Kotlin provides a better way to do this rather abstract classes:
// "Interface delegation".

fun main() {
    //delegate()

//    aula5_2()

    aula5_4()
}

fun delegate() {
    val pleco = Plecostomus2()
    println("Fish has color ${pleco.color}.")
    pleco.eat()
}

interface FishColor {
    val color: String
}

class Plecostomus2: FishAction, FishColor {
    override fun eat() = println("eat algae")
    override val color: String
        get() = "gold"
}

object GoldColor: FishColor {
    override val color = "gold"
}

object RedColor: FishColor {
    override val color = "red"
}

// With this object that implements one instance of the FishColor
// interface, we are ready to use interface delegation and rewrite
// the class Plecostomus again:
class Plecostomus3: FishAction,
                    FishColor by GoldColor { // This means: implement the
                                             // interface FishColor by deferring
                                             // all calls to the object GoldColor.
                                             // So every time we call the 'color'
                                             // property in this class. it will
                                             // actually call the color property in
                                             // GoldColor.
    override fun eat() = println("eat algae")
// This declaration is not needed anymore since we have delegated it
// to the interface implementation
//    override val color: String
//        get() = "gold"
}

// We can also use delegates in class constructor, like this:
class Plecostomus4(fishColor: FishColor = GoldColor):
    FishColor by fishColor,
    FishAction {
    override fun eat() {
        println("eat algae")
    }
}

// One more example: in this case, since we have an input argument 'food',
// we cannot make PrintingFishAction an object, since we want a different
// instance for each food we pass in.
class PrintingFishAction(val food: String): FishAction {
    override fun eat() {
        println(food)
    }
}


// Note that we can still use interface delegation within the
// class Plecostomus and class PrintingFishAction, where we will
// replace the override of eat with the delegation to FishAction:
class Plecostomus5(fishColor: FishColor = GoldColor):
    FishColor by fishColor,
    FishAction by PrintingFishAction("lots of algae!")

// Note that Plecostomus5 does not have a body. All the override
// implementations are handled by interface delegation!

// aula 4.13: Special class types in Kotlin:

object MobyDickWhale {
    val author = "Herman Melville"
    fun jump() {}
}

enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}

sealed class Seal

class SealLion: Seal()
class Walrus: Seal()

fun matchSeal(seal: Seal):String {
    return when(seal) {
        is SealLion -> "Sea Lion"
        is Walrus -> "Walrus"
    }
}

// aula 5.2: Pairs
fun aula5_2() {
    val equipment = "fishnet" to "catching fish" to "big size" to "and strong"
    println(equipment.first.first.second)

    val equipment2 = ("fishnet" to "catching fish") to ("of big size" to "and strong")
    println(equipment2)

    val fishnet = "fishnet" to "catching fish"
    val (tool, use) = fishnet
    println("The $tool is a tool for $use.")

    // we can turn pairs into a string
    val fishnetString = fishnet.toString()
    println(fishnetString)

    // we can turn pairs into a list
    println(fishnet.toList())

    // pairs can be used to return more than one value from
    // a function, destructuring it on the arrival?
    val (toool, usee) = giveMeATool()
    println(toool)
    println(usee + "\n\n")

    val cpp_book = Book("C++ Essentials", "Bjarne Stroupstroup", "1998")

    val (title, author, year) = cpp_book.returnBookInfo2()
    println("Here is your book '$title', written by '$author' in $year.")

}

fun giveMeATool(): Pair<String, String> {
    return ("fishnet" to "catching fish")
}

class Book(val title: String, val author: String, val year: String) {

    fun returnBookInfo(): Pair<String,String> = Pair(title, author)
    fun returnBookInfo2(): Triple<String,String,String> = Triple(title,author,year)
}

// aula 5.4: Collections

// a classical way to revert a list: loops through the
// list, adding elements in reverse order to a second list
fun revertList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (i in 0..list.size-1) {
        result.add(list[list.size-i-1])
    }
    return result
}

// we can improve this in Kotlin a little bit:
fun revertList2(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (i in list.size-1 downTo 0) {
        result.add(list.get(i))
    }
    return result
}


fun aula5_4() {
    val testList = listOf(11,12,13,14,15,16,17,18,19,20)

    println(revertList(testList))

    //in Kotlin, we can just ask for a reversed list with this!
    println(testList.reversed())

    val symptoms = mutableListOf("white spots", "red spots", "not eating", "bloated", "belly up")

    // a mutable list is a list where we can add/remove elements
    symptoms.add("white fungus")
    symptoms.remove("white fungus")

    // Whether exist both listOf and mutableListOf, it is good style
    // to prefer listOf

    // In fact, we can do lots of things with lists, for example, sum up
    // its elements.
    listOf(1,2,3).sum()

    // We can use sumOf when we need to specify which property
    // of the elements to use to summarize. sumOf takes a lambda
    // function, where 'it' is the default name for lambda arguments.
    println(listOf("a", "bbb", "cc").sumOf { it.length })

    // Mapping: like mapping keys to values
    val cures = mapOf("white spots" to "Ich", "red sores" to "hole disease")

    println(cures.get("white spots"))
    // OR
    println(cures["white spots"])

    // Use getOrDefault to indicate what to return when an element
    // is not on the list:
    println(cures.getOrDefault("bloating", "sorry I don't know."))

    println(cures.getOrElse("bloating"){ "No cure for this" })

    // We can create e map that can be modified with mutableMapOf:
    val inventory = mutableMapOf("fish net" to 1)
    inventory.put("tank scrubber", 3) // this can be written as inventory["tank scrubber"] = 3
    inventory.remove("fish net")

    // immutable collections (listOf, mapOf) are particularly useful
    // in a threaded environment when multiple threads touch in the
    // same collection

    val allBooks = setOf("Hamlet", "The Tempest", "Romeo and Juliet", "Macbeth", "Othello", "King Lear", "The Merchant of Venice")
    val library = mapOf("William Shakespeare" to allBooks)
    println(library.any{it.value.contains("Hamlet")})
    println(library)
//    println(library["William Shakespeare"]?.any{it.contains("Hamlet")})

    val moreBooks = mutableMapOf("William Shakespeare" to allBooks)
    val dickensBooks = setOf("The Pickwick Papers", "Oliver Twist", "A Christmas Carol", "Great Expectations")
    moreBooks.put("Charles Dickens", dickensBooks)

    // I coudn't get it right ;(
//    moreBooks["Charles Dickens"].getOrElse("Little Dorrit")

    // This is the solution of the exercise:
    val allBooks2 = setOf("Macbeth", "Romeo and Juliet", "Hamlet", "A Midsummer Night's Dream")
    val library2 = mapOf("Shakespeare" to allBooks2)
    println(library2.any { it.value.contains("Hamlet") })

    val moreBooks2 = mutableMapOf<String, String>("Wilhelm Tell" to "Schiller")
    moreBooks2.getOrPut("Jungle Book") { "Kipling" }
    moreBooks2.getOrPut("Hamlet") { "Shakespeare" }
    println(moreBooks2)
}

// Aula 5.7

fun aula5_7() {
    println("Does it have spaces?".hasSpaces())
}

fun String.hasSpaces() = find {it == ' '} != null

class AquariumPlant(val color: String, private val size: Int)
// Extension function that is not part of the main API. In this case,
// we clearly show that it is just a helper
fun AquariumPlant.isRed() = color == "Red"

// Extended functions don't get access to private vars:
//fun AquariumPlant.isBig() = size > 50 // <- this will leeds us to an error

// There are also the "extension properties":
val AquariumPlant.isGreen: Boolean
    get() = color == "Green"
fun propertyExample() {
    val plant = AquariumPlant("Green", size = 50)
    println(plant.isGreen)
}

// another example with a nullable receiver:
fun AquariumPlant?.pull() { // a pull method that takes a nullable receiver
                            // that is, the object in which the extension
    //                      // function is called can be null.
    this?.apply {
        println("removing $this")
    }
}
fun nullableExample() {
    val plant: AquariumPlant? = null
    println(plant.pull()) // ok
}

// Aula 5.10: Generic Classes
// my comment: it is like C++ templates

// when we have similar methods for several data types, eventually we would
// need to implement these:
class MyIntList {
    fun get(pos: Int): Int { return 0 }
    fun addItem(item: Int) {}
}
class MyShortList {
    fun get(pos: Int): Short { return 0 }
    fun addItem(item: Short) {}
}

// That's where generics come in handy. We can make this:

class MyList<Type> {
    fun get(pos: Int): Type { TODO("implement") }
    fun addItem(item: Type) {}
}

// And then create the objects. For ex:
fun workWithLists() {
    val strList: MyList<String>
    val fishList: MyList<Fish>
}