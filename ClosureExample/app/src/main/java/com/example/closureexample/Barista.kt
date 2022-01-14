package com.example.closureexample

import android.os.Build
import androidx.annotation.RequiresApi

class Barista(private val name: String) {
    private val coffeeMaker = CoffeeMaker()

    /**
     * This is creating a blocking, synchronous execution
     */
    fun acceptOrderBlocking(type: CoffeeType) {
        val coffee = coffeeMaker.brewCoffee(type)
        println("$name finished brewing ${coffee.type}")
    }
}

class BaristaWithCallback(private val name: String) : CoffeeMakerWithInterface.OnCoffeeBrewedListener {
    private val coffeeMaker = CoffeeMakerWithInterface()

    @RequiresApi(Build.VERSION_CODES.N)
    fun acceptOrder(type: CoffeeType) {
        coffeeMaker.brewCoffee(type, this)
    }

    override fun onCoffeeBrewed(coffee: Coffee) {
        println("$name finished brewing ${coffee.type}")
    }
}


class BaristaWithAnonymousCallback(private val name: String) {
    private val coffeeMaker = CoffeeMakerWithInterface()

    @RequiresApi(Build.VERSION_CODES.N)
    fun acceptOrder(type: CoffeeType) {
        /**
         * Callbacks can be put inline also, like anonymous functions. This format can be used
         *   when the interface is simple enough. However, if you take this anonymous approach,
         *   there is a better way: that's closures!
         */
        coffeeMaker.brewCoffee(type, object : CoffeeMakerWithInterface.OnCoffeeBrewedListener {
            override fun onCoffeeBrewed(coffee: Coffee) {
                println("$name finished brewing ${coffee.type}")
            }

        })
    }
}

/**
 * If a callback interface has only one function, a callback can typically be converted into
 *   a closure, or, more generally, a lambda.
 *
 * A lambda is a function that can be stored as a variable. This is also referred to as
 *   first-class functions. A closure is a way of using a lambda.
 */

class BaristaWithClosure(private val name: String) {
    private val coffeeMaker = CoffeeMakerWithClosure()

    /**
     * Defining a variable that has a type of a function that takes Coffee and returns nothing
     *   (the same signature of the lambda function inside CoffeeMakerWithClosure class).
     */
    private val onFinished: ((Coffee) -> Unit) = { coffee ->
        println("$name finished brewing ${coffee.type}")
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun acceptOrder(type: CoffeeType) {
        coffeeMaker.brewCoffee(type, onFinished)
    }
}

class BaristaWithLambdaArg(private val name: String) {

    private val onFinished: ((Coffee) -> Unit) = { coffee ->
        println("$name finished brewing ${coffee.type}")
    }

    private var coffeeMaker = CoffeeMakerWithLambdaArg(onFinished)
    /**
     * This can also be like this:
     * private var coffeeMaker = CoffeeMakerWithLambdaArg() {
     *   println("$name finished brewing ${it.type}")
     * }
     */

    @RequiresApi(Build.VERSION_CODES.N)
    fun acceptOrder(type: CoffeeType) {
        coffeeMaker.brewCoffee(type)
    }
}

class BaristaWithLambdaVar(private val name: String) {

    private val onFinished: ((Coffee) -> Unit) = { coffee ->
        println("$name finished brewing ${coffee.type}")
    }

    private var coffeeMaker = CoffeeMakerWithLambdaVar()

    init {
        coffeeMaker.onBrewed = onFinished

        coffeeMaker.onBrewed = {
            println("$name finished brewing ${it.type}")
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun acceptOrder(type: CoffeeType) {
        coffeeMaker.brewCoffee(type)
    }
}
