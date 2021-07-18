import java.util.*
import kotlin.random.Random.Default.nextInt
import kotlin.text.*

fun getFortuneCookie(day: Int): String {
    val fortunes = listOf(
        "You will have a great day!",
        "Things will go well for you today.",
        "Enjoy a wonderful day of success.",
        "Be humble and all will turn out well.",
        "Today is a good day for exercising restraint.",
        "Take it easy and enjoy life!",
        "Treasure your friends because they are your greatest fortune."
    )
//    println("Enter your birthday: ")
//    val birthday: Int = readLine()?.toIntOrNull() ?: 1
    return when(day) {
        28, 31 -> fortunes[1]
        in 1..7 -> fortunes[2]
        else -> fortunes[day.rem(fortunes.size)]
    }
//    fortunes[day % fortunes.size]
}

fun getBirthday(): Int {
    print("\nEnter your birthday: ")
    return readLine()?.toIntOrNull() ?: 1
}

fun fitMoreFish(tankSize: Double,
                currentFish: List<Int>,
                fishSize: Int = 2,
                hasDecorations: Boolean = true) : Boolean {

    val totalFishSize = fishSize + currentFish.sum()
//    for(s in currentFish) totalFishSize += s

    val availableSpace = if(hasDecorations) tankSize*0.8 else tankSize

    return availableSpace >= totalFishSize
}

fun typeYourMood() : String {
    print("\nType your mood: ")
    return readLine()!!
}

fun isVeryHot(temperature: Int) = temperature > 35
fun isSadRainyCold(mood: String, weather: String, temperature: Int) = mood == "sad" && weather == "rainy" && temperature < 15
fun isHappySunny(mood: String, weather: String) = mood == "happy" && weather == "sunny"

fun whatShouldIDoToday(mood: String? = null,
                       weather: String = "sunny",
                       temperature: Int = 24) : String {

    val thisMood = mood ?: typeYourMood()

    return when {
        isSadRainyCold(thisMood, weather, temperature) -> "stay in bed"
        isHappySunny(thisMood, weather) && temperature >= 20 -> "go to the fields"
        isHappySunny(thisMood, weather) -> "go for a walk"
        thisMood == "happy" && weather == "rainy" -> "go for a car ride"
        thisMood == "sad" && weather == "sunny" -> "stay home and watch a movie"
        thisMood == "sad" && weather == "rainy" -> "stay home and read a book"
        isVeryHot(temperature) -> "go swimming"
        else -> "sip a chimarrão"
    }
}

fun eagerExample() {
    val decorations = listOf("rock", "pagoda", "plastic plant", "alligator", "flowerpot")

    val eager = decorations.filter{it[0] == 'p'}
    println(eager) // this will be printed out because it is evaluated eagerly
}

fun lazyExample() {
    val decorations = listOf("rock", "pagoda", "plastic plant", "alligator", "flowerpot")

    val filtered = decorations.asSequence().filter{it[0] == 'p'}
    println(filtered) // nothing will be printed out since this is a lazy execution
    println(filtered.toList()) // will be printed out since it is evaluated to a list

    //apply map lazily
    val lazyMap = decorations.asSequence().map {
        println("map: $it")
        it
    }
    println(lazyMap)
    println("first: ${lazyMap.first()}")
}

fun randomDay(): String {
    val week= listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    return week[Random().nextInt(7)]
}

fun fishFood(day: String): String {
    return when(day) {
        "Monday" -> "flakes"
        "Tuesday" -> "pellets"
        "Wednesday" -> "redworms"
        "Thursday" -> "ganules"
        "Friday" -> "mosquitoes"
        "Saturday" -> "lettuce"
        "Sunday" -> "plankton"
        else -> "fasting"
    }
}

fun getDirtySensorReading() = 20
fun shouldChangeWater(day: String, temperature: Int=22, dirty: Int=getDirtySensorReading()): Boolean {
    fun isTooHot(temperature: Int) = temperature>30
    fun isDirty(dirty: Int) = dirty>30
    fun isSunday(day: String) = day == "Sunday"
    return when {
        isTooHot(temperature) -> true
        isDirty(dirty) -> true
        isSunday(day) -> true
        else -> false
    }
}

fun feedTheFish() {
    val day = randomDay()
    val food = fishFood(day)
    println("Today is $day and the fish ate $food")

    if(shouldChangeWater(day) ) {
        println("Change the water today.")
    }

    dirtyProcessor()
}

// Lambdas:

var dirty=20
val waterFilter={dirty:Int->dirty/2}

fun feedFish(dirty: Int) = dirty+10 // This is a named function

// this is a "higher order function", that takes a lambda as the
// last argument (Kotlin prefers function arguments to be always
// the last parameter). Note that the operation has a function type, just
// like we used in the function variables.
fun updateDirty(dirty: Int, operation: (Int)->Int): Int {
    return operation(dirty)
}

fun dirtyProcessor() {
    dirty = updateDirty(dirty, waterFilter)
    dirty = updateDirty(dirty, ::feedFish) // Note that, since feedFish is a named
                                           // function, and not a lambda, we need to
                                           // use the double colon; it allows you pass
                                           // a function reference instead of a lambda
    // "Last parameter syntax": we don't need to put the lambda function inside the
    // function parenthesis
    dirty = updateDirty(dirty) {
            dirty -> dirty+50
    }
    // We can rewrite this syntax with the parenthesis back in the function, and
    // the lambda as the last parameter:
    dirty = updateDirty(dirty, {
        dirty -> dirty+50
    })
}

// a simple lambda
val rollDice = {(1..12).random()}
// a lambda with parameters
//var rollDiceWithArgs: (Int)->Int = {sides -> (1..sides).random()}
// OR:
val rollDiceWithArgs = {sides: Int -> (0..sides).random()}
// function type notation:
val rollDice2: (Int)->Int = {sides -> (0..sides).random()}
// a higher order function that takes a lambda as argument
fun rollDice3(sides: Int, operation:(Int)->Int): Int {
    return operation(sides)
}

fun gamePlay(sides: Int, operation:(Int)->Int): Int {
    return operation(sides)
}
// Another, easy way:
fun gamePlay2(diceRoll: Int) = println(diceRoll)
// OR: fun gamePlay2(diceRoll: Int) { println(diceRoll) }

fun main(args: Array<String>) {

    // aula 4
    val spice = SimpleSpice()
    println("Spice name: ${spice.name}.")
    println("Heat level: ${spice.heat}.")

//    println(rollDice())
//    println(rollDiceWithArgs(7))
//    println(rollDiceWithArgs(0))
//    println(rollDice2(5))
//    println(rollDice3(6, rollDiceWithArgs))
//    println(gamePlay(6, rollDice2))
//    gamePlay2(rollDice2(6))

    // lambda functions
//    dirtyProcessor()
//    println(dirty)

//    val spices = listOf("curry", "pepper", "cayenne", "ginger", "red curry", "green curry", "red pepper" )
//    val sortedSpices = spices.sorted()
//    println(sortedSpices)
//    println(sortedSpices.filter{it.startsWith('c') && it.endsWith('e')})
//    // OR
//    println(sortedSpices.filter{it.startsWith('c')}.filter{it.endsWith('e')})

//    eagerExample()
//    lazyExample()

//    println(whatShouldIDoToday())
//    println(whatShouldIDoToday("sad"))
//    println(whatShouldIDoToday(temperature = 21, mood = "happy", weather = "sunny"))

//    print("\nHow do you feel? ")
//    println(whatShouldIDoToday(readLine()!!))

//    println(fitMoreFish(10.0, listOf(3,3,3)))
//    println(fitMoreFish(8.0, listOf(2,2,2), hasDecorations = false))
//    println(fitMoreFish(9.0, listOf(1,1,3), 3))
//    println(fitMoreFish(10.0, listOf(), 7, true))

//    var fortune: String
////    for(i in 1..10) {
//    while(true) {
//        fortune = getFortuneCookie(getBirthday())
//        println("Your fortune is: \"$fortune\"")
//        if(fortune.contains(("easy"))) break
//    }

//    for(i in 1..10) {
//        val f = getFortuneCookie()
//        println("Your fortune is: \"$f\"")
//        if(f.contains(("easy"))) break
//    }

//    println("Good ${if(args[0].toInt()<12) "morning" else "night"}, Kotlin!")

}