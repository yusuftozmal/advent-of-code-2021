package day3

import java.io.File

private const val BINARY_SIZE = 12
fun main() {
    val binaries = File("src/main/kotlin/day3/input.txt").readLines()
    dayThreePuzzleOne(binaries)
    dayThreePuzzleTwo(binaries)
}

fun dayThreePuzzleOne(binaries: List<String>) {
    val zero = IntArray(BINARY_SIZE)
    val one = IntArray(BINARY_SIZE)
    val gamma = IntArray(BINARY_SIZE)
    val epsilon = IntArray(BINARY_SIZE)

    binaries.forEach { binary ->
        for (i in binary.indices) {
            if (binary[i] == '0') zero[i] += 1
            else one[i] += 1
        }
    }

    print("zeroArray = ")
    zero.forEach { print("$it ") }
    println()
    print("oneArray = ")
    one.forEach { print("$it ") }
    println()

    var i = 0
    while(i < BINARY_SIZE) {
        if (zero[i] > one[i]) {
            gamma[i] = 0
            epsilon[i] = 1
        } else {
            gamma[i] = 1
            epsilon[i] = 0
        }
        i++
    }

    print("gammaBinary = ")
    gamma.forEach { print(it) }
    println()
    print("epsilonBinary = ")
    epsilon.forEach { print(it) }
    println()

    val gammaDecimal = convertBinaryArrayToDecimal(gamma)
    val epsilonDecimal = convertBinaryArrayToDecimal(epsilon)
    val powerConsumption = gammaDecimal * epsilonDecimal

    println("gammaDecimal = $gammaDecimal")
    println("epsilonDecimal = $epsilonDecimal")
    println("Power Consumption = $powerConsumption")

}

private fun convertBinaryArrayToDecimal(binaryArray: IntArray): Int {
    var decimal = 0
    for (i in binaryArray.indices) {
        when(i) {
            0 -> decimal += binaryArray[i] * (2*2*2*2*2*2*2*2*2*2*2)
            1 -> decimal += binaryArray[i] * (2*2*2*2*2*2*2*2*2*2)
            2 -> decimal += binaryArray[i] * (2*2*2*2*2*2*2*2*2)
            3 -> decimal += binaryArray[i] * (2*2*2*2*2*2*2*2)
            4 -> decimal += binaryArray[i] * (2*2*2*2*2*2*2)
            5 -> decimal += binaryArray[i] * (2*2*2*2*2*2)
            6 -> decimal += binaryArray[i] * (2*2*2*2*2)
            7 -> decimal += binaryArray[i] * (2*2*2*2)
            8 -> decimal += binaryArray[i] * (2*2*2)
            9 -> decimal += binaryArray[i] * (2*2)
            10 -> decimal += binaryArray[i] * 2
            11 -> decimal += binaryArray[i]
        }
    }
    return decimal
}

fun dayThreePuzzleTwo(input: List<String>) {
    var oxygenBinaries = input
    var scrubberBinaries = input
    var i = 0
    while (i < BINARY_SIZE) {
        if (oxygenBinaries.size > 1) {
            var oxygenZero = 0
            var oxygenOne = 0
            oxygenBinaries.forEach {
                if (it[i] == '0') oxygenZero += 1
                else oxygenOne += 1
            }
            println("position $i oxygenZero = $oxygenZero")
            println("position $i oxygenOne = $oxygenOne")

            val remainingBinaries = mutableListOf<String>()
            oxygenBinaries.forEach {
                if (oxygenZero > oxygenOne && it[i] == '0') remainingBinaries.add(it)
                else if (oxygenOne > oxygenZero && it[i] == '1') remainingBinaries.add(it)
                else if (oxygenZero == oxygenOne && it[i] == '1') remainingBinaries.add(it)
            }
            oxygenBinaries = remainingBinaries

            println("Remaining oxygen binaries")
            oxygenBinaries.forEach { println(it) }
        }

        if (scrubberBinaries.size > 1) {
            var scrubberZero = 0
            var scrubberOne = 0
            scrubberBinaries.forEach {
                if (it[i] == '0') scrubberZero += 1
                else scrubberOne += 1
            }
            println("position $i scrubberZero = $scrubberZero")
            println("position $i scrubberOne = $scrubberOne")

            val remainingBinaries = mutableListOf<String>()
            scrubberBinaries.forEach {
                if (scrubberZero < scrubberOne && it[i] == '0') remainingBinaries.add(it)
                else if (scrubberOne < scrubberZero && it[i] == '1') remainingBinaries.add(it)
                else if (scrubberZero == scrubberOne && it[i] == '0') remainingBinaries.add(it)
            }
            scrubberBinaries = remainingBinaries

            println("Remaining scrubber binaries")
            scrubberBinaries.forEach { println(it) }
        }

        i++
    }

    val oxygenGeneratorRatingArray = IntArray(BINARY_SIZE)
    val co2ScrubberRatingArray = IntArray(BINARY_SIZE)
    var j = 0
    while (j < BINARY_SIZE) {
        oxygenGeneratorRatingArray[j] = if(oxygenBinaries.first()[j] == '1') 1 else 0
        co2ScrubberRatingArray[j] = if(scrubberBinaries.first()[j] == '1') 1 else 0
        j++
    }

    val oxygenGeneratorRating = convertBinaryArrayToDecimal(oxygenGeneratorRatingArray)
    val co2ScrubberRating = convertBinaryArrayToDecimal(co2ScrubberRatingArray)
    println("oxygenGeneratorRating = $oxygenGeneratorRating")
    println("co2ScrubberRating = $co2ScrubberRating")

    val lifeSupportRating = oxygenGeneratorRating * co2ScrubberRating
    println("lifeSupportRating = $lifeSupportRating")
}

