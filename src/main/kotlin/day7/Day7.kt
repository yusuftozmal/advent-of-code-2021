package day7

import java.io.File

private const val TEST = false

fun main() {
    val input =
        if (TEST) File("src/main/kotlin/day7/test-input.txt").readLines()[0].split(",").map { it.toInt() }
        else File("src/main/kotlin/day7/input.txt").readLines()[0].split(",").map { it.toInt() }

    val crabPositions = IntArray(input.size).apply {
        input.forEachIndexed { i, number -> this[i] = number }
    }

    daySevenPuzzleOne(crabPositions, FuelCost.INCREASING_WITH_ONE_PER_STEP)
}

fun daySevenPuzzleOne(crabPositions: IntArray, fuelCost: FuelCost) {
    val leastFuelUsedPosition = IntArray(2)
    val maxPosition = crabPositions.maxOrNull() ?: 0
    val minPosition = crabPositions.minOrNull() ?: 0

    for (startPosition in minPosition.rangeTo(maxPosition)) {
        var fuelSum = 0
        crabPositions.forEach { destinationPosition ->
            fuelSum += calculateFuelCost(startPosition, destinationPosition, fuelCost)
        }
        if (startPosition == 0 || fuelSum < leastFuelUsedPosition[1]) {
            leastFuelUsedPosition[0] = startPosition
            leastFuelUsedPosition[1] = fuelSum
        }
        println("Fuel sum for position $startPosition is $fuelSum")
    }
    println("Least used fuel is ${leastFuelUsedPosition[1]} and it is on position ${leastFuelUsedPosition[0]}")
}

private fun calculateFuelCost(startPosition: Int, destinationPosition: Int, fuelCost: FuelCost): Int {
    val moves =
        if (startPosition >= destinationPosition) startPosition - destinationPosition
        else destinationPosition - startPosition

    return when (fuelCost) {
        FuelCost.ONE_PER_STEP -> moves
        FuelCost.INCREASING_WITH_ONE_PER_STEP -> (moves * (1 + moves)) / 2 //Arithmetic progression formula
    }
}

enum class FuelCost {
    ONE_PER_STEP,
    INCREASING_WITH_ONE_PER_STEP
}