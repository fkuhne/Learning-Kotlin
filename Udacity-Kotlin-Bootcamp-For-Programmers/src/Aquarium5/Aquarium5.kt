package Aquarium5

import java.util.*

data class Fish(val name: String)

fun fishExamples() {
    val fish = Fish("splashy")

    // lets create a lambda. The object is implicitly referenced
    // by the word "this". Actually we can remove this
    with(fish.name) { println(this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })  }
    // under the hood, with is a higher-order function. To see how this works, we can make our
    // own greatly simplified version of "with" that just works for strings.
    myWith(fish.name) { println(replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }) }

    println(fish)
}
// Locally, inside myWith function body, block is an extension function of a String object
// and we can apply it to a string.
fun myWith(name: String, block: String.() -> Unit) {
    // So now we can apply the passed in function to the passed in argument.
    name.block()
}

fun main() {
    fishExamples()

    val numbers = listOf<Int>(1,2,3,4,5,6,7,8,9,0)

    println(numbers.divisibleBy { it.rem(3) })

    val filteredList = numbers.applyFilterToEachItem { it.rem(3) }
    println(filteredList)

}


// Practice 6.5: Create an extension on List using a higher order
// function that returns all the numbers in the list that are divisible
// by 3. Start by creating an extension function on List that takes an
// lambda on Int and applies it to each item in the list. When the
// lambda returns zero, include the item in the output.

fun List<Int>.divisibleBy(block: (Int) -> Int): List<Int> {
    val res = mutableListOf<Int>()
    for(it in this) if(block(it)==0) res.add(it)
    return res
}

// divisibleBy is a higher-order function that takes a lambda to compute
// something inside the input argument.

// Maybe a better name to "divisibleBy" is "applyFilterToEachItem":
fun List<Int>.applyFilterToEachItem(block: (Int) -> Int): List<Int> {
    val res = mutableListOf<Int>()
    for(it in this) if(block(it)==0) res.add(it)
    return res
}


// My wrong attempts!! :(
fun filterList(name: List<Int>, block: Int.() -> Boolean) {
    var t = mutableListOf<Int>()
    for(it in name) if(!it.block()) t.add(it)
}

fun divBy(name: Int, block: Int.() -> Int) {

}

// ---

// Aula 6.6: Inlines:
// let's take a look at myWith again: it takes a String "name" and an extension lambda, "block".
// In the body, it applies the extension lambda "block" to the "name":
// fun myWith(name: String, block: String.() -> Unit) {
//     name.block()
// }
// and we call it like this
// myWith2(fish.name) { capitalize() }
// here we are saying: "call myWith2 on fish.name" and we pass in a lambda
// which will call capitalize on the name.
// There is one BIG problem with this: every time we call myWith, Kotlin will make
// a new lambda object. YES, lambdas as objects. a lambda expression is an
// instance of a function interface, which is itself a subtype of an object.
// To help understand, we can rewrite the call to HOF like this:
// myWith(fish.name, object: Function1<String, Unit> {
//      override fun invoke(name: String) { name.capitalize() }
// })
// That is, it creates an instance of Function1 every time myWith is called
// takes CPU time and memory, adding potentially lots of overhead in runtime.
// To fix this problem, Kotlin let us define HOFs as inlines:
inline fun myWith2(name: String, block: String.() -> Unit) {
    name.block()
}
// That is a promise that every time myWith is called, it will actually
// transform the source code to _inline_ the function. That is, the compiler
// will change the code to replace the lambda with the instructions
// inside the lambda, and that means zero overhead.
// with inline, the call to the lambda is replaced with the contents of the
// function body of the lambda. In our myWith example, when we apply the
// transform, capitalize() is called directly on fish.name, like this:
// fish.name.capitalize()

// This is really important. Kotlin lets us define lambda-based APIs
// with zero overhead.
// It is worth noting that inline functions does increase the code size. So,
// it's best used for simple functions, like myWith.
// As a users of these APIs, we don't have to worry about creating extra
// objects, because the compiler will do that for us.
