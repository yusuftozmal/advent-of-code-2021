package day2

import java.io.File
private const val WHITE_SPACE = " "
private const val FORWARD = "forward"
private const val UP = "up"
private const val DOWN = "down"

fun main() {
    val directions = File("src/main/kotlin/day2/input.txt").readLines()
    dayTwoPuzzleOne(directions)
    dayTwoPuzzleTwo(directions)
}

fun dayTwoPuzzleOne(directions: List<String>) {
    var horizontalPosition = 0
    var depth = 0

    directions.forEach {  direction ->
        val directionValue = direction.substringAfter(WHITE_SPACE).toInt()
        when {
            direction.startsWith(FORWARD) -> horizontalPosition += directionValue
            direction.startsWith(UP) -> depth -= directionValue
            direction.startsWith(DOWN) -> depth += directionValue
        }
    }
    println("Horizontal position = $horizontalPosition | Depth = $depth")
    println("Multiply of horizontal position and depth = ${horizontalPosition * depth}")
}

fun dayTwoPuzzleTwo(directions: List<String>) {
    var horizontalPosition = 0
    var depth = 0
    var aim = 0

    directions.forEach {  direction ->
        val directionValue = direction.substringAfter(WHITE_SPACE).toInt()
        when {
            direction.startsWith(FORWARD) -> {
                horizontalPosition += directionValue
                depth += directionValue * aim
            }
            direction.startsWith(UP) -> aim -= directionValue
            direction.startsWith(DOWN) -> aim += directionValue
        }
    }
    println("Horizontal position = $horizontalPosition | Depth = $depth")
    println("Multiply of horizontal position and depth = ${horizontalPosition * depth}")
}
