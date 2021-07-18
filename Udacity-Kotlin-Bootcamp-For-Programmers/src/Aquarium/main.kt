package Aquarium

fun main(args: Array<String>) {
    buildAquarium()

    makeFish()
}

fun buildAquarium() {
    val myAquarium = Aquarium()

    println("Length: ${myAquarium.length} cm, " +
            "Width: ${myAquarium.width} cm, " +
            "Height: ${myAquarium.height} cm")

    myAquarium.height = 80
    println("Height: ${myAquarium.height} cm.")
    println("Volume: ${myAquarium.volume} liters.")

    val smallAquarium = Aquarium(width = 20,height = 15, length = 30)
    println("Small aquarium has ${smallAquarium.volume} liters.")

    val smallAquarium2 = Aquarium(numberOfFish = 9)
    println("Small aquarium 2 has ${smallAquarium2.volume} liters with " +
            "length ${smallAquarium2.length} cm, " +
            "width ${smallAquarium2.width} cm, " +
            "height ${smallAquarium2.height} cm.")
}

// every fish passed by here must implement
// the interface eat
fun feedFish(fish: FishAction) {
    fish.eat()
}

fun makeFish() {
    val shark = Shark()
    val pleco = Plecostomus()

    println("Shark ${shark.color}\nPlecostomus ${pleco.color}.")

    shark.eat()
    pleco.eat()
}