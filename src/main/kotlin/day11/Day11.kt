package day11

import Cell
import extensions.findNeighbors
import extensions.print
import parseFileToTwoDimensionalArray

private const val TEST = false
private const val FULL = 10

fun main() {
    Day11().dayElevenPuzzleOne()
//    Day11().dayElevenPuzzleTwo()
}

class Day11 {
    private val octopusMatrix by lazy { parseInput() }
    private val queue = mutableSetOf<Cell>()
    private val flashedOctopus = mutableListOf<Cell>()
    private val alreadyFo = mutableListOf<Cell>()
    private var totalFlashed = 0
    private var step = 0

    fun dayElevenPuzzleOne() {
        checkFlashingDumboOctopuses()
    }

    private fun checkFlashingDumboOctopuses() {
        println("Before any steps")
        octopusMatrix.print()
        while (step < 100) {
            increaseAllValues()
            step += 1
            while (flashedOctopus.isNotEmpty()) {
                val octopusCell = flashedOctopus.first()
                increaseNeighborEnergy(octopusCell)
                flashedOctopus.removeAt(0)
            }
            println("After step $step")
            octopusMatrix.print()
        }
        println("Total flashed octopus $totalFlashed")
    }

    private fun increaseNeighborEnergy(octopusCell: Cell) {
        octopusMatrix.findNeighbors(octopusCell.x, octopusCell.y).map { queue.add(it) }
        queue.map {
            if (!alreadyFo.contains(it)) {
                octopusMatrix[it.y][it.x] += 1
                if (octopusMatrix[it.y][it.x] == FULL) {
                    flash(it.x, it.y)
                }
            }
        }
        queue.clear()
    }

    private fun flash(x: Int, y: Int) {
        octopusMatrix[y][x] -= 10
        totalFlashed += 1
        flashedOctopus.add(Cell(x, y))
        alreadyFo.add(Cell(x, y))
    }

    private fun increaseAllValues() {
        alreadyFo.clear()
        octopusMatrix.mapIndexed { y, row ->
            row.mapIndexed { x, _ ->
                octopusMatrix[y][x] += 1
                if (octopusMatrix[y][x] == FULL) {
                    flash(x, y)
                }
            }
        }
    }
}

private fun parseInput(): Array<IntArray> {
    val inputPath = if (TEST) "src/main/kotlin/day11/test-input.txt" else "src/main/kotlin/day11/input.txt"
    return parseFileToTwoDimensionalArray(inputPath)
}