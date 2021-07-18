package Aquarium

import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.findAnnotation

annotation class ImAPlant

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class OnGet

@Target(AnnotationTarget.PROPERTY_SETTER)
annotation class OnSet

@ImAPlant class Plant {
    fun trim() {}
    fun fertilize() {}

    @get:OnGet
    val isGrowing: Boolean = true

    @set:OnSet
    var needsFood: Boolean = false
}

fun reflections() {
    val classObj = Plant::class

    // print all methods of the class
    for(method in classObj.declaredMemberFunctions)
        println(method.name)

    // print all annotations of the class.
    // It will print "ImAPlant"
    for(annotation in classObj.annotations)
        println(annotation.annotationClass.simpleName)

    // One can check for a specific annotation and do something
    // as a response:
    val annotated = classObj.findAnnotation<ImAPlant>()
    annotated?.apply {
        println("Found a plant annotation ${classObj.findAnnotation<ImAPlant>().toString()}.")
    }
}

// Annotations can target getters and setters:
// @get:OnGet can only be applied to property getters;
// @set:OnSet can only be applied to property setters.


fun main() {
    reflections()
}