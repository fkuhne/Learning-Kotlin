package com.example.closureexample

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

/**
 * The Roast of the Coffee
 */
enum class CoffeeRoast {
    LIGHT,
    MEDIUM,
    DARK
}

/**
 * The Coffee Type
 *
 * @param brewTime: emulates time
 */
enum class CoffeeType(val brewTime: Long) {
    AMERICANO(300L),
    CAPPUCCINO(950L),
    DRIP(100L),
    ESPRESSO(800L),
    LATTE(875L)
}

/**
 * Emulate a delay by blocking the main thread.
 *
 * Warning: Never use this (lol)
 */
fun delay(time: Long) {
    Thread.sleep(time)
}

/**
 * Emulate a delay using threads.
 *
 * Warning: This is really just as an example.
 */
@RequiresApi(Build.VERSION_CODES.N)
fun delay(time: Long, complete: () -> Unit) {
    val completableFuture = CompletableFuture<String>()
    Executors.newCachedThreadPool().submit<Any?> {
        Thread.sleep(time)
        completableFuture.complete("")
        null
    }
    completableFuture.thenAccept { complete() }
}

/**
 * The Carrier Object for the finished order
 */
data class Coffee(val type: CoffeeType)

class CoffeeMaker {
    fun brewCoffee(type: CoffeeType): Coffee {
        delay(type.brewTime)
        return Coffee(type)
    }
}

class CoffeeMakerWithInterface {

    /**
     * Now, since we are using a callback to give us a result at a later time,
     *   we can remove the return type from this function. This indicates that the caller
     *   of this function should not expect a result immediately, rather at a later time.
     */
    @RequiresApi(Build.VERSION_CODES.N)
    fun brewCoffee(type: CoffeeType, callback: OnCoffeeBrewedListener) {
        /**
         * This simulates time to make coffee in an asynchronous way, like if the coffee machine
         *   would not need anyone supervising it...
         */
        delay(type.brewTime) {
            val madeCoffee = Coffee(type)
            callback.onCoffeeBrewed(madeCoffee)
        }
    }

    interface OnCoffeeBrewedListener {
        fun onCoffeeBrewed(coffee: Coffee)
    }
}

class CoffeeMakerWithClosure {

    /**
     * Let's replace the callback parameter with a Lambda parameter definition:
     */

    @RequiresApi(Build.VERSION_CODES.N)
    fun brewCoffee(type: CoffeeType, onBrewed: (Coffee) -> Unit) {
        delay(type.brewTime) {
            val madeCoffee = Coffee(type)
            onBrewed(madeCoffee)
        }
    }
}

class CoffeeMakerWithLambdaArg(var onBrewed: ((Coffee) -> Unit)? = null) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun brewCoffee(type: CoffeeType) {
        delay(type.brewTime) {
            val madeCoffee = Coffee(type)
            onBrewed?.let {
                it(madeCoffee)
            }
        }
    }
}

class CoffeeMakerWithLambdaVar {

    var onBrewed: ((Coffee) -> Unit)? = null

    @RequiresApi(Build.VERSION_CODES.N)
    fun brewCoffee(type: CoffeeType) {
        delay(type.brewTime) {
            val madeCoffee = Coffee(type)
            onBrewed?.let {
                it(madeCoffee)
            }
        }
    }
}
