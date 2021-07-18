package Aquarium

// volumeNeeded doesn't contain val or var so Kotlin will
// not create a property for this variable
class Fish(val friendly: Boolean = true, volumeNeeded: Int) {

    val size: Int // this will be a public class parameter that
    // can be used by the object (instead of volumeNeeded).

    init {
        println("First init block.")
    }
    // Most classes in Kotlin don't need a secondary constructor, but if
    // we do declare one, it must contain a call to the primary
    // constructor by using 'this':
    constructor() : this(true, 9) {
        println("Running secondary constructor.")
    }

    init {
        if (friendly) {
            size = volumeNeeded
        } else {
            size = volumeNeeded * 2
        }
    }

    init {
        println("Constructed fish of size $volumeNeeded. Final size is ${this.size}.")
    }

    init {
        println("Last init block.")
    }
}

fun makeDefaultFish() = Fish(true, 50)

fun fishExample() {
    val fish = Fish(true, 20)
    println("Is the fish friendly? ${fish.friendly}. It needs volume ${fish.size}.")
}

fun main() {
    println("Calling makeDefaultFish():")
    makeDefaultFish()

    println("\n\nCalling fishExample():")
    fishExample()

    println("\n\nCalling Fish():")
    Fish()

    aula6_2()
}


// Aula 6.2 Lambas recap
data class Fish2(val name:String)
fun aula6_2() {
    val myFish = listOf(Fish2("Flipper"), Fish2("Moby Dick"), Fish2("Dory"))

    //lets filter this list by names that has an  "i":
    // inside the filter method there is a lambda, where it refers to the current element of
    // the list. We get the name and check which contains the letter "i". We then apply
    // joinToString creates a string from all the elements of the list, separated by the
    // supplied separator.
    println(myFish.filter{it.name.contains('i')}.joinToString(", "){it.name})
}