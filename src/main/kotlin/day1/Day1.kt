package day1

import java.io.File

fun main() {
    val numbers = File("src/main/kotlin/day1/input.txt")
        .readLines()
        .map(String::toInt)

    dayOnePuzzleOne(numbers)
    dayOnePuzzleTwo(numbers)
}

fun dayOnePuzzleOne(numbers: List<Int>) {
    var larger = 0
    var previous = numbers.first()
    numbers.forEach { number ->
        if (number > previous) larger += 1
        previous = number
    }
    println("Values larger than previous values = $larger")
}

fun dayOnePuzzleTwo(numbers: List<Int>) {
    val bottom = numbers.size - 3
    var sumsLargerThanPrevious = 0
    repeat(numbers.size) { index ->
        if (index < bottom) {
            val previousSum = numbers[index] + numbers[index + 1] + numbers[index + 2]
            val currentSum = numbers[index + 1] + numbers[index + 2] + numbers[index + 3]
            val what: String
            when {
                currentSum > previousSum -> {
                    what = "increased"
                    sumsLargerThanPrevious += 1
                }
                previousSum == currentSum -> what = "no change"
                else -> what = "decreased"
            }
//            println("previousSum = $previousSum | currentSum = $currentSum | $what")
        }
    }
    println("Sums that are larger than the previous sum = $sumsLargerThanPrevious")
}
