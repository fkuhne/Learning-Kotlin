
enum class Direction {
    NORTH, SOUTH, EAST, WEST, START, END
}

class Coord2D(var x: Int = 0, var y: Int = 0)

class Game() {
    var path = mutableListOf<Direction>(Direction.START)

    // A set of lambda functions:
    val north = { path.add(Direction.NORTH) }
    val south = { path.add(Direction.SOUTH)  }
    val east  = { path.add(Direction.EAST)  }
    val west  = { path.add(Direction.WEST)  }
    val end   = { path.add(Direction.END); print("Game Over: "); println(path); path.clear(); false }

    // A higher-order functions that takes a lambda as argument
    private fun move(where: () -> Boolean) = where.invoke()

    fun makeMove(arg: String?) {
        when(arg) {
            "w" -> move(west)
            "e" -> move(east)
            "n" -> move(north)
            "s" -> move(south)
            "", null -> move(end)
            else -> return
        }
    }
}

class Location(private val game: Game) {
    private val width = 4; private val height = 4
    private var currentLocation = Coord2D()
    private val map = Array(height) { arrayOfNulls<String>(width) }

    init {
        map[0][0]="casa"; map[1][0]="escola";                            map[3][0]="Basso"
                                                map[2][1] = "joãozinho"; map[3][1]="bicicleta"
        map[0][2]="Ijui"; map[1][2]="Protasio"; map[2][2] = "igreja"
        map[0][3]="Erico";                                               map[3][3] = "oficina"
    }

    fun updateLocation(arg: String?) {
        game.makeMove(arg)
        when (arg) {
            "n" -> if (currentLocation.y > 0) currentLocation.y -= 1
            "s" -> if (currentLocation.y < height-1) currentLocation.y += 1
            "e" -> if (currentLocation.x > 0) currentLocation.x -= 1
            "w" -> if (currentLocation.x < width-1) currentLocation.x += 1
            else -> return
        }
        println("Current coord: (${currentLocation.x}, ${currentLocation.y}) = ${map[currentLocation.x][currentLocation.y]}")
    }
}

fun main() {

    val game = Game()
    val location = Location(game)
    println(game.path)

    var c:String? = null
    while(true) {
        print("Enter a direction: n/s/e/w:")
        c = readLine()
        println(c)
        if(c == null) break
        location.updateLocation(c)
    }

    println(game.path)
}

