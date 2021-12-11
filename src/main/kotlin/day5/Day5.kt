package day5

import day5.Direction.*
import java.io.File
import java.util.*

private const val TEST = false
private const val X1 = 0
private const val Y1 = 1
private const val X2 = 2
private const val Y2 = 3

fun main() {
    val input =
        if (TEST) File("src/main/kotlin/day5/test-input.txt").readLines()
        else File("src/main/kotlin/day5/input.txt").readLines()
    val coordinates = createCoordinates(input)
    dayFivePuzzleOne(coordinates)
    dayFivePuzzleTwo(coordinates)
}

fun dayFivePuzzleOne(coordinates: List<IntArray>) {
    val diagram = createDiagram(coordinates)
    val dangerousAreas = calculateDangerousArea(diagram)
    println("There are $dangerousAreas dangerous areas")
}

fun dayFivePuzzleTwo(coordinates: List<IntArray>) {
    val diagram = createDiagram(coordinates, true)
    val dangerousAreas = calculateDangerousArea(diagram)
    println("There are $dangerousAreas dangerous areas")
}

private fun createCoordinates(input: List<String>): List<IntArray> {
    val coordinates = mutableListOf<IntArray>()
    input.forEach { coordinateString ->
        val startCoordinateString = coordinateString.substring(0, coordinateString.indexOf("-")).trim()
        val endCoordinateString = coordinateString.substring(coordinateString.indexOf(">") + 2).trim()
        val startCoordinate = startCoordinateString.split(',').map(String::toInt).toIntArray()
        val endCoordinate = endCoordinateString.split(',').map(String::toInt).toIntArray()
        val coordinate = intArrayOf(startCoordinate[0], startCoordinate[1], endCoordinate[0], endCoordinate[1])
        coordinates.add(coordinate)
    }
    return coordinates.toList()
}

/** To avoid the most dangerous areas, you need to determine the number of points where at least two lines overlap. */
fun calculateDangerousArea(diagram: List<IntArray>): Int {
    var dangerousAreas = 0
    diagram.forEach { row ->
        row.forEach { cell ->
            if (cell > 1) dangerousAreas += 1
        }
    }
    return dangerousAreas
}

private fun createDiagram(coordinates: List<IntArray>, considerDiagonal: Boolean = false): List<IntArray> {
    val diagram: MutableList<IntArray> = createEmptyDiagram()

    coordinates.forEach { coordinate ->
        val startX = minOf(coordinate[X1], coordinate[X2])
        val endX = maxOf(coordinate[X1], coordinate[X2])
        val startY = minOf(coordinate[Y1], coordinate[Y2])
        val endY = maxOf(coordinate[Y1], coordinate[Y2])
        val moves = endX - startX
        when (direction(coordinate)) {
            HORIZONTAL -> for (i in startX..endX) diagram[startY][i] += 1
            VERTICAL -> for (i in startY.. endY) diagram[i][startX] += 1
            DOWN_RIGHT -> {
                if (considerDiagonal) {
                    for (i in 0.. moves) {
                        diagram[coordinate[Y1] + i][coordinate[X1] + i] += 1
                    }
                }
                println("down right | ${coordinate[0]},${coordinate[1]} -> ${coordinate[2]},${coordinate[3]}")
            }
            DOWN_LEFT -> {
                if (considerDiagonal) {
                    for (i in 0.. moves) {
                        diagram[coordinate[Y1] + i][coordinate[X1] - i] += 1
                    }
                }
                println("down left | ${coordinate[0]},${coordinate[1]} -> ${coordinate[2]},${coordinate[3]}")
            }
            UP_RIGHT -> {
                if (considerDiagonal) {
                    for (i in 0.. moves) {
                        diagram[coordinate[Y1] - i][coordinate[X1] + i] += 1
                    }
                }
                println("up right | ${coordinate[0]},${coordinate[1]} -> ${coordinate[2]},${coordinate[3]}")
            }
            UP_LEFT -> {
                if (considerDiagonal) {
                    for (i in 0.. moves) {
                        diagram[coordinate[Y1] - i][coordinate[X1] - i] += 1
                    }
                }
                println("up left | ${coordinate[0]},${coordinate[1]} -> ${coordinate[2]},${coordinate[3]}")
            }
        }
    }
    return diagram.toList()
}

private fun createEmptyDiagram(): MutableList<IntArray> {
    val diagram: MutableList<IntArray> = mutableListOf()
    if (TEST) for (i in 0..9) diagram.add(IntArray(10))
    else for (i in 0..999) diagram.add(IntArray(1000))
    return diagram
}

fun direction(coordinate: IntArray): Direction = when {
    coordinate[X1] != coordinate[X2] && coordinate[Y1] == coordinate[Y2] -> HORIZONTAL
    coordinate[X1] == coordinate[X2] && coordinate[Y1] != coordinate[Y2] -> VERTICAL
    coordinate[X1] < coordinate[X2] && coordinate[Y1] < coordinate[Y2] -> DOWN_RIGHT
    coordinate[X1] > coordinate[X2] && coordinate[Y1] < coordinate[Y2] -> DOWN_LEFT
    coordinate[X1] < coordinate[X2] && coordinate[Y1] > coordinate[Y2] -> UP_RIGHT
    else -> UP_LEFT
}

enum class Direction {
    HORIZONTAL,
    VERTICAL,
    DOWN_RIGHT,
    DOWN_LEFT,
    UP_RIGHT,
    UP_LEFT,
}