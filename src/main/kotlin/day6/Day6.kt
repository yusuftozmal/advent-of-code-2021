package day6

import java.io.File

private const val DAYS_18 = 18
private const val DAYS_80 = 80
private const val DAYS_256 = 256

private const val TEST = false

fun main() {
    val input =
        if (TEST) File("src/main/kotlin/day6/test-input.txt").readLines()[0]
        else File("src/main/kotlin/day6/input.txt").readLines()[0]

    val amountFishPerDay: LongArray = LongArray(9).apply {
        input.split(",").map { it.toInt() }.forEach { this[it] += 1L }
    }

    daySixPuzzleOne(amountFishPerDay, DAYS_80)
    daySixPuzzleTwo(amountFishPerDay, DAYS_256)
}

fun daySixPuzzleOne(amountFishPerDay: LongArray, days: Int) {
   calculateFish(amountFishPerDay, days)
}

fun daySixPuzzleTwo(amountFishPerDay: LongArray, days: Int) {
    calculateFish(amountFishPerDay, days)
}

private fun calculateFish(amountFishPerDay: LongArray, days: Int) {
    var fishPerDay = amountFishPerDay
    repeat(days) {
        val newFish = fishPerDay[0]
        fishPerDay = longArrayOf(
            fishPerDay[1],
            fishPerDay[2],
            fishPerDay[3],
            fishPerDay[4],
            fishPerDay[5],
            fishPerDay[6],
            fishPerDay[7] + newFish,
            fishPerDay[8],
            newFish
        )
    }
    println("After $days days there are total ${fishPerDay.sum()} fish")
}