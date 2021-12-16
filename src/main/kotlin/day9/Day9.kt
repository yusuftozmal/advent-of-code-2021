package day9

import java.io.File

private const val TEST = false
private const val VISITED = -1
private const val STOP = 9

fun main() {
    Day9().dayNinePuzzleOne()
    Day9().dayNinePuzzleTwo()
}
class Day9 {
    private val heightmap by lazy { parseInput() }

    fun dayNinePuzzleOne() {
        val lowestPoints = mutableListOf<Int>()
        heightmap.forEachIndexed { y, column ->
            column.forEachIndexed { x, height ->
                when {
                    // top-left cell
                    (x == 0 && y == 0) -> {
                        if (checkRight(height, x, y) && checkBelow(height, x, y)) lowestPoints.add(height)
                    }
                    // top-right cell
                    (x == column.lastIndex && y == 0) -> {
                        if (checkLeft(height, x, y) && checkBelow(height, x, y)) lowestPoints.add(height)
                    }
                    // bottom-left cell
                    (x == 0 && y == heightmap.lastIndex) -> {
                        if (checkAbove(height, x, y) && checkRight(height, x, y)) lowestPoints.add(height)
                    }
                    // bottom-right cell
                    (x == column.lastIndex && y == heightmap.lastIndex) ->  {
                        if (checkAbove(height, x, y) && checkLeft(height, x, y)) lowestPoints.add(height)
                    }
                    // left cells
                    x == 0 -> {
                        if (checkAbove(height, x, y) && checkRight(height, x, y) && checkBelow(height, x, y)) lowestPoints.add(height)
                    }
                    // top cells
                    y == 0 -> {
                        if (checkLeft(height, x, y) && checkBelow(height, x, y) && checkRight(height, x, y)) lowestPoints.add(height)
                    }
                    // right cells
                    x == column.lastIndex -> {
                        if (checkAbove(height, x, y) && checkBelow(height, x, y) && checkLeft(height, x, y)) lowestPoints.add(height)
                    }
                    // bottom cells
                    y == heightmap.lastIndex -> {
                        if (checkLeft(height, x, y) && checkAbove(height, x, y) && checkRight(height, x, y)) lowestPoints.add(height)
                    }
                    // inner cells
                    else -> {
                        if (checkLeft(height, x, y) && checkAbove(height, x, y) && checkRight(height, x, y) && checkBelow(height, x, y)) {
                            lowestPoints.add(height)
                        }
                    }
                }
            }
        }
        val riskLevelSum = lowestPoints.sum().plus(lowestPoints.size)
        println("The sum of the risk levels of all low points are $riskLevelSum")
    }

    private val queue = mutableSetOf(Pair(0, 0))
    private val basins = mutableListOf<List<Int>>()

    fun dayNinePuzzleTwo() {
        setFirstValidPointToQueue()
        val basin = mutableListOf<Int>()

        if (queue.isEmpty()) {
            basins.sortByDescending { it.size }
            val threeLargestBasins = basins[0].size * basins[1].size * basins[2].size
            println("The multiply of 3 larges basins are: $threeLargestBasins")
        } else {
            while (queue.isNotEmpty()) {
                val point = queue.first()
                val height = heightmap[point.second][point.first]
                heightmap[point.second][point.first] = STOP
                queue.remove(point)
                if (isValid(height)) {
                    basin.add(height)
                    findNeighbors(point).map { queue.add(it) }
                }
            }
            basins.add(basin)
            dayNinePuzzleTwo()
        }
    }

    private fun setFirstValidPointToQueue() {
        run loop@{
            heightmap.forEachIndexed { y, row ->
                row.forEachIndexed { x, height ->
                    if (isValid(height)) {
                        queue.add(Pair(x, y))
                        return@loop
                    }
                }
            }
        }
    }

    private fun isValid(currentPoint: Int) = currentPoint != STOP //&& currentPoint != VISITED

    private fun findNeighbors(point: Pair<Int, Int>): List<Pair<Int, Int>> {
        val neighbors = mutableListOf<Pair<Int, Int>>()
        if (point.first != 0) neighbors.add(Pair(point.first - 1, point.second)) // left neighbor
        if (point.first != heightmap[0].lastIndex) neighbors.add(Pair(point.first + 1, point.second)) // right neighbor
        if (point.second != 0) neighbors.add(Pair(point.first, point.second - 1)) // above neighbor
        if (point.second != heightmap.lastIndex) neighbors.add(Pair(point.first, point.second + 1)) // below neighbor

        val neighborsCopy = mutableListOf<Pair<Int, Int>>()
        neighborsCopy.addAll(neighbors)
        neighborsCopy.forEach {
            val neighborPoint = heightmap[it.second][it.first]
            if (neighborPoint == STOP) neighbors.remove(it)
        }
        return neighbors
    }

    private fun checkAbove(height: Int, x: Int, y: Int) = height < above(x, y)
    private fun checkBelow(height: Int, x: Int, y: Int) = height < below(x, y)
    private fun checkLeft(height: Int, x: Int, y: Int) = height < left(x, y)
    private fun checkRight(height: Int, x: Int, y: Int) = height < right(x, y)

    private fun above(x: Int, y: Int) = heightmap[y-1][x]
    private fun below(x: Int, y: Int) = heightmap[y+1][x]
    private fun left(x: Int, y: Int) = heightmap[y][x-1]
    private fun right(x: Int, y: Int) = heightmap[y][x+1]

    private fun parseInput(): Array<IntArray> {
        val input =
            if (TEST) File("src/main/kotlin/day9/test-input.txt").readLines().toTypedArray()
            else File("src/main/kotlin/day9/input.txt").readLines().toTypedArray()

        val arraysOfInputDigit = mutableListOf<IntArray>()
        input.forEach { inputRow ->
            val inputDigits = mutableListOf<Int>()
            inputRow.split("").forEach { char ->
                if (char.isNotBlank()) inputDigits.add(char.toInt())
            }
            arraysOfInputDigit.add(inputDigits.toIntArray())
        }

        return arraysOfInputDigit.toTypedArray()
    }
}