package Buildings

import Aquarium.generics.Aquarium4
import Aquarium.generics.WaterSupply

open class BaseBuildingMaterial(val numberNeeded:Int = 1)
class Wood: BaseBuildingMaterial(4)
class Brick: BaseBuildingMaterial(8)

// a class that takes a generic type T of type at least BaseBuildingMaterial (that is, can
// be BaseBuildingMaterial, Wood or Brick.
class Building<T: BaseBuildingMaterial>(val base: T, private val baseMaterialsNeeded: Int = 100) {
    var actuallyMaterialsNeeded = base.numberNeeded * baseMaterialsNeeded
    fun build() = println("$actuallyMaterialsNeeded ${base::class.simpleName} required.")
}

// Practice 5.16
fun <T: BaseBuildingMaterial> isSmallBuilding(baseMaterial: Building<T>) {
    if(baseMaterial.actuallyMaterialsNeeded < 500) println("small building")
    else println("large building")
}

fun main() {
    val wood = Building(Wood())
    wood.build()
    // OR: Building(Wood()).build()

    isSmallBuilding(wood)

    val brick = Building(Brick())
    brick.build()
    isSmallBuilding(brick)


}
