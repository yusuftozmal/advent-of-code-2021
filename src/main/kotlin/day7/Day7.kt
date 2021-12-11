package day6

import java.io.File
import kotlin.math.max
import kotlin.math.min

private const val TEST = false

fun main() {
    val input =
        if (TEST) File("src/main/kotlin/day7/test-input.txt").readLines()[0].split(",").map { it.toInt() }
        else File("src/main/kotlin/day7/input.txt").readLines()[0].split(",").map { it.toInt() }

    val crabPositions = IntArray(input.size).apply {
        input.forEachIndexed { i, number -> this[i] = number }
    }

    daySevenPuzzleOne(crabPositions)
//    daySixPuzzleTwo(amountFishPerDay, DAYS_256)
}

fun daySevenPuzzleOne(crabPositions: IntArray) {
//    val maxLeftPosition = crabPositions.minOrNull()
//    val maxRightPosition = crabPositions.maxOrNull()
//    var usedFuel = IntArray(crabPositions.size)
    val leastFuelUsedPosition = IntArray(2)
    val maxPosition = crabPositions.maxOrNull() ?: 0
    val minPosition = crabPositions.minOrNull() ?: 0

    for (i in minPosition.rangeTo(maxPosition)) {
        var fuelSum = 0
        crabPositions.forEach { currentPosition ->
            val rightCrab = max(i, currentPosition)
            val leftCrab = min(i, currentPosition)
            fuelSum += rightCrab - leftCrab
        }
        if (i == 0 || fuelSum < leastFuelUsedPosition[1]) {
            leastFuelUsedPosition[0] = i
            leastFuelUsedPosition[1] = fuelSum
        }
        println("Fuel sum for position $i is $fuelSum")
    }
    println("Position ${leastFuelUsedPosition[0]} requires least fuel (${leastFuelUsedPosition[1]})")
}
