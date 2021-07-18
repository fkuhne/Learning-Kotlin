package Aquarium.generics

import Aquarium.Fish

// Aula 5.10: Generic Classes

// create this package inside the package Aquarium allows us to redefine
// stuff using the same names without conflicts

// this class is open, meaning that we can create subclasses from it. It also
// creates a mutable parameter, along with a getter and a setter, for variable
// needsProcessed.
open class WaterSupply(var needsProcessed: Boolean)
// these are specialized classes
class TapWater : WaterSupply(true) {
    fun addChemicalCleaners() {
        needsProcessed = false
    }
}
class FishStoreWater : WaterSupply(false)
class LakeWater : WaterSupply(true) {
    fun filter() {
        needsProcessed = false
    }
}

// Lets create an Aquarium class which accepts generic type as its type, and then use it:
class Aquarium<T>(val waterSupply: T)
fun genericExample() {
    val aquarium = Aquarium<TapWater>(TapWater())
    // ok now we have an object Aquarium of type TapWater and access addChemicalCleaners():
    aquarium.waterSupply.addChemicalCleaners()

    // the object type in line 30 can be inferred by the parameter type, so we can remove the
    // argument between angled brackets and declare the object like this:
    val aquarium2 = Aquarium(TapWater())
    aquarium2.waterSupply.addChemicalCleaners()

    // look how strange: using a top level generic type like <T> allows us to create
    // an object of an unsupported type:
    val wrongAquarium = Aquarium("string")
    println(wrongAquarium.waterSupply)
    // this is because type T doesn't have any bounds, so it can actually be set to Any type
    // (nullable type included - the type at the top of the type hierarchy (Any?)), and that
    // could be a problem. Even null type can be passed:
    val nullAquarium = Aquarium(null)
    println(nullAquarium.waterSupply)
}

// To ensure the Aquarium class will receive only allowable types, we can limit like this:
class Aquarium2<T: Any>(val waterSupply: T)
// this will ensure non nullable type of Any type, and makes it impossible to pass null as
// a parameter

// Actually, we can be specific as we want, and limit further, with:
class Aquarium3<T: WaterSupply>(val waterSupply: T) {
    fun addWater() {
        // check is an STL function from Kotlin. It is like an assertion.
        check(!waterSupply.needsProcessed) {"water supply needs processed."}
        println("add water from $waterSupply.")
    }
}
// this generic type will represent the type at the top hierarchy we want to use, that is,
// we can use the base class WaterSupply and also its subclasses TapWater, FishStoreWater
// and LakeWater.

fun genericExample2() {
    // ok now lets use Aquarium3 class:
    val aquarium = Aquarium3(LakeWater())
    aquarium.addWater() // this will throw an exception due to check function above!
    // that is, before adding the water, we need to clean it, with:
    aquarium.waterSupply.filter()
}

// Aula 5.13: Generic In and Out:
// class that can be used as input argument:
interface Cleaner<in T: WaterSupply> {
    fun clean(waterSupply: T)
}
// and its implementations for the three subclasses:
class TapWaterCleaner: Cleaner<TapWater> {
    override fun clean(waterSupply: TapWater) {
        waterSupply.addChemicalCleaners()
    }
}
class FishStoreWaterCleaner: Cleaner<FishStoreWater> {
    override fun clean(waterSupply: FishStoreWater) {}
}
class LakeWaterCleaner: Cleaner<LakeWater> {
    override fun clean(waterSupply: LakeWater) {
        waterSupply.filter()
    }
}

// A class that can be used as a return value:
class Aquarium4<out T: WaterSupply>(val waterSupply: T) {
    fun addWater(cleaner: Cleaner<T>) {
        if(waterSupply.needsProcessed) cleaner.clean(waterSupply)
        println("Adding water from $waterSupply.")
    }
}
fun addItemTo(aquarium: Aquarium4<WaterSupply>) {
    println("Item added.")
}
fun genericExample3() {
    val cleaner = TapWaterCleaner()
    val aquarium = Aquarium4(TapWater())
    aquarium.addWater(cleaner)
}


// Aula 5.15: Generic functions:
// It is a good idea whether the function has an argument as a class that takes generics.

// Once we have the type T, we can use it as the generic to Aquarium. Another way to say this is:
// T is a type parameter to isWaterClean that is been used to specify the generic type of
// the Aquarium.
fun <T: WaterSupply> isWaterClean(aquarium: Aquarium4<T>) {
    println("Aquarium water is clean: ${aquarium.waterSupply.needsProcessed}")
}
fun genericExample4() {
    val aquarium:Aquarium4<TapWater> = Aquarium4(TapWater()) // the type here is not mandatory
                                                             // since Kotlin can discover it by type inference
    isWaterClean<TapWater>(aquarium) // The type between angle brackets is also not mandatory because of
                                     // the type inference Kotlin performs on the parameter type aquarium.
}

// we can use generic function also on class methods, even on classes that have their
// own generic type. Lets create another class to demonstrate this:
class Aquarium5<out T: WaterSupply>(val waterSupply: T) {
//    fun <R: WaterSupply> hasWaterSupplyOfType(): Boolean {
//        return waterSupply is R
//    }
    // the function above will not work yet because we have to make R
    // "REIFIED" in order to use R in the "is" check.
    // Reified just means "real". A reified type is a real type. We also
    // need the keyword inline in the beginning of the function.
    inline fun <reified R: WaterSupply> hasWaterSupplyOfType(): Boolean {
        return waterSupply is R
    }
    // Non reified types are only available at compile time and can't be use
    // at run time by the program.
}
// and finally, let's call the generic method. Just by calling generic functions,
// call generic methods by using angle brackets af ter the function name:
fun genericExample5() {
    val aquarium:Aquarium5<TapWater> = Aquarium5(TapWater())
    println(aquarium.hasWaterSupplyOfType<TapWater>()) // this will return TRUE
}

// Let's go back to generic functions. We can use reified also on them, and
// even on extension functions, like this:
inline fun <reified T: WaterSupply> WaterSupply.isOfType() = this is T
fun genericExample6() {
    val aquarium:Aquarium5<TapWater> = Aquarium5(TapWater())
    println(aquarium.waterSupply.isOfType<LakeWater>()) // this will return FALSE
}

// Star projections: we don't really care what type of Aquarium
// hasWaterSupplyOfType is called on, so we can say it just by putting
// an asterisk, or a star, as the generic. This says that the function
// will take any type. This is a convenient way to skip specifying the type
// when you don't really need to care. Kotlin will ensure you won't make
// any thing unsafe.
inline fun <reified R: WaterSupply> Aquarium<*>.hasWaterSupplyOfType(): Boolean {
    return waterSupply is R
}

// Type Erasure.
// All generic types are only used at compile time. This lets the compiler make sure
// that we are doing everything safely. However, during RUNTIME, all generic types are
// ERASED! It turns out that the compiler can create completely correct code without
// keeping the generic types at runtime. But it does mean that sometimes we do something
// like "is" checks like the ones we did above in a generic type, and the compiler
// can't support it. That's why Kotlin added the reified, or real types